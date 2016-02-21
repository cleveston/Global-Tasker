<?php

namespace app\models;

use Yii;

/**
 * @property integer $idprocess
 * @property string $status
 * @property string $result
 * @property string $datesend
 * @property integer $idclient
 * @property integer $idtask
 * @property string $datedone
 *
 * @property Client $idclient0
 * @property Task $idtask0
 * @property Score[] $scores
 */
class Process extends \yii\db\ActiveRecord {

    //STATE
    const AVAILABLE = 'a';
    const ASSIGNED = 'b';
    const FINISHED = 'c';
    const EXCLUDED = 'd';

    //The table name in the database
    public static function tableName() {
        return 'process';
    }

    //Rules used to validate
    public function rules() {
        return [
            [['status', 'idtask', 'score'], 'required'],
            [['result'], 'string'],
            [['datesend', 'datedone'], 'safe'],
            [['idclient', 'idtask'], 'integer'],
            [['score'], 'double'],
            [['status'], 'string', 'max' => 1]
        ];
    }

    //The attributes labels
    public function attributeLabels() {
        return [
            'idprocess' => 'Idprocess',
            'status' => 'Status',
            'result' => 'Result',
            'datesend' => 'Datesend',
            'idclient' => 'Idclient',
            'idtask' => 'Idtask',
            'datedone' => 'Datedone',
        ];
    }

    //Get the user that has received the task
    public function getClient() {
        return $this->hasOne(Client::className(), ['idclient' => 'idclient']);
    }

    //Get the main task
    public function getTask() {
        return $this->hasOne(Task::className(), ['idtask' => 'idtask']);
    }

    //Get the list of subtask to be processed
    public static function getProcess() {

        //Find all subtasks
        $tasks = self::find()->where('status = :status', [':status' => Process::AVAILABLE])->all();

        return $tasks;
    }

}
