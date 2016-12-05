package com.woofnroof.woofnroofapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class LoginScreen extends AppCompatActivity {


    private CallbackManager callbackManager;
    private TextView info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //facebook callback
        callbackManager = CallbackManager.Factory.create();
        //get Login Window
        setContentView(R.layout.login_screen);
        final LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);


        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult loginResult) {
                //Graph request to facebook in order to get email, id
                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(final JSONObject object, GraphResponse response) {
                                try{

                                    //Set the facebook Token
                                    RequestParams params = new RequestParams();
                                    params.put("facebookToken",AccessToken.getCurrentAccessToken().getToken());
                                    WoofnRoofApi.post("/api/login/facebook",params,new JsonHttpResponseHandler(){
                                        @Override
                                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                            super.onSuccess(statusCode, headers, response);
                                            try {
                                                int isRequiredInfo = response.getInt("orange-info");
                                                //reading token woof
                                                String tokenWoof = response.getString("token");
                                                User.storeTokenWoof(tokenWoof);
                                                if(isRequiredInfo == 0){
                                                    //if doesn't exist then create new one
                                                    //get profile using the tokenWoof
                                                    RequestParams params = new RequestParams();
                                                    WoofnRoofApi.get("/api/profile",params,tokenWoof,new JsonHttpResponseHandler() {

                                                        @Override
                                                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                                            try{
                                                                User user = new User();
                                                                user.setId(  Integer.parseInt(response.getString("id")) );
                                                                user.setFacebookId( response.getString("facebookId") );
                                                                user.setName( response.getString("name") );
                                                                user.setEmail( response.getString("email") );
                                                                user.setPhone( response.getString("mobile") );
                                                                // Launch Orange screen
                                                                Intent intent = new Intent(getApplicationContext() ,RequiredDataScreen.class);
                                                                intent.putExtra("User",user);
                                                                startActivity(intent);
                                                                finish();
                                                                Log.e("JSON OBJECT", response.toString() );
                                                            }catch(JSONException e){

                                                            }
                                                        }

                                                        @Override
                                                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                                                            try {
                                                                Log.e("JSON ERROR", errorResponse.getString("error"));
                                                            }catch (JSONException e){

                                                            }
                                                        }

                                                        @Override
                                                        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                                                            Log.e("JSON ERROR",responseString);
                                                        }
                                                    }); //End woofnRoofAPI call

                                                }else{
                                                    //Launch MainActivity
                                                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                                    startActivity(intent);
                                                    finish();
                                                }

                                            }catch (Exception e){

                                            }
                                        }//end onSuccess

                                        @Override
                                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                                            try {
                                                Log.e("API ERROR", errorResponse.getString("error"));
                                            }catch (JSONException e){

                                            }
                                        }

                                        @Override
                                        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                                            Log.e("API ERROR",responseString);
                                        }
                                    });
                                }catch (Exception e){
                                    Log.e("Error woofnroof",e.toString());
                                }
                            } //end onCompleted graph call
                        }); //graph newMemberRequest
                //Start the facebook Request
                Bundle parameters = new Bundle();
                parameters.putString("fields","id,name,email");
                request.setParameters(parameters);
                request.executeAsync();
            } //end onSuccess

            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException error) {
            }
        }); // end LoginButton facebook callback
    } //end onCreate


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }






}
