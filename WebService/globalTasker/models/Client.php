<?php

namespace app\models;

use Yii;
use app\models\Task;
use yii\web\IdentityInterface;

/**
 * @property integer $idclient
 * @property string $name
 * @property string $login
 * @property string $password
 * @property string $datecreation
 * @property string $status
 *
 * @property Process[] $processes
 * @property Score[] $scores
 * @property Task[] $tasks
 */

class Client extends \yii\db\ActiveRecord implements IdentityInterface {

    //STATUS
    const AVAILABLE = 'a';
    const OCCUPIED = 'b';

    //The table name in the database
    public static function tableName() {
        return 'client';
    }

    //The rules used to validate
    public function rules() {
        return [
            [['login', 'password', 'datecreation', 'status'], 'required'],
            [['datecreation'], 'safe'],
            [['name', 'login', 'password', 'accesstoken'], 'string', 'max' => 255],
            [['status'], 'string', 'max' => 1]
        ];
    }

    //The attributes labels
    public function attributeLabels() {
        return [
            'idclient' => 'Idclient',
            'name' => 'Name',
            'login' => 'Login',
            'password' => 'Password',
            'datecreation' => 'Datecreation',
            'status' => 'Status',
        ];
    }

    //Get the user process
    public function getProcess() {
        return $this->hasMany(Process::className(), ['idclient' => 'idclient']);
    }

    //Get the user score
    public function getScores() {
        return $this->hasMany(Score::className(), ['idclient' => 'idclient'])->sum('score');
    }

    //Get the tasks that are waiting
    public function getTaskswaiting() {
        return $this->hasMany(Task::className(), ['idclient' => 'idclient'])->where(['status' => Task::SUBMITTED]);
    }

    //Get the tasks that are finished
    public function getTasksfinished() {
        return $this->hasMany(Task::className(), ['idclient' => 'idclient'])->where(['status' => Task::FINISHED]);
    }

    public function getAuthKey() {
        
    }

    //Get the user id
    public function getId() {
        return $this->idclient;
    }

    public function validateAuthKey($authKey) {
        
    }

    public static function findIdentity($id) {
        
    }

    //Find user by token
    public static function findIdentityByAccessToken($token, $type = null) {
        return static::findOne(['accesstoken' => $token]);
    }

    //Validate login
    public static function validateLogin($login) {

        $user = self::find()->where('login = :login', [':login' => $login])->one();

        //Check if anything was found
        if ($user) {

            return 'Login is in use';
        }

        return false;
    }

}
