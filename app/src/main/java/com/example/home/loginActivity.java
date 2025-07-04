package com.example.home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import model.ResponseModel;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class loginActivity extends AppCompatActivity {

    private EditText etUsername, etPassword;
    private CheckBox cbShowPassword, cbRememberPassword;
    private Button btnLogin;
    private SharedPreferences sharedPreferences;
    private UserDataManager userDataManager;
    private Handler handler = new Handler(Looper.getMainLooper());

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        userDataManager = UserDataManager.getInstance();
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        cbShowPassword = findViewById(R.id.checkBoxShowPassword);
        cbRememberPassword = findViewById(R.id.checkBoxRememberPassword);
        btnLogin = findViewById(R.id.button);
        sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE);

        checkRememberPassword();
        setupShowPassword();
        setupLoginButton();

        findViewById(R.id.textView4).setOnClickListener(v -> {
            Intent intent = new Intent(loginActivity.this, regestActivity.class);
            startActivity(intent);
        });
    }

    private void checkRememberPassword() {
        boolean isRemembered = sharedPreferences.getBoolean("remember_password", false);
        if (isRemembered) {
            etUsername.setText(sharedPreferences.getString("username", ""));
            etPassword.setText(sharedPreferences.getString("password", ""));
            cbRememberPassword.setChecked(true);
        }
    }

    private void setupShowPassword() {
        cbShowPassword.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
            etPassword.setSelection(etPassword.getText().length());
        });
    }

    private void setupLoginButton() {
        btnLogin.setOnClickListener(v -> {
            final String username = etUsername.getText().toString().trim();
            final String password = etPassword.getText().toString().trim();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "用户名和密码不能为空", Toast.LENGTH_SHORT).show();
                return;
            }

            btnLogin.setEnabled(false);
            btnLogin.setText("登录中...");

            userDataManager.verifyLogin(username, password, new UserDataManager.LoginCallback() {
                @Override
                public void onLoginSuccess(ResponseModel responseModel) {
                    handler.post(() -> {
                        btnLogin.setEnabled(true);
                        btnLogin.setText("登录");

                        if (responseModel.isSuccess()) {
                            saveRememberInfo(username, password);
                            Toast.makeText(loginActivity.this,
                                    responseModel.getMessage(),
                                    Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(loginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(loginActivity.this,
                                    responseModel.getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                @Override
                public void onLoginFailed(String errorMsg) {
                    handler.post(() -> {
                        btnLogin.setEnabled(true);
                        btnLogin.setText("登录");
                        Toast.makeText(loginActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
                    });
                }
            });
        });
    }

    private void saveRememberInfo(String username, String password) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (cbRememberPassword.isChecked()) {
            editor.putBoolean("remember_password", true);
            editor.putString("username", username);
            editor.putString("password", password);
        } else {
            editor.remove("remember_password");
            editor.remove("username");
            editor.remove("password");
        }
        editor.apply();
    }
}