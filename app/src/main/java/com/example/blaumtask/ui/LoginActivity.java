package com.example.blaumtask.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.blaumtask.R;
import com.example.blaumtask.presenter.loginpresenter.LoginPresenter;
import com.example.blaumtask.presenter.loginpresenter.LoginPresenterListener;
import com.example.blaumtask.utils.SpinnerDialog;

public class LoginActivity extends AppCompatActivity implements LoginPresenterListener {

    private SpinnerDialog spinnerDialog;

    private TextView orRegister;
    private EditText email , password;
    private Button submit;

    private LoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();
    }

    private void initViews(){
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        orRegister = findViewById(R.id.or_register);
        submit = findViewById(R.id.submit_login);
        spinnerDialog = new SpinnerDialog(this);

        loginPresenter = new LoginPresenter(this,this,this);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailString = email.getText().toString().trim();
                String passwordString = password.getText().toString();
                loginPresenter.submitLogin(emailString , passwordString);
            }
        });
        orRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,SignupActivity.class));
            }
        });
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }
}