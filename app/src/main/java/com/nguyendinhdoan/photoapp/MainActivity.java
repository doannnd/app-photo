package com.nguyendinhdoan.photoapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private Toolbar mToolbar;

    private FirebaseAuth mFirebaseAuth;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        setupFirebase();
        setupToolbar();
    }

    @Override
    protected void onStart() {
        super.onStart();
        // check if user doesn't exist to login
        FirebaseUser user = mFirebaseAuth.getCurrentUser();
        if (user == null) {
            jumLoginActivity();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuId = item.getItemId();
        switch (menuId) {
            case R.id.action_search: {
                handleSearch();
                break;
            }
            case R.id.action_settings: {
                handleSettings();
                break;
            }
            case R.id.action_logout: {
                handleLogout();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void initViews() {
        mToolbar = findViewById(R.id.activity_main_toolbar);
    }

    private void setupToolbar() {
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setTitle(getString(R.string.label_toolbar));
        }
    }

    private void setupFirebase() {
        mFirebaseAuth = FirebaseAuth.getInstance();
    }

    private void handleSearch() {
        // TODO:
    }

    private void handleSettings() {
        // TODO:
    }

    private void handleLogout() {
        mFirebaseAuth.signOut();
        jumLoginActivity();
    }

    private void jumLoginActivity() {
        Intent intentLogin = LoginActivity.getStartIntent(MainActivity.this);
        startActivity(intentLogin);
        finish();
    }
}
