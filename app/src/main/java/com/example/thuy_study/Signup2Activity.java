package com.example.thuy_study;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.widget.CompoundButton;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

public class Signup2Activity extends AppCompatActivity {
    private EditText usernameEdit, emailEdit, passwordEdit;
    private Switch policySwitch;
    private Button signupButton;
    private ImageView showHidePass;
    private boolean isPasswordVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_signup2);

        // Ánh xạ các view
        usernameEdit = findViewById(R.id.editTextt);
        emailEdit = findViewById(R.id.editTexttt);
        passwordEdit = findViewById(R.id.editText3);
        policySwitch = findViewById(R.id.switch2);
        signupButton = findViewById(R.id.button3);
        showHidePass = findViewById(R.id.showHidePass);

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

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateInput()) {
                    // Xử lý đăng ký ở đây
                    String username = usernameEdit.getText().toString();
                    String email = emailEdit.getText().toString();
                    String password = passwordEdit.getText().toString();

                    // Kiểm tra lại một lần nữa về Policy
                    if(!policySwitch.isChecked()) {
                        showPolicyError();
                        return;
                    }

                    // Hiển thị thông báo đăng ký thành công
                    Toast.makeText(Signup2Activity.this,
                            "Sign up successful!", Toast.LENGTH_SHORT).show();

                    // Chuyển đến màn hình login
                    startActivity(new Intent(Signup2Activity.this, LoginActivity.class));
                    finish();
                }
            }
        });
    }

    private void showPolicyError() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Terms & Policy Required")
                .setMessage("Please accept our Terms and Policy to continue.")
                .setPositiveButton("OK", null)
                .show();
    }

    private boolean validateInput() {
        boolean isValid = true;

        // Kiểm tra username
        if(usernameEdit.getText().toString().trim().isEmpty()) {
            usernameEdit.setError("Username is required");
            isValid = false;
        }

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
        String password = passwordEdit.getText().toString();
        if(password.isEmpty()) {
            passwordEdit.setError("Password is required");
            isValid = false;
        }

        // Kiểm tra policy
        if(!policySwitch.isChecked()) {
            Toast.makeText(this, "Please accept the Policy and Terms", Toast.LENGTH_SHORT).show();
            isValid = false;
        }

        return isValid;
    }

    // Thêm hàm kiểm tra định dạng email
    private boolean isValidEmail(String email) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        return email.matches(emailPattern);
    }
}