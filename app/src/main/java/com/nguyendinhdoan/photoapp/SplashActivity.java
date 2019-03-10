package com.nguyendinhdoan.photoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        // if user doesn't sign in
        if (user == null) {
            Intent intentLogin = LoginActivity.getStartIntent(SplashActivity.this);
            startActivity(intentLogin);
            finish();
        } else {
            // if user exist jump to MainActivity
            Intent intentMain = MainActivity.getStartIntent(SplashActivity.this);
            startActivity(intentMain);
            finish();
        }
    }
}
