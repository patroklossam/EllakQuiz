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

/**
 * Created by Patroklos on 7/10/15.
 */
public class DatabaseAPI {




    private static final String endpoint = "http://83.212.98.207:8080/";
    private static final String USER_AGENT = "Mozilla/5.0";
    private static String result;

    private static String saveNewUser(String login, String passkey){

        String request = "/HandlerServlet?method=new?login=" + login
                + "?passkey=" + passkey;

        return endpoint+request;
    }

    private static String login(String login, String passkey){
        String request = "/HandlerServlet?method=connect?login=" + login
                + "&passkey=" + passkey;
        return endpoint+request;
    }

    private static String getCards(int num){
        String request = "/HandlerServlet?method=getCard?howMany=" +num;
        return endpoint+request;
    }




    public static String getResponse(ApiActions action, Object... params) throws Exception {
        //base url declaration
        String url = null;

        // fields to be used
        String login;
        String passkey;


        switch(action){
            case SAVE_USER:
                login = params[0].toString();
                passkey = params[1].toString();

                url = saveNewUser(login, passkey);
                break;
            case LOGIN:
                login = params[0].toString();
                passkey = params[1].toString();

                url = login(login, passkey);
                break;
            case RETRIEVE_CARDS:
                //TODO: set the number from rounds selection
                url = getCards(20);
                break;
            default:
                break;

        }

        // add format parameter to base http request
        url+="&format=json";

        new doGet().execute(url);

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


                        return new JSONObject(String.valueOf(response)).toString();
                    }
                } catch (JSONException | IOException e) {
                    e.printStackTrace();
                }


            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            try{
                //TODO: try to use Gson
                JSONObject json = new JSONObject(s);
                result = json.get("return").toString();
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }

}
