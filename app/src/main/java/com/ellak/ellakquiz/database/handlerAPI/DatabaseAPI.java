package com.ellak.ellakquiz.database.handlerAPI;

import android.os.AsyncTask;

import com.ellak.ellakquiz.database.ApiActions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.concurrent.TimeUnit;

/**
 * Created by Patroklos on 7/10/15.
 */
public class DatabaseAPI {

    private static final String endpoint = "http://83.212.98.207:8080/ellak_ws";
    private static final String USER_AGENT = "Mozilla/5.0";
    private static String result;


    private static String saveNewUser(String login, String passkey, String email){

        String request = "/HandlerServlet?method=new&login=" + login
                + "&passkey=" + passkey + "&email="+ email;

        return endpoint+request;
    }

    private static String login(String login, String passkey){
        String request = "/HandlerServlet?method=connect&login=" + login
                + "&passkey=" + passkey;
        return endpoint+request;
    }

    private static String getCards(int num, String category){
        String request = "/HandlerServlet?method=getCards&howMany=" +num
                + "&category="+category;
        return endpoint+request;
    }

    private static String saveStats(long user_id, double score, String category){
        String request = "/HandlerServlet?method=updateStats&user_id="+user_id
                +"&score=" +score
                +"&category="+category;
        return endpoint+request;
    }


    private static String getStats(long user_id){
        String request = "/HandlerServlet?method=getStats&user_id=" +user_id;
        return endpoint+request;
    }




    public static Object getResponse(ApiActions action, Object... params) throws Exception {
        //base url declaration
        String url = null;

        // fields to be used
        String login;
        String passkey;
        String email;


        switch(action){
            case SAVE_USER:
                login = params[0].toString();
                passkey = params[1].toString();
                email = params[2].toString();

                //passkey = passkey.replaceAll("@","");

                url = saveNewUser(login, passkey,email);
                break;
            case LOGIN:
                login = params[0].toString();
                passkey = params[1].toString();

                //passkey = passkey.replaceAll("@", "");
                url = login(login, passkey);
                break;
            case RETRIEVE_CARDS:
                url = getCards(Integer.parseInt(params[0].toString()), params[1].toString());
                break;
            case SAVE_STATS:
                url = saveStats(Long.parseLong(params[0].toString()),Double.parseDouble(params[1].toString()),params[1].toString());
                break;
            case GET_STATS:
                url = getStats(Long.parseLong(params[0].toString()));
                break;
            default:
                break;

        }

        // add format parameter to base http request
        url+="&format=json";

        if(action == ApiActions.SAVE_USER ||action ==  ApiActions.LOGIN) {
            try {
                HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
                // optional default is GET

                con.setRequestMethod("GET");
                con.setRequestProperty("User-Agent", USER_AGENT);

                int responseCode = con.getResponseCode();
                if (responseCode == 200) {
                    BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String inputLine;
                    StringBuffer response = new StringBuffer();

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();


                    result = response.toString();
                    if(result.contains("return")) {
                        JSONObject json = new JSONObject(result);
                        result = json.get("return").toString();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            result = new doGet().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, url).get(3000, TimeUnit.MILLISECONDS);
        }
//        new doGet().execute(url);

        return result;
    }

    private static class doGet extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... url) {
            if(url[0] != null) {
                try {
                    HttpURLConnection con = (HttpURLConnection) new URL(url[0]).openConnection();
                    // optional default is GET

                    con.setRequestMethod("GET");
                    con.setRequestProperty("User-Agent", USER_AGENT);

                    int responseCode = con.getResponseCode();
                    if (responseCode == 200) {
                        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

                        String inputLine;
                        StringBuffer response = new StringBuffer();

                        while ((inputLine = in.readLine()) != null) {
                            response.append(inputLine);
                        }
                        in.close();

                        String result = response.toString();

                        try{
                            //TODO: try to use Gson
                            if(result.contains("return")) {
                                JSONObject json = new JSONObject(result);
                                result = json.get("return").toString();
                            }
                        }catch(Exception ex){
                            ex.printStackTrace();
                        }
                        return result;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {

        }
    }

}
