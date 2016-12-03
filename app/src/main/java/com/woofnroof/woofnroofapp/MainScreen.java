package com.woofnroof.woofnroofapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Fade;
import android.transition.TransitionInflater;

import com.facebook.login.LoginManager;

public class MainScreen extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupWindowAnimations();
        setContentView(R.layout.activity_main_screen);

        LoginManager loginManager = LoginManager.getInstance();
        loginManager.logOut();
    }

    private void setupWindowAnimations() {
        Fade fade = (Fade) TransitionInflater.from(this).inflateTransition(R.transition.activity_fade);
        getWindow().setEnterTransition(fade);
    }
}
