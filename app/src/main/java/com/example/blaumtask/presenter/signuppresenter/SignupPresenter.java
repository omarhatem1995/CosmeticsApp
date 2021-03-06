package com.example.blaumtask.presenter.signuppresenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.blaumtask.models.Signup.SignupRequestModel;
import com.example.blaumtask.ui.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;

import static androidx.core.content.ContextCompat.startActivity;

public class SignupPresenter {

    private Activity activity;
    private Context context;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private String TAG = "SignupPresenter" , userId;

    private SignupRequestModel signupRequestModel;

    FirebaseFirestore firestore;

    private SignupPresenterListener signupPresenterListener;

    public SignupPresenter(Activity activity,Context context,SignupPresenterListener signupPresenterListener){
        this.activity = activity;
        this.context = context;
        this.signupPresenterListener = signupPresenterListener;
        firestore = FirebaseFirestore.getInstance();
    }


    public void submitRegister(String fullName, String email, String password){

        signupRequestModel = new SignupRequestModel();
        signupRequestModel.setFullname(fullName);
        signupRequestModel.setEmail(email);
        signupRequestModel.setPassword(password);

        signupPresenterListener.showProgress();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            userId = mAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = firestore.collection("users").document(userId);
                            Map<String, Object> user = new HashMap<>();
                            user.put("userID" , userId);
                            user.put("FullName" , fullName);
                            user.put("Email" , email);
                            user.put("Total" , 0);
                            user.put("Basket" , 0);
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                    signupPresenterListener.hideProgress();

                                    Log.d(TAG,"UserFirestore" + userId);
                                    startActivity(context,new Intent(activity, MainActivity.class),null);
                                    activity.finishAffinity();

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                    signupPresenterListener.hideProgress();
                                    Toast.makeText(activity, "Failed" + e.getMessage(), Toast.LENGTH_SHORT).show();

                                }
                            });
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(activity, "" + task.getException(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}
