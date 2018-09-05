package com.example.admin.myneofeedback.Views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.admin.myneofeedback.Common.SessionManager;
import com.example.admin.myneofeedback.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    SessionManager sessionManager;

    EditText editText_number, editText_password;
    Button button_login;

    private String login_number = "0000", login_pwd = "0000";
    String number, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sessionManager = new SessionManager(this);

        editText_number = findViewById(R.id.login_et_number);
        editText_password = findViewById(R.id.login_et_password);
        button_login = findViewById(R.id.login_btn_login);

        button_login.setOnClickListener(this);


    }

    private void method_login() {
        number = editText_number.getText().toString();
        password = editText_password.getText().toString();

        if (number.equals(login_number) && password.equals(login_pwd)) {

            sessionManager.createLoginSession(number, password);

            finish();
            Intent i = new Intent(this, MainNavigationActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);

        } else {
            if (number.equals("")) {
                editText_number.setError("Number required");
                editText_number.requestFocus();
            } else if (number.length() < 10) {
                editText_number.setError("Not a valid number");
                editText_number.requestFocus();
            } else if (password.equals("")) {
                editText_password.setError("Password required");
                editText_password.requestFocus();
            } else if (!password.equals(login_pwd)) {
                editText_password.setError("Wrong password");
                editText_password.requestFocus();
            }

        }

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.login_btn_login:
                method_login();
                break;

        }
    }


}
