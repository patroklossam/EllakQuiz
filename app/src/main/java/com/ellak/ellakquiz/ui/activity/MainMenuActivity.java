package com.ellak.ellakquiz.ui.activity;

import com.ellak.ellakquiz.EllakQuiz;
import com.ellak.ellakquiz.GameScenario;
import com.ellak.ellakquiz.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;


public class MainMenuActivity extends Activity {

    private Button newGame_simple;
    private Button newGame_timed;
    private Button loadGame;
    private Button statistics;
    private Button logout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_main_menu);


        //TODO: handle buttons here
        newGame_simple = (Button) findViewById(R.id.button);
        newGame_simple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((EllakQuiz)getApplicationContext()).setTime(false);

                finish();
                Intent intent = new Intent(MainMenuActivity.this,GameOptionActivity.class);
                startActivity(intent);


            }
        });

        newGame_timed = (Button) findViewById(R.id.button2);
        newGame_timed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((EllakQuiz)getApplicationContext()).setTime(true);

                finish();
                Intent intent = new Intent(MainMenuActivity.this,GameOptionActivity.class);
                startActivity(intent);
            }
        });

        loadGame = (Button) findViewById(R.id.button3);
        loadGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    ObjectInputStream ois = new ObjectInputStream(new FileInputStream("savegame.elk"));
                    ((EllakQuiz)getApplicationContext()).setScenario((GameScenario) ois.readObject());
                    ois.close();
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
                finish();
                Intent intent = new Intent(MainMenuActivity.this,GameActivity.class);
                startActivity(intent);

            }
        });

        statistics = (Button) findViewById(R.id.button4);
        statistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });

        logout = (Button) findViewById(R.id.button5);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ((EllakQuiz)getApplicationContext()).reset();

                finish();
                Intent intent = new Intent(MainMenuActivity.this,GreetActivity.class);
                startActivity(intent);

            }
        });

    }



}
