<?php

namespace app\controllers;

use Yii;
use yii\rest\ActiveController;
use yii\web\Response;
use app\models\Task;
use app\models\Process;
use app\models\Score;
use app\models\TaskForm;
use yii\filters\auth\QueryParamAuth;
use yii\base\ErrorException;

//The Task Controller
class TaskController extends ActiveController {

    //Define the specific model to be used
    public $modelClass = 'app\models\Task';

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
        unset($actions['delete']);
        return $actions;
    }

//Action to view all tasks
    public function actionIndex() {

        $tasksWaiting = [];
        $tasksFinished = [];

        //Get the user
        $user = Yii::$app->user->identity;

        //Populate the tasks waiting for processment
        foreach ($user->taskswaiting as $value) {
            $tasksWaiting[] = $value->idtask;
        }

        //Populate the tasks finished
        foreach ($user->tasksfinished as $value) {
            $tasksFinished[] = $value->idtask;
        }

        return [
            'tasksWaiting' => $tasksWaiting,
            'tasksFinished' => $tasksFinished,
        ];
    }

//Action to view a task
    public function actionView($id) {

        //Find the task
        $task = Task::findOne($id);

        //Check if the task is finished
        if ($task && $task->status == Task::FINISHED) {

            $result = [
                "subTask_0" => [],
                "subTask_1" => [],
                "subTask_2" => []
            ];


            //Go through all subtask
            foreach ($task->process as $index => $process) {

                //Add the result to a big vector and save in the task table
                $result["subTask_$index"] = $process->result;
            }

            return $result;
        } else
            return false;
    }

//Action to create new task
    public function actionCreate() {

        $taskForm = new TaskForm();

        try {

            //Load the data and validate it
            if ($taskForm->load(Yii::$app->getRequest()->getBodyParams(), '') && $taskForm->validate()) {

                //Add the new task
                $task = new Task();
                $task->task = $taskForm->task;
                $task->datecreation = date("d/m/Y H:i:s");
                $task->status = Task::VECTOR_SORT;
                $task->type = Task::SUBMITTED;
                $task->idclient = Yii::$app->user->identity->getId();

                $task->save(false);

                //Convert the json to php format
                $data = json_decode($task->task);

                //Split the big task into more
                $vector = array_chunk($data, ceil(count($data) / 3));

                foreach ($vector as $value) {

                    //For each subtask, create a new process
                    $process = new Process();
                    $process->subtask = json_encode($value);
                    $process->status = Process::AVAILABLE;
                    $process->score = count($value) * Score::SCORE_ADJUST;
                    $process->idtask = $task->idtask;

                    $process->save(false);
                }

                return true;
            }

            return false;
        } catch (ErrorException $error) {
            return false;
        }
    }

//Action to delete a task
    public function actionDelete($id) {

        //Find the task
        $task = Task::findOne($id);

        //Check if the task is submitted
        if ($task && $task->status == Task::SUBMITTED) {

            //For each subtask, exclude it
            foreach ($task->process as $subtask) {

                //Update the subtask status
                $subtask->status = Process::EXCLUDED;

                $subtask->save();
            }

            //Update the task status
            $task->status = Task::EXCLUDED;

            $task->save();

            return true;
        } else {
            return false;
        }
    }

}
