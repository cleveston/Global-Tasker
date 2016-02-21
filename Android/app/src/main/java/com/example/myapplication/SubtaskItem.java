package com.example.myapplication;

/**
 * Created by lucasfronza on 10/12/15.
 */
public class SubtaskItem {

    private Integer idsubtask;
    private String type;
    private String login;
    private Integer score;

    public SubtaskItem(Integer idsubtask, String login, String type, Integer score) {
        this.score = score;
        this.login = login;
        this.type = type;
        this.idsubtask = idsubtask;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getIdsubtask() {
        return idsubtask;
    }

    public void setIdsubtask(Integer idsubtask) {
        this.idsubtask = idsubtask;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
