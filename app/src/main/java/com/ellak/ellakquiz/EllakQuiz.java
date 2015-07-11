package com.ellak.ellakquiz;

import android.app.Application;

/**
 * Created by Patroklos on 7/10/15.
 */
public class EllakQuiz extends Application {

    private long user_id;

    private boolean time;

    private Category category;

    private GameScenario scenario;

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public boolean isTime() {
        return time;
    }

    public void setTime(boolean time) {
        this.time = time;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public GameScenario getScenario() {
        return scenario;
    }

    public void setScenario(GameScenario scenario) {
        this.scenario = scenario;
    }


    public void reset(){
        user_id = -1L;
        time = false;
        category=Category.NO;
        scenario = null;
    }

    public void initScenario(){
        scenario = new GameScenario();
        try {
            scenario.init(category);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
