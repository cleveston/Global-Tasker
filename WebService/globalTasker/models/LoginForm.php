<?php

namespace app\models;

use Yii;
use yii\base\Model;

//The LoginForm model
class LoginForm extends Model {

    //Variables
    public $login;
    public $password;

    //Form rules
    public function rules() {
        return [
            [['login', 'password'], 'required', 'message' => '{attribute} is in blank'],
        ];
    }

}
