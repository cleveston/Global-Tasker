<?php

namespace app\controllers;

use Yii;
use yii\rest\ActiveController;
use yii\web\Response;
use app\models\Process;
use app\models\Score;
use app\models\Task;
use app\models\SubtaskForm;
use yii\filters\auth\QueryParamAuth;

//The Subtask Controller
class SubtaskController extends ActiveController {

    //Define the specific model to be used
    public $modelClass = 'app\models\Process';

    //Set the authenticator behavior
    public function behaviors() {
        $behaviors = parent::behaviors();

        $behaviors['authenticator'] = [
            'class' => QueryParamAuth::className(),
        ];

        //The response format
        $behaviors['contentNegotiator']['formats']['text/html'] = Response::FORMAT_JSON;

        return $behaviors;
    }

    //Overwrite default actions
    public function actions() {
        $actions = parent::actions();
        unset($actions['index']);
        unset($actions['view']);
        unset($actions['create']);
        unset($actions['update']);
        return $actions;
    }

    //Action to view all subtasks
    public function actionIndex() {

        try {

            $result = [];

            //Get the tasks available to be processed
            $subtasks = Process::getProcess();

            //Populate the vector
            foreach ($subtasks as $subtask) {
                $result[] = [
                    'idsubtask' => $subtask->idprocess,
                    'score' => $subtask->score,
                    'type' => $subtask->task->type,
                    'login' => $subtask->task->client->login,
                    'idtask' => $subtask->idtask
                ];
            }

            return $result;
        } catch (Exception $exception) {
            return false;
        }
    }

    //Action to get a subtask
    public function actionView($id) {

        //Find the task
        $subtask = Process::findOne($id);

        //Check if the task is available
        if ($subtask && $subtask->status == Process::AVAILABLE) {

            //Assigned the task to the user
            $subtask->status = Process::ASSIGNED;
            $subtask->datesend = date("d/m/Y H:i:s");
            $subtask->idclient = Yii::$app->user->identity->getId();

            $subtask->save();

            //Return the task to be processed
            return $subtask;
        } else
            return false;
    }

    //Action to update the subtask
    public function actionUpdate($id) {

        $subtaskForm = new SubtaskForm();

        //Load the data and validate
        if ($subtaskForm->load(Yii::$app->getRequest()->getBodyParams(), '')) {


            //Find the subtask
            $subtask = Process::findOne($id);

            //Save the data processed
            $subtask->result = $subtaskForm->result;
            $subtask->datedone = date("d/m/Y H:i:s");
            $subtask->status = Process::FINISHED;

            $subtask->save();

            //Add score to the user
            $score = new Score();
            $score->idclient = Yii::$app->user->identity->getId();
            $score->score = $subtask->score;
            $score->idprocess = $subtask->idprocess;
            $score->datecreation = date("d/m/Y H:i:s");

            $score->save();

            //Check the other tasks
            $task = $subtask->task;

            //Start with true
            $terminou = true;

            //Go through all subtask
            foreach ($task->process as $process) {

                //Check if all the tasks are finished
                if ($process->status != Process::FINISHED) {
                    $terminou = false;
                    break;
                }
            }

            //If all tasks are finished, update the main task
            if ($terminou) {

                //Update the task status
                $task->status = Task::FINISHED;

                $task->save();
            }


            return true;
        }
        return false;
    }

}
