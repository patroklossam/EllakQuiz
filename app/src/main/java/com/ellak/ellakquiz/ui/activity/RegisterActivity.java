package com.ellak.ellakquiz.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ellak.ellakquiz.R;

import java.security.MessageDigest;
import java.util.regex.Pattern;

/**
 * Created by Veruz on 3/7/2015.
 */
public class RegisterActivity extends Activity{
    private String PASSKEY;
    private View registerProgressView;
    private View registerFormView;
    private UserRegisterTask registerTask;
    private EditText usernameEditText;
    private EditText emailEditText;
    private EditText passwordEditText, passwordEditText2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
        registerProgressView =findViewById(R.id.registerProgressBar);
        registerFormView = findViewById(R.id.registerForm);



        usernameEditText = (EditText) findViewById(R.id.usernameEditText);
        emailEditText = (EditText) findViewById(R.id.emailEditText);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        passwordEditText2 = (EditText) findViewById(R.id.re_passwordEditText);
        Button registerBtn = (Button) findViewById(R.id.createAccountButton);
        registerBtn.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {

                   attemptRegister();

               }
        });
    }




    public boolean attemptRegister(){
        boolean cancel =false;
        usernameEditText.setError(null);
        emailEditText.setError(null);
        passwordEditText.setError(null);
        passwordEditText2.setError(null);

        View focusView = null;
        if(!isValidUsername(usernameEditText.getText().toString())){
            usernameEditText.setError("Το όνομα χρήστη πρέπει να είναι τουλάχιστον 8 χαρακτήρες");
            focusView = usernameEditText;
            cancel = true;
        }
        if(userNameExists(usernameEditText.getText().toString())){
            usernameEditText.setError("Υπάρχει ήδη...");
            focusView = usernameEditText;
            cancel = true;
        }
        if (!isEmailValid(emailEditText.getText().toString())){
            emailEditText.setError("Μή έκγυρο ή κενό");
            focusView = emailEditText;
            cancel = true;
        }
        if(emailExists(emailEditText.getText().toString())){
            emailEditText.setError("Υπάρχει ήδη λογαριασμός με αυτό το email.");
            focusView = emailEditText;
            cancel = true;
        }
        if(passwordEditText.getText().toString().equals(passwordEditText2.getText().toString())) {
            if (!isValid(passwordEditText.getText().toString())) {
                passwordEditText.setError("Επιλέξτε μεγαλύτερο κωδικό");
                focusView = passwordEditText;
                cancel = true;
            }
            if (!isValid(passwordEditText2.getText().toString())) {
                passwordEditText2.setError("Επιλέξτε μεγαλύτερο κωδικό");
                focusView = passwordEditText2;
                cancel = true;
            }
        }else{
            passwordEditText.setError("Οι κωδικοί δεν ταιριάζουν");
            focusView = passwordEditText;
            cancel = true;
        }



        if(cancel){
            focusView.requestFocus();
        }
        else {
            showProgress(true);
            registerTask = new UserRegisterTask();
            registerTask.execute();
        }
        return cancel;
    }

    public final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
    );


    private boolean isValidUsername(String field){
        if(field.length()<8)
            return false;
        return true;
    }

    private boolean emailExists(String email){
        //TODO: method to check if user with this email exists in database
        return false;
    }

    private boolean userNameExists(String uname){
        //TODO: method to check if user with this username exists in database
        return false;
    }


    private boolean isEmailValid(String email){
        boolean res = false;
        if(EMAIL_ADDRESS_PATTERN.matcher(email).matches() && !TextUtils.isEmpty(email)){
            res = true;
        }
        return res;
    }

    private boolean isValid(String field){
        boolean res = false;
        if(field.length() > 3 && !TextUtils.isEmpty(field)){
            res = true;
        }
        return res;
    }
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            registerFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            registerFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    registerFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            registerProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            registerProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    registerProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            registerProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            registerFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }
    public class UserRegisterTask extends AsyncTask<Void,Void,Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {

            // hash the password so that no-one can see the real one
            try {
                MessageDigest md = MessageDigest.getInstance("SHA-256");
                md.update(passwordEditText.getText().toString().getBytes("UTF-8"));
                PASSKEY = md.digest().toString();

                //TODO: database handling
                //DatabaseAPI.execute(ApiActions.SAVE_USER,usernameEditText.getText(), emailEditText.getText().toString(), PASSKEY);

                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }

            //TODO: change to false when online
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            showProgress(false);
            if(aBoolean) {
                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                builder.setMessage("Εγγραφή Επιτυχής! Παρακαλώ Συνδεθείτε!")
                        .setCancelable(false)
                        .setPositiveButton("OK",new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int id) {
                                finish();
                                Intent intent = new Intent(RegisterActivity.this,GreetActivity.class);
                                startActivity(intent);
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                // finish();
            }
        }
    }

}
