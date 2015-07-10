package com.ellak.ellakquiz;

import android.app.Application;

/**
 * Created by Patroklos on 7/10/15.
 */
public class EllakQuiz extends Application {

    private long user_id;


    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }
}
