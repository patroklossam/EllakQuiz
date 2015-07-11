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


public class GameCategoryActivity extends Activity {

    private Button cat1;
    private Button cat2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.game_category_activity);


        //TODO: handle buttons here
        cat1 = (Button) findViewById(R.id.button);
        cat1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((EllakQuiz) getApplicationContext()).setCategory(Category.CATEGORY1);

                ((EllakQuiz)getApplicationContext()).initScenario();

                finish();
                Intent intent = new Intent(GameCategoryActivity.this,GameActivity.class);
                startActivity(intent);
            }
        });

        cat2 = (Button) findViewById(R.id.button2);
        cat2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((EllakQuiz) getApplicationContext()).setCategory(Category.CATEGORY2);

                ((EllakQuiz)getApplicationContext()).initScenario();

                finish();
                Intent intent = new Intent(GameCategoryActivity.this,GameActivity.class);
                startActivity(intent);
            }
        });


    }



}
