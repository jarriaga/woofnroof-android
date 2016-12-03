package com.woofnroof.woofnroofapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.transition.Fade;
import android.transition.TransitionInflater;
import android.util.Log;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by jbarron on 11/13/16.
 */

public class SplashScreen extends AppCompatActivity {
    @Override
    protected void onResume() {
        super.onResume();
        setupWindowAnimations();
    }

    @Override
    protected void onPause() {
        super.onPause();
        setupWindowAnimations();
    }

    private static final String TAG = "Woof - SPLASHSCREEN";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupWindowAnimations();
        String tokenWoof = User.getTokenWoof();
        //Check for previous Token and then launch
        if(tokenWoof.length()>0){
                loginTokenWoof(tokenWoof);
        }else{
            //If tokenWoof doesn't exists then launch login
            launchLoginScreen();
        }
    }


    private void setupWindowAnimations() {
        Fade fade = (Fade) TransitionInflater.from(this).inflateTransition(R.transition.activity_fade);
        getWindow().setExitTransition(fade);
        getWindow().setEnterTransition(fade);
    }

    /**
     * Launch the Login Screen
     */
    private void launchLoginScreen(){
        Intent intent = new Intent(this,LoginScreen.class);
        startActivity(intent);
        finish();
    }


    /**
     * Method to Log in a user using the tokenWoof
     * and get the new refreshed token  and Launch the MainScreen
     * @param tokenWoof
     */
    private void loginTokenWoof(final String tokenWoof) {
        RequestParams params = new RequestParams();
        //Call API to login using tokenWoof
        WoofnRoofApi.post("/api/login/token", params,tokenWoof, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try{
                    //Get the new token and store the new refreshed token
                    User.storeTokenWoof(response.getString("token"));
                    //Launch MainScreen
                    Intent intent = new Intent(getApplicationContext(),MainScreen.class);
                    startActivity(intent);
                    finish();

                }catch (JSONException e){

                }
            }

            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                try {
                    Log.e(TAG, errorResponse.getString("error"));
                    launchLoginScreen();
                }catch (JSONException e){

                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.e(TAG,responseString);
                launchLoginScreen();
            }


        });
    }

}
