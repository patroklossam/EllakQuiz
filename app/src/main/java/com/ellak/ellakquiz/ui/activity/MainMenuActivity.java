package com.ellak.ellakquiz.ui.activity;

import com.ellak.ellakquiz.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;


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


            }
        });

        newGame_timed = (Button) findViewById(R.id.button2);
        newGame_timed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });

        loadGame = (Button) findViewById(R.id.button3);
        loadGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


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


            }
        });

    }



}
