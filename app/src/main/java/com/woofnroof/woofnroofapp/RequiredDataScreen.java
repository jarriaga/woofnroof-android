package com.woofnroof.woofnroofapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class RequiredDataScreen extends AppCompatActivity {

    private EditText inputName;
    private EditText inputEmail;
    private EditText inputPhone;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_required_data_screen);
        Intent i = getIntent();
        //Load user object
        user = (User) i.getSerializableExtra("User");
        //fill inputs
        inputName = (EditText) findViewById(R.id.inputName);
        inputName.setText(user.getName());
        inputEmail = (EditText) findViewById(R.id.inputEmail);
        inputEmail.setText(user.getEmail());
        inputPhone = (EditText) findViewById(R.id.inputPhone);

    }

    @Override
    protected void onStart() {
        super.onStart();
        //set create account events
        Button createAccountButton = (Button) findViewById(R.id.createAccountButton);
        createAccountButton.setOnClickListener(new View.OnClickListener() {
            //if the button is clicked
            @Override
            public void onClick(View v) {

                //if required fields are valid
                if(isValidRequiredInput()){


                    String tokenWoof = User.getTokenWoof();
                    RequestParams params = new RequestParams();
                    params.put("mobile",inputPhone.getText());
                    params.put("name",inputName.getText());

                    WoofnRoofApi.put("/api/profile",params,tokenWoof,new JsonHttpResponseHandler(){
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            try{
                                String successMsg = response.getString("success");
                                //Launch MainScreen
                                Intent intent = new Intent(getApplicationContext(),MainScreen.class);
                                startActivity(intent);
                                finish();
                            }catch (JSONException e){

                            }
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                            try {
                                Log.i("api", errorResponse.getString("error"));
                            }catch (JSONException e){

                            }
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                            Log.i("api",responseString);
                        }
                    });


                }
            }
        });

    }


    /**
     * input text validation
     * @return
     */

    private Boolean isValidRequiredInput(){
        Boolean error = false;

        //validations
        if(inputPhone.getText().toString().trim().equals("")){
            error=true;
            inputPhone.setError(getString(R.string.error_required_phone));
        }

        if(inputName.getText().toString().trim().equals("")){
            error=true;
            inputName.setError(getString(R.string.error_required_name));
        }
        if(inputName.getText().toString().trim().length()>0 && inputName.getText().toString().trim().length()<3){
            error=true;
            inputName.setError(getString(R.string.error_name_length));
        }

        if( !Patterns.EMAIL_ADDRESS.matcher(inputEmail.getText().toString()).matches() ){
            error=true;
            inputEmail.setError(getString(R.string.error_required_email));
        }

        return !error;
    }
}
