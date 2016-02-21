<?php

namespace app\models;

use Yii;

/**
 * @property integer $idscore
 * @property integer $score
 * @property integer $idprocess
 * @property integer $idclient
 * @property string $datecreation
 *
 * @property Client $idclient0
 * @property Process $idprocess0
 */
class Score extends \yii\db\ActiveRecord {

    //COEFICIENT
    const SCORE_ADJUST = 0.1;

    //The table name in the database
    public static function tableName() {
        return 'score';
    }

    //Rules used to validate
    public function rules() {
        return [
            [['score', 'idprocess', 'idclient', 'datecreation'], 'required'],
            [['idprocess', 'idclient'], 'integer'],
            [['score'], 'double'],
            [['datecreation'], 'safe']
        ];
    }

    //Attributes label
    public function attributeLabels() {
        return [
            'idscore' => 'Idscore',
            'score' => 'Score',
            'idprocess' => 'Idprocess',
            'idclient' => 'Idclient',
            'datecreation' => 'Datecreation',
        ];
    }

    //Get the user
    public function getClient() {
        return $this->hasOne(Client::className(), ['idclient' => 'idclient']);
    }

    //Get the process
    public function getProcess() {
        return $this->hasOne(Process::className(), ['idprocess' => 'idprocess']);
    }

}
