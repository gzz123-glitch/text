package com.example.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import model.ResponseModel;

public class regestActivity extends AppCompatActivity {

    private EditText etUsername, etPhone, etPassword, etConfirmPassword;
    private CheckBox checkBoxShowPassword;
    private Button btnRegister;
    private UserDataManager userDataManager;
    private Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_regest);

        userDataManager = UserDataManager.getInstance();
        initViews();
        setupPasswordVisibility();
        setupRegisterButton();
        setupInputListeners();
    }
    private void initViews() {
        etUsername = findViewById(R.id.etUsername);  // ✅ 此控件对应API中的name参数
        etPhone = findViewById(R.id.etPhone);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        checkBoxShowPassword = findViewById(R.id.checkBoxShowPassword);
        btnRegister = findViewById(R.id.button);
    }

    private void setupPasswordVisibility() {
        checkBoxShowPassword.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                etPassword.setInputType(android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                etConfirmPassword.setInputType(android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            } else {
                etPassword.setInputType(android.text.InputType.TYPE_CLASS_TEXT |
                        android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD);
                etConfirmPassword.setInputType(android.text.InputType.TYPE_CLASS_TEXT |
                        android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD);
            }
            etPassword.setSelection(etPassword.getText().length());
            etConfirmPassword.setSelection(etConfirmPassword.getText().length());
        });
    }

    private void setupRegisterButton() {
        btnRegister.setOnClickListener(v -> {
            String name = etUsername.getText().toString().trim();  // ✅ username改为name
            String phone = etPhone.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            String confirmPassword = etConfirmPassword.getText().toString().trim();

            if (name.isEmpty() || phone.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "所有字段均不能为空", Toast.LENGTH_SHORT).show();
                return;
            }
            if (phone.length() != 11 || !phone.matches("^1[3-9]\\d{9}$")) {
                Toast.makeText(this, "请输入正确的11位手机号", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!password.equals(confirmPassword)) {
                Toast.makeText(this, "两次输入的密码不一致", Toast.LENGTH_SHORT).show();
                return;
            }

            btnRegister.setEnabled(false);
            btnRegister.setText("注册中...");

            // ✅ 注意参数顺序：name, password, phone
            userDataManager.registerUser(name, password, phone, new UserDataManager.RegisterCallback() {
                @Override
                public void onRegisterSuccess(ResponseModel responseModel) {
                    handler.post(() -> {
                        btnRegister.setEnabled(true);
                        btnRegister.setText("注册");
                        Toast.makeText(regestActivity.this,
                                responseModel.getMessage(),
                                Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(regestActivity.this, loginActivity.class);
                        startActivity(intent);
                        finish();
                    });
                }

                @Override
                public void onRegisterFailed(String errorMsg) {
                    handler.post(() -> {
                        btnRegister.setEnabled(true);
                        btnRegister.setText("注册");
                        Toast.makeText(regestActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
                    });
                }
            });
        });
    }

    private void setupInputListeners() {
        // 用户名输入监听
        etUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                String username = s.toString();
                if (!username.matches("^[a-zA-Z0-9_]{3,16}$")) {
                    etUsername.setError("仅支持字母、数字、下划线（3-16位）");
                } else {
                    etUsername.setError(null);
                }
            }
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        // 手机号输入监听
        etPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                String phone = s.toString();
                if (phone.length() == 11 && !phone.matches("^1[3-9]\\d{9}$")) {
                    etPhone.setError("手机号格式错误");
                } else {
                    etPhone.setError(null);
                }
            }
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        // 密码强度校验
        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                String pwd = s.toString();
                if (pwd.length() < 6) {
                    etPassword.setError("密码至少6位");
                } else {
                    etPassword.setError(null);
                }
            }
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });
    }
}