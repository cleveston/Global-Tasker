<?php

namespace app\models;

use Yii;

/**
 * @property integer $idtask
 * @property string $task
 * @property string $type
 * @property string $status
 * @property string $datecreation
 * @property string $result
 * @property integer $idclient
 *
 * @property Process[] $processes
 * @property Client $idclient0
 */
class Task extends \yii\db\ActiveRecord {

    //TYPE
    const VECTOR_SORT = 'a';
    //STATUS
    const SUBMITTED = 'a';
    const FINISHED = 'c';
    const EXCLUDED = 'd';

    //The table name in the database
    public static function tableName() {
        return 'task';
    }

    //Rules used to validate
    public function rules() {
        return [
            [['task', 'type', 'status', 'datecreation', 'idclient'], 'required'],
            [['task', 'result'], 'string'],
            [['datecreation'], 'safe'],
            [['idclient'], 'integer'],
            [['type', 'status'], 'string', 'max' => 1]
        ];
    }

    //Attributes labels
    public function attributeLabels() {
        return [
            'idtask' => 'Idtask',
            'task' => 'Task',
            'type' => 'Type',
            'status' => 'Status',
            'datecreation' => 'Datecreation',
            'result' => 'Result',
            'idclient' => 'Idclient',
        ];
    }

    //Get all processes
    public function getProcess() {
        return $this->hasMany(Process::className(), ['idtask' => 'idtask']);
    }

    //Get the own of the task
    public function getClient() {
        return $this->hasOne(Client::className(), ['idclient' => 'idclient']);
    }

}
