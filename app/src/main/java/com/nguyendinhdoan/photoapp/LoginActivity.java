package com.nguyendinhdoan.photoapp;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "LoginActivity";

    private EditText etEmail, etPassword;
    private Button btnLogin, btnNeedRegister;
    private ProgressBar pbLoading;

    private FirebaseAuth mFirebaseAuth;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, LoginActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();
        setupFirebase();
        addEvents();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mFirebaseAuth.getCurrentUser();
        if (user != null) {
            jumpMainActivity();
        }
    }

    private void initViews() {
        etEmail = findViewById(R.id.activity_register_et_email);
        etPassword = findViewById(R.id.activity_register_et_password);
        btnLogin = findViewById(R.id.activity_login_btn_login);
        btnNeedRegister = findViewById(R.id.activity_login_btn_register);
        pbLoading = findViewById(R.id.activity_login_pb_loading);
    }

    private void setupFirebase() {
        mFirebaseAuth = FirebaseAuth.getInstance();
    }

    private void addEvents() {
        btnLogin.setOnClickListener(this);
        btnNeedRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.activity_login_btn_login: {
                handleLogin();
                break;
            }
            case R.id.activity_login_btn_register: {
                jumpRegisterActivity();
                break;
            }
        }
    }

    private void handleLogin() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Email and Password is required", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.length() < 6) {
            Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show();
            return;
        }

        pbLoading.setVisibility(View.VISIBLE);

        mFirebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, "LOGIN SUCCESSFUL: " + task.getResult());
                    jumpMainActivity();
                } else {
                    Log.w(TAG, "LOGIN FAILED: ", task.getException());
                    Toast.makeText(LoginActivity.this, "Login failed" + task.getException(), Toast.LENGTH_SHORT).show();
                }
                pbLoading.setVisibility(View.GONE);
            }
        });
    }

    private void jumpMainActivity() {
        Intent intentMain = MainActivity.getStartIntent(LoginActivity.this);
        startActivity(intentMain);
        finish();
    }

    private void jumpRegisterActivity() {
        Intent intentRegister = RegisterActivity.getStartIntent(LoginActivity.this);
        startActivity(intentRegister);
        finish();
    }
}
