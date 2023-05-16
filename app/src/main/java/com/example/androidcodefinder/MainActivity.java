package com.example.androidcodefinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button btnLogin, btnSignup , buttonLogin;
    private EditText editText;
    private EditText editText2;
    UserDataHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnSignup = (Button) findViewById(R.id.btnSignup);
        buttonLogin =(Button) findViewById(R.id.buttonLogin);
        editText =(EditText) findViewById(R.id.editText);
        editText2 =(EditText) findViewById(R.id.editText2);

        dbHelper = new UserDataHelper(this);

        btnSignup.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editText.getText().toString().trim();
                String password = editText2.getText().toString().trim();
//
                User user = new User(email,password);
                if(validateForm()){
                    if (dbHelper.getUserByEmail(user.getEmail()) && dbHelper.getUserByPassword(user.getPassword())) {
                        // Đăng nhập thành công
//                        Toast.makeText(getApplicationContext(), "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this, Home.class);
                        startActivity(intent);
                    }
               }
            }
        });

    }
    private boolean validateForm() {
        boolean valid = true;
        String email = editText.getText().toString().trim();
        String password = editText2.getText().toString().trim();
        User user = new User(email,password);
        //kiem tra email

        if (TextUtils.isEmpty(email)) {
            editText.setError("Email khong duoc de trong");
            valid = false;
        } else if (!dbHelper.getUserByEmail(user.getEmail())) {
            editText.setError("Email khong dung");
            valid = false;
        } else {
            editText.setError(null);
        }

        if (TextUtils.isEmpty(password)) {
            editText2.setError("Mat khau khong duoc de trong");
            valid = false;
        } else if (!dbHelper.getUserByPassword(user.getPassword())) {
            editText2.setError("Mat khau khong dung");
            valid = false;
        } else {
            editText2.setError(null);
        }
        return valid;
    }
}