package com.woofnroof.woofnroofapp;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.loopj.android.http.AsyncHttpClient;

import java.io.Serializable;
import java.util.Date;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by jbarron on 11/20/16.
 */

public class User implements Serializable{

    private String email;
    private String name;
    private Date birthday;
    private String facebookId;
    private String phone;
    private int id;

    private boolean resultUpdate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFacebookId() {
        return facebookId;
    }

    public void setFacebookId(String facebookId) {
        this.facebookId = facebookId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }


    public static void userExistsByFacebookToken(String fbToken){
        //Todo  submit login/signup faceoobk
        AsyncHttpClient http = new AsyncHttpClient();
        //http.post()
    }

    /**
     * store the token woof API into the device
     * @param tokenWoof
     */
    public static void storeTokenWoof(String tokenWoof){
        //Save the tokenWoof in shared preferences
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("tokenWoof",tokenWoof);
         editor.commit();
    }

    /**
     * Function to get the tokenWoof
     * @return
     */
    public static String getTokenWoof(){
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String tokenWoof = settings.getString("tokenWoof","");
        return tokenWoof;
    }






}
