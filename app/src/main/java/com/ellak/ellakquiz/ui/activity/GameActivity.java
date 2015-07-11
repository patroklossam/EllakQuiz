package com.ellak.ellakquiz.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.ellak.ellakquiz.Category;
import com.ellak.ellakquiz.EllakQuiz;
import com.ellak.ellakquiz.R;
import com.ellak.ellakquiz.database.entity.CardDTO;


public class GameActivity extends Activity {

    private TextView question;
    private TextView categorysub;

    private Button ans1;
    private Button ans2;
    private Button ans3;
    private Button ans4;


    private boolean selected1, selected2, selected3, selected4;


    private CardDTO card;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.game_activity);

        card= ((EllakQuiz)getApplicationContext()).getScenario().getCurrentCard();

        question = (TextView) findViewById(R.id.question);
        question.setText(card.getQuestion());


        //TODO: handle buttons here
        ans1 = (Button) findViewById(R.id.ans1);
        ans1.setText(card.getAns1());
        ans1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ans1.setBackgroundColor(getResources().getColor(R.color.orange));
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (card.getFlag1())
                    ans1.setBackgroundColor(getResources().getColor(R.color.green));
                else{
                    ans1.setBackgroundColor(getResources().getColor(R.color.red));
                    viewCorrect();
                    advance();
                }

            }
        });


        ans2 = (Button) findViewById(R.id.ans2);
        ans2.setText(card.getAns2());
        ans2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ans2.setBackgroundColor(getResources().getColor(R.color.orange));
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (card.getFlag2())
                    ans2.setBackgroundColor(getResources().getColor(R.color.green));
                else{
                    ans2.setBackgroundColor(getResources().getColor(R.color.red));
                    viewCorrect();
                    advance();
                }

            }
        });


        ans3 = (Button) findViewById(R.id.ans3);
        ans3.setText(card.getAns3());
        ans3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ans3.setBackgroundColor(getResources().getColor(R.color.orange));
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (card.getFlag3())
                    ans3.setBackgroundColor(getResources().getColor(R.color.green));
                else{
                    ans3.setBackgroundColor(getResources().getColor(R.color.red));
                    viewCorrect();
                    advance();
                }
            }
        });


        ans4 = (Button) findViewById(R.id.ans4);
        ans4.setText(card.getAns4());
        ans4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ans4.setBackgroundColor(getResources().getColor(R.color.orange));

                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if(card.getFlag4())
                    ans4.setBackgroundColor(getResources().getColor(R.color.green));
                else{
                    ans4.setBackgroundColor(getResources().getColor(R.color.red));
                    viewCorrect();
                    advance();
                }
            }
        });


    }

    private void viewCorrect(){
        if(card.getFlag1())
            ans1.setBackgroundColor(getResources().getColor(R.color.green));
        else if(card.getFlag2())
            ans2.setBackgroundColor(getResources().getColor(R.color.green));
        else if(card.getFlag3())
            ans3.setBackgroundColor(getResources().getColor(R.color.green));
        else if(card.getFlag4())
            ans4.setBackgroundColor(getResources().getColor(R.color.green));
    }

    private void advance(){
        if(((EllakQuiz)getApplicationContext()).getScenario().hasMore()) {
            ((EllakQuiz) getApplicationContext()).getScenario().nextCard();
            finish();
            Intent intent = new Intent(GameActivity.this,GameActivity.class);
            startActivity(intent);
        }else{

        }
    }



}
