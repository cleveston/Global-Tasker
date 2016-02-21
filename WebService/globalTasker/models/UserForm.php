<?php

namespace app\models;

use Yii;
use yii\base\Model;
use app\models\Client;

//The UserForm Model
class UserForm extends Model {

    //Variables
    public $login;
    public $password;

    //Form rules
    public function rules() {
        return [
            [['login', 'password'], 'required', 'message' => '{attribute} em Branco'],
            [['login'], 'validateLogin'],
        ];
    }

    //Login validation
    public function validateLogin($attribute) {

        $valido = Client::validateLogin($this->$attribute);
        if ($valido) {
            $message = $valido;
            $this->addError($attribute, $message);
        }
    }

}
