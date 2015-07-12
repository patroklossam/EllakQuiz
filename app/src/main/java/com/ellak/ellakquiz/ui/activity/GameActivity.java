package com.ellak.ellakquiz.ui.activity;

import android.app.ActionBar;
import android.os.CountDownTimer;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ellak.ellakquiz.Category;
import com.ellak.ellakquiz.EllakQuiz;
import com.ellak.ellakquiz.R;
import com.ellak.ellakquiz.database.entity.CardDTO;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.zip.Inflater;


public class GameActivity extends FragmentActivity{

    private TextView question;
    private TextView categorysub;
    private TextView timeView;
    private Chronometer chrono;
    private long paused_time;


    private Button ans1;
    private Button ans2;
    private Button ans3;
    private Button ans4;


    private boolean selected1=false, selected2=false, selected3=false, selected4=false;


    private CardDTO card;

    private static int popupaction=-2;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.game_activity);

        card = ((EllakQuiz) getApplicationContext()).getScenario().getCurrentCard();


        question = (TextView) findViewById(R.id.question);
        question.setText(card.getQuestion());

        categorysub = (TextView) findViewById((R.id.category_sub));

        if(((EllakQuiz) getApplicationContext()).getCategory()!= Category.NO)
            categorysub.setText(((EllakQuiz) getApplicationContext()).getCategory().toString());


        timeView = (TextView) findViewById(R.id.timeView);
        if(((EllakQuiz) getApplicationContext()).isTime()) {
           new CountDownTimer(31000, 1000) {

                public void onTick(long millisUntilFinished) {
                    timeView.setText("" + millisUntilFinished / 1000);
                    paused_time = millisUntilFinished / 1000;
                }


                public void onFinish() {
                    if(paused_time <=1) {
                        timeView.setText("" + 0);
                        viewCorrect();
                        advance();
                    }
                }
            }.start();
        }else{
            timeView.setText("");
        }
        //TODO: increase score in correct answers
        ans1 = (Button) findViewById(R.id.ans1);
        ans1.setText(card.getAns1());
        ans1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ans1.setBackgroundColor(getResources().getColor(R.color.yellow));
                disableButtons();
                selected1 = true;
                delayedButtonHandler();
            }
        });


        ans2 = (Button) findViewById(R.id.ans2);
        ans2.setText(card.getAns2());
        ans2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ans2.setBackgroundColor(getResources().getColor(R.color.yellow));
                disableButtons();
                selected2 = true;
                delayedButtonHandler();

            }
        });


        ans3 = (Button) findViewById(R.id.ans3);
        ans3.setText(card.getAns3());
        ans3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ans3.setBackgroundColor(getResources().getColor(R.color.yellow));
                disableButtons();
                selected3=true;
                delayedButtonHandler();
            }
        });


        ans4 = (Button) findViewById(R.id.ans4);
        ans4.setText(card.getAns4());
        ans4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ans4.setBackgroundColor(getResources().getColor(R.color.yellow));
                disableButtons();
                selected4 = true;
                delayedButtonHandler();
            }
        });

        final ImageButton pause = (ImageButton) findViewById(R.id.imageButton);
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(((EllakQuiz)getApplicationContext()).isTime())
                    stopTimer();

                LayoutInflater l_inflater = (LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = l_inflater.inflate(R.layout.pause_screen,null);

                final PopupWindow popup = new PopupWindow(popupView, ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT);

                Button resume = (Button) popupView.findViewById(R.id.btn_continue);
                resume.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popupaction = -1;
                        popup.dismiss();
                    }
                });
                Button save = (Button) popupView.findViewById(R.id.btn_save);
                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popupaction = 0;
                        popup.dismiss();
                    }
                });
                Button qq = (Button) popupView.findViewById(R.id.btn_quit);
                qq.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popupaction = 1;
                        popup.dismiss();
                    }
                });

                popup.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        switch (popupaction){
                            case -1:
                                if(((EllakQuiz)getApplicationContext()).isTime())
                                    startTimer();
                                break;
                            case 0:
                                try {
                                    ((EllakQuiz) getApplicationContext()).getScenario().decreaseCurrentOnSave();
                                    ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("savegame.elk"));
                                    oos.writeObject(((EllakQuiz) getApplicationContext()).getScenario());
                                    oos.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                finish();

                                Intent intent = new Intent(GameActivity.this,MainMenuActivity.class);
                                startActivity(intent);
                                break;
                            case 1:
                                ((EllakQuiz)getApplicationContext()).resetGame();
                                finish();

                                Intent intent2 = new Intent(GameActivity.this,MainMenuActivity.class);
                                startActivity(intent2);
                                break;
                            default:
                                break;

                        }
                    }
                });

                popup.showAsDropDown(pause, 50, -30);
                popup.setFocusable(true);
                popup.update();




/*
                RelativeLayout layout = (RelativeLayout) findViewById(R.id.pause_layout);



                PopupMenu popup = new PopupMenu(getApplicationContext(), layout);
                popup.show();*/
            }
        });

    }

    private void startTimer(){
        new CountDownTimer(paused_time, 1000) {

            public void onTick(long millisUntilFinished) {
                timeView.setText("" + millisUntilFinished / 1000);
                paused_time = millisUntilFinished / 1000;
            }


            public void onFinish() {
                if(paused_time <=1) {
                    timeView.setText("" + 0);
                    viewCorrect();
                    advance();
                }
            }
        }.start();
    }

    private void stopTimer(){
        paused_time = Long.parseLong(timeView.getText().toString());
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

        if(selected1 && !card.getFlag1())
            ans1.setBackgroundColor(getResources().getColor(R.color.red));
        if(selected2 && !card.getFlag2())
            ans2.setBackgroundColor(getResources().getColor(R.color.red));
        if(selected3 && !card.getFlag3())
            ans3.setBackgroundColor(getResources().getColor(R.color.red));
        if(selected4 && !card.getFlag4())
            ans4.setBackgroundColor(getResources().getColor(R.color.red));

        if(selected1 && card.getFlag1())
            ((EllakQuiz)getApplicationContext()).getScenario().increaseCorrect();
        else if(selected2 && card.getFlag2())
            ((EllakQuiz)getApplicationContext()).getScenario().increaseCorrect();
        else if(selected3 && card.getFlag3())
            ((EllakQuiz)getApplicationContext()).getScenario().increaseCorrect();
        else if(selected4 && card.getFlag4())
            ((EllakQuiz)getApplicationContext()).getScenario().increaseCorrect();



    }

    private void disableButtons(){
        ans1.setEnabled(false);
        ans2.setEnabled(false);
        ans3.setEnabled(false);
        ans4.setEnabled(false);
    }

    private void delayedButtonHandler(){
        final Handler handle = new Handler();
        Runnable delay = new Runnable() {
            public void run() {
                viewCorrect();
                advance();
            }
        };
        handle.postDelayed(delay,2000);
    }

    private void advance(){
        if(((EllakQuiz)getApplicationContext()).getScenario().hasMore()) {

            //delay starting new activity to let the user see the correct answer
            final Handler handle = new Handler();
            Runnable delay = new Runnable() {
                public void run() {
                    finish();
                    Intent intent = new Intent(GameActivity.this,GameActivity.class);
                    startActivity(intent);
                }
            };
            handle.postDelayed(delay,2000);
        }else{

            finish();
            Intent intent = new Intent(GameActivity.this,ResultsActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        Intent intent = new Intent(GameActivity.this,MainMenuActivity.class);
        startActivity(intent);
    }
}
