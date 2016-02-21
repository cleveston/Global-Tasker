<?php

namespace app\controllers;

use Yii;
use yii\rest\ActiveController;
use yii\web\Response;
use app\models\Client;
use app\models\UserForm;
use app\models\LoginForm;
use yii\filters\auth\QueryParamAuth;

//The User Controller
class UserController extends ActiveController {

    //Define the specific model to be used
    public $modelClass = 'app\models\Client';

    //Set the authenticator behavior
    public function behaviors() {
        $behaviors = parent::behaviors();

        $behaviors['authenticator'] = [
            'class' => QueryParamAuth::className(),
            'except' => ['create', 'login']
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
        return $actions;
    }

    //Action to view all users
    public function actionIndex() {

        //Get the user
        $user = Yii::$app->user->identity;

        return [
            'name' => $user->name,
            'login' => $user->login,
            'accesstoken' => $user->accesstoken,
            'datecreation' => $user->datecreation,
            'sumTasksWaiting' => count($user->taskswaiting),
            'sumTasksFinished' => count($user->tasksfinished),
            'score' => $user->scores
        ];
    }

    //Action to create new user
    public function actionCreate() {

        $userForm = new UserForm();

        //Load the data and validate it
        if ($userForm->load(Yii::$app->getRequest()->getBodyParams(), '') && $userForm->validate()) {

            $user = new Client();
            $user->login = $userForm->login;
            $user->password = md5($userForm->password);
            $user->datecreation = date("d/m/Y H:i:s");
            $user->status = Client::AVAILABLE;
            $user->accesstoken = md5(time());

            $user->save();

            //Return the user
            return $user;
        }

        return false;
    }

    //Action to login the user
    public function actionLogin() {

        $loginForm = new LoginForm();

        //Load the data and validate it
        if ($loginForm->load(Yii::$app->getRequest()->getBodyParams(), '') && $loginForm->validate()) {

            //Get the user by login
            $user = Client::findOne(['login' => $loginForm->login]);

            //Check if the password is correct
            if ($user && $user->password == md5($loginForm->password)) {

                //Update the accesstoken
                $user->accesstoken = md5(time());

                $user->save();

                //Return the user
                return $user;
            }
        }

        return false;
    }

    //Action to logout the user
    public function actionLogout() {

        //Get the user
        $user = Yii::$app->user->identity;

        //Set the accesstoken to null
        $user->accesstoken = null;

        $user->save();

        return true;
    }

}
