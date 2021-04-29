package com.example.blaumtask.presenter.loginpresenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.blaumtask.presenter.signuppresenter.SignupPresenterListener;
import com.example.blaumtask.ui.MainActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.NonNull;

import static androidx.core.content.ContextCompat.startActivity;

public class LoginPresenter {
    private Activity activity;
    private Context context;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private String TAG = "SignupPresenter" , userId;
    private LoginPresenterListener loginPresenterListener;

    FirebaseFirestore firestore;

    public LoginPresenter(Activity activity,Context context,LoginPresenterListener loginPresenterListener){
        this.activity = activity;
        this.context = context;
        this.loginPresenterListener = loginPresenterListener;
        firestore = FirebaseFirestore.getInstance();
    }

    public void submitLogin(String email, String password){
        mAuth.signInWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                firestore.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .get().addOnCompleteListener(task -> {
                    if(task.isSuccessful() && task.getResult() != null){
                        String firstName = task.getResult().getString("FullName");
                        String email = task.getResult().getString("Email");
                        String phone = task.getResult().getString("userID");
                        Log.d(TAG,"firstName " + firstName + " " + email + " " + phone  );
                        //other stuff
                    }else{
                        //deal with error
                    }
                });
                startActivity(context,new Intent(activity, MainActivity.class),null);
                activity.finishAffinity();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(activity, "failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
