package com.example.thuy_study;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    private EditText emailEdit, passwordEdit;
    private Switch rememberSwitch;
    private Button loginButton;
    private ImageView showHidePass;
    private boolean isPasswordVisible = false;
    private SharedPreferences sharedPreferences;
    private static final String PREF_NAME = "LoginPrefs";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_REMEMBER = "remember";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        // Ánh xạ các view
        emailEdit = findViewById(R.id.editText21);
        passwordEdit = findViewById(R.id.editText20);
        rememberSwitch = findViewById(R.id.sw1);
        loginButton = findViewById(R.id.button1);
        showHidePass = findViewById(R.id.showHidePass);

        // Khởi tạo SharedPreferences
        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);

        // Load thông tin đăng nhập đã lưu
        if(sharedPreferences.getBoolean(KEY_REMEMBER, false)) {
            emailEdit.setText(sharedPreferences.getString(KEY_EMAIL, ""));
            passwordEdit.setText(sharedPreferences.getString(KEY_PASSWORD, ""));
            rememberSwitch.setChecked(true);
        }

        // Xử lý show/hide password
        showHidePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isPasswordVisible) {
                    // Hiện password
                    passwordEdit.setInputType(InputType.TYPE_CLASS_TEXT |
                            InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    showHidePass.setImageResource(android.R.drawable.ic_lock_lock);
                    isPasswordVisible = true;
                } else {
                    // Ẩn password
                    passwordEdit.setInputType(InputType.TYPE_CLASS_TEXT |
                            InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    showHidePass.setImageResource(android.R.drawable.ic_lock_idle_lock);
                    isPasswordVisible = false;
                }
                // Đặt con trỏ về cuối text
                passwordEdit.setSelection(passwordEdit.getText().length());
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateInput()) {
                    String email = emailEdit.getText().toString();
                    String password = passwordEdit.getText().toString();

                    // Lưu thông tin đăng nhập nếu Remember me được chọn
                    if(rememberSwitch.isChecked()) {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(KEY_EMAIL, email);
                        editor.putString(KEY_PASSWORD, password);
                        editor.putBoolean(KEY_REMEMBER, true);
                        editor.apply();
                    } else {
                        // Xóa thông tin đã lưu
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.clear();
                        editor.apply();
                    }

                    Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }

    private boolean validateInput() {
        boolean isValid = true;

        // Kiểm tra email format
        String email = emailEdit.getText().toString().trim();
        if(email.isEmpty()) {
            emailEdit.setError("Email is required");
            isValid = false;
        } else if (!isValidEmail(email)) {
            emailEdit.setError("Invalid email format");
            isValid = false;
        }

        // Kiểm tra password
        if(passwordEdit.getText().toString().isEmpty()) {
            passwordEdit.setError("Password is required");
            isValid = false;
        }

        return isValid;
    }

    private boolean isValidEmail(String email) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        return email.matches(emailPattern);
    }
}