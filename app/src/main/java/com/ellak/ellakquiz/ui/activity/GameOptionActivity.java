package com.ellak.ellakquiz.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.ellak.ellakquiz.Category;
import com.ellak.ellakquiz.EllakQuiz;
import com.ellak.ellakquiz.R;


public class GameOptionActivity extends Activity {

    private Button newGame_nocat;
    private Button newGame_cat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.game_option_activity);


        //TODO: handle buttons here
        newGame_nocat = (Button) findViewById(R.id.button);
        newGame_nocat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((EllakQuiz)getApplicationContext()).setCategory(Category.NO);

                ((EllakQuiz)getApplicationContext()).initScenario(5);


                finish();
                Intent intent = new Intent(GameOptionActivity.this,GameActivity.class);
                startActivity(intent);

            }
        });

        newGame_cat = (Button) findViewById(R.id.button2);
        newGame_cat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intent = new Intent(GameOptionActivity.this,GameCategoryActivity.class);
                startActivity(intent);
            }
        });


    }



}
