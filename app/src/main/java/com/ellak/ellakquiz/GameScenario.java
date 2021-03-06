package com.ellak.ellakquiz;

import android.widget.Button;

import com.ellak.ellakquiz.database.ApiActions;
import com.ellak.ellakquiz.database.entity.CardDTO;
import com.ellak.ellakquiz.database.handlerAPI.DatabaseAPI;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Patroklos on 7/11/15.
 */
public class GameScenario implements Serializable{


    private List<CardDTO> cards;
    private int correct;
    private int current;

    private boolean timer;


    public GameScenario(){
        this.correct = 0;
    }

    public List<CardDTO> getCards() {
        return cards;
    }

    public void setCards(List<CardDTO> cards) {
        this.cards = cards;
    }

    public int getCorrect() {
        return correct;
    }

    public void setCorrect(int correct) {
        this.correct = correct;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public boolean isTimer() {
        return timer;
    }

    public void setTimer(boolean timer) {
        this.timer = timer;
    }

    public void increaseCorrect(){
        correct++;
    }

    public void decreaseCurrentOnSave(){
        current--;
    }

    public void init(Category ctg, int count) throws Exception {

        Gson gson = new Gson();

        String cat = "";

        switch (ctg){
            case CATEGORY1:
                cat="1";
                break;
            case CATEGORY2:
                cat="2";
                break;
            case NO:
                cat = "NO";
                break;
            default:
                cat="NO";
                break;
        }

        String resp = DatabaseAPI.getResponse(ApiActions.RETRIEVE_CARDS,count,cat).toString();


        CardDTO[] cardArray = gson.fromJson(resp,CardDTO[].class);

        cards = new ArrayList<>();

        for(CardDTO card : cardArray)
            cards.add(card);


        current =0;
    }

    public CardDTO getCurrentCard(){
        CardDTO card =  cards.get(current);
        current++;

        return card;
    }

    public boolean hasMore(){
        if(current < cards.size()-1)
            return true;
        return false;
    }

    public Double getScore(){
        return ((double)correct/(cards.size()-1))*100;
    }

}
