package com.ellak.ellakquiz.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ellak.ellakquiz.EllakQuiz;
import com.ellak.ellakquiz.R;
import com.ellak.ellakquiz.database.ApiActions;
import com.ellak.ellakquiz.database.handlerAPI.DatabaseAPI;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;


public class ResultsActivity extends Activity {

    @Override
    protected void onResume() {
        super.onResume();


    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_dialog);

        TextView score = (TextView) findViewById(R.id.score_field);

        score.setText(((EllakQuiz) getApplicationContext()).getScenario().getScore().toString() + " %");
        String ctg = "";
        switch (((EllakQuiz) getApplicationContext()).getCategory()){
            case NO:
                ctg = "NO";
                break;
            case CATEGORY1:
                ctg = "1";
                break;
            case CATEGORY2:
                ctg= "2";
                break;
            default:
                ctg = "NO";
                break;
        }
        try {
            DatabaseAPI.getResponse(ApiActions.SAVE_STATS, ((EllakQuiz) getApplicationContext()).getUser_id(), ((EllakQuiz) getApplicationContext()).getScenario().getScore(), ctg);

        } catch (Exception e) {
            e.printStackTrace();
        }
        Button back = (Button) findViewById(R.id.returnButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((EllakQuiz) getApplicationContext()).resetGame();

                finish();
                Intent intent = new Intent(ResultsActivity.this, MainMenuActivity.class);
                startActivity(intent);
            }
        });

    }
}
