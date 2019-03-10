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

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "RegisterActivity";

    private EditText etEmail, etPassword, etConfirmPassword;
    private Button btnRegister, btnNeedLogin;
    private ProgressBar pbLoading;

    private FirebaseAuth mFirebaseAuth;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, RegisterActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

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
        etConfirmPassword = findViewById(R.id.activity_register_et_confirm_password);
        btnNeedLogin = findViewById(R.id.activity_regsiter_btn_need_login);
        btnRegister = findViewById(R.id.activity_register_btn_register);
        pbLoading = findViewById(R.id.activity_register_pb_loading);
    }

    private void setupFirebase() {
        mFirebaseAuth = FirebaseAuth.getInstance();
    }

    private void addEvents() {
        btnRegister.setOnClickListener(this);
        btnNeedLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int idButton = v.getId();
        switch (idButton) {
            case R.id.activity_register_btn_register: {
                handleRegister();
                break;
            }
            case R.id.activity_regsiter_btn_need_login: {
                jumpLoginActivity();
                break;
            }
        }
    }

    private void handleRegister() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String confirmPassword = etConfirmPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPassword)) {
            Toast.makeText(this, "Fields is required", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.length() < 6) {
            Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Password doesn't match comfirm password", Toast.LENGTH_SHORT).show();
            return;
        }

        pbLoading.setVisibility(View.VISIBLE);

        mFirebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "REGISTER ACCOUNT SUCCESSFUL: " + task.getResult());
                            jumpMainActivity();
                        } else {
                            Toast.makeText(RegisterActivity.this, "Register account failed", Toast.LENGTH_SHORT).show();
                            Log.w(TAG, "REGISTER ACCOUNT FAILED", task.getException());
                        }
                        pbLoading.setVisibility(View.GONE);
                    }
                });
    }

    private void jumpMainActivity() {
        Intent intentMain = MainActivity.getStartIntent(RegisterActivity.this);
        startActivity(intentMain);
        finish();
    }

    private void jumpLoginActivity() {
        Intent intentLogin = LoginActivity.getStartIntent(RegisterActivity.this);
        startActivity(intentLogin);
        finish();
    }
}
