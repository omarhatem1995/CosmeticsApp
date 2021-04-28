package com.example.blaumtask.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.blaumtask.R;
import com.example.blaumtask.presenter.signuppresenter.SignupPresenter;
import com.example.blaumtask.presenter.signuppresenter.SignupPresenterListener;
import com.example.blaumtask.utils.SpinnerDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignupActivity extends AppCompatActivity implements SignupPresenterListener {

    private FirebaseAuth mAuth;

    private EditText email ,fullname , password;
    private Button submit;
    private String TAG = "SignupActivity";

    private SignupPresenter signupPresenter;
    private SpinnerDialog spinnerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        initViews();
    }

    private void initViews(){
        email = findViewById(R.id.email);
        fullname = findViewById(R.id.fullname);
        password = findViewById(R.id.password);

        submit = findViewById(R.id.submit_signup);

        mAuth = FirebaseAuth.getInstance();
        spinnerDialog = new SpinnerDialog(this);
        signupPresenter = new SignupPresenter(this,this, this);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fullNameString = fullname.getText().toString();
                String emailString = email.getText().toString().trim();
                String passwordString = password.getText().toString();
                signupPresenter.submitRegister(fullNameString,emailString,passwordString);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
        }
    }

    @Override
    public void showProgress() {
        if(spinnerDialog!=null)
        spinnerDialog.show();
    }

    @Override
    public void hideProgress() {
        if(spinnerDialog!=null && spinnerDialog.isShowing()){
            spinnerDialog.hide();
        }
    }
}