package com.example.blaumtask.ui.SignupPresenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.blaumtask.ui.MainActivity;
import com.example.blaumtask.ui.SignupActivity;
import com.example.blaumtask.ui.models.Signup.SignupRequestModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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

    public SignupPresenter(Activity activity,Context context){
        this.activity = activity;
        this.context = context;
        firestore = FirebaseFirestore.getInstance();
    }


    public void submitLogin(String fullName, String email, String password){

        signupRequestModel = new SignupRequestModel();
        signupRequestModel.setFullname(fullName);
        signupRequestModel.setEmail(email);
        signupRequestModel.setPassword(password);

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
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                    Log.d(TAG,"UserFirestore" + userId);
                                    startActivity(context,new Intent(activity, MainActivity.class),null);

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
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
