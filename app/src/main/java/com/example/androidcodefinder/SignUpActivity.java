package com.example.androidcodefinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class SignUpActivity extends AppCompatActivity {
    private Button btnLogin,btnSignup;
    private EditText editText;
    private EditText editText2;
    private EditText editText3;
    UserDataHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        btnLogin = findViewById(R.id.btnLogin);
        btnSignup = findViewById(R.id.btnSignup);

        editText = findViewById(R.id.editText);
        editText2 = findViewById(R.id.editText2);
        editText3 = findViewById(R.id.editText3);
        Button buttonSignup = findViewById(R.id.buttonSignup);
        dbHelper = new UserDataHelper(this);

        btnLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        buttonSignup.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(validateForm()){
                    String email = editText.getText().toString().trim();
                    String password = editText2.getText().toString().trim();

                    //Khoi tao doi tuong User voi thong tin dang ky
                    User user = new User(email, password);

                    //Them du lieu vao database
                    dbHelper.addUser(user.getEmail() , user.getPassword());
                    Toast.makeText(SignUpActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                }
        });
    }
    private boolean validateForm() {
        boolean valid = true;
        //kiem tra email
        String email = editText.getText().toString().trim();
        if (TextUtils.isEmpty(email)) {
            editText.setError("Email khong duoc de trong");
            valid = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editText.setError("Email khong hop le");
            valid = false;
        } else {
            editText.setError(null);
        }

        String password = editText2.getText().toString().trim();
        if (TextUtils.isEmpty(password)) {
            editText2.setError("Mat khau khong duoc de trong");
            valid = false;
        } else if (password.length() < 8) {
            editText2.setError("Mat khau phai co it nhat 8 ky tu");
            valid = false;
        } else if (!password.matches("(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+")) {
            editText2.setError("Mat khau phai chua it nhat mot chu cai viet hoa, mot chu cai viet thuong, mot so va mot ky tu dac biet");
            valid = false;
        } else {
            editText2.setError(null);
        }

        String confirmPassword = editText3.getText().toString().trim();
        if (TextUtils.isEmpty(confirmPassword)) {
            editText3.setError("Xac nhan mat khau khong duoc de trong");
            valid = false;
        } else if (!confirmPassword.equals(password)) {
            editText3.setError("Xac nhan mat khau khong khop");
            valid = false;
        } else {
            editText3.setError(null);
        }
        return valid;
    }
}