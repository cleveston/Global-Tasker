<?php

namespace app\models;

use Yii;
use yii\base\Model;

//The TaskForm model
class TaskForm extends Model {

    //Variables
    public $task;
    public $type;

    //Form rules
    public function rules() {
        return [
            [['task', 'type'], 'required', 'message' => '{attribute} em Branco'],
            [['task'], 'string'],
            [['type'], 'string', 'max' => 1]
        ];
    }

}
