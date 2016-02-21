<?php

namespace app\models;

use Yii;
use yii\base\Model;

//The SubtaskForm model
class SubtaskForm extends Model {

    //Variables
    public $result;

    //Form rules
    public function rules() {
        return [
            [['result'], 'required', 'message' => '{attribute} in blank'],
            [['result'], 'string'],
        ];
    }

}
