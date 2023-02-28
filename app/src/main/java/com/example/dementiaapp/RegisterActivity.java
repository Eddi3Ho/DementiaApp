package com.example.dementiaapp;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        setContentView(R.layout.register);
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}

        final EditText fullnametxt = (EditText) findViewById(R.id.fullname_editText);
        final EditText usernametxt = (EditText) findViewById(R.id.username_editText);
        final EditText emailtxt = (EditText) findViewById(R.id.email_editText);
        final EditText passwordtxt = (EditText) findViewById(R.id.password_editText);
        final EditText confirmpasswordtxt = (EditText) findViewById(R.id.confirm_password_editText);
        final TextView backtologin = (TextView) findViewById(R.id.back_to_login);
        Button btnRegister = (Button) findViewById(R.id.registerButton);
        final String tempID;
        backtologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);

            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String fullname = fullnametxt.getText().toString();
                String email = emailtxt.getText().toString();
                String username = usernametxt.getText().toString();
                String password = passwordtxt.getText().toString();
                String confirm_password = confirmpasswordtxt.getText().toString();


                //check if EditText fields are empty
                if (username.matches("")) {
                    Toast.makeText(getApplicationContext(), "Please enter your username", Toast.LENGTH_SHORT).show();
                } else if (email.matches("")) {
                    Toast.makeText(getApplicationContext(), "Please enter your email", Toast.LENGTH_SHORT).show();
                } else if (password.matches("")) {
                    Toast.makeText(getApplicationContext(), "Please enter your password", Toast.LENGTH_SHORT).show();
                } else if (fullname.matches("")) {
                    Toast.makeText(getApplicationContext(), "Please enter your full name", Toast.LENGTH_SHORT).show();
                } else if (!confirm_password.matches(password)) {
                    Toast.makeText(getApplicationContext(), "Confirm password does not match password", Toast.LENGTH_SHORT).show();
                } else {

                    //add user
                    Map<String, Object> user = new HashMap<>();
                    user.put("fullname", fullname);
                    user.put("username", username);
                    user.put("email", email);
                    user.put("password", password);
                    user.put("progress_symptom", "0");
                    user.put("progress_tips", "0");
                    user.put("progress_dealing", "0");
                    user.put("bookmark_page", "0");
                    user.put("score_symptom", "0");
                    user.put("score_tips", "0");
                    user.put("score_dealing", "0");
                    user.put("quiz_p_symptom", "0");
                    user.put("quiz_p_tips", "0");
                    user.put("quiz_p_dealing", "0");

                    db.collection("users")
                        .add(user)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(getApplicationContext(), "Registration Complete", Toast.LENGTH_LONG).show();
                                //add empty symptom table
                                Map<String, Object> symptom = new HashMap<>();
                                symptom.put("user_id", documentReference.getId());
                                symptom.put("q1", "-1");
                                symptom.put("q2", "-1");
                                symptom.put("q3", "-1");
                                symptom.put("q4", "-1");
                                symptom.put("q5", "-1");
                                symptom.put("q6", "-1");
                                symptom.put("q7", "-1");
                                symptom.put("q8", "-1");
                                symptom.put("q9", "-1");
                                symptom.put("q10", "-1");

                                db.collection("symptom_draft")
                                        .add(symptom)
                                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                            @Override
                                            public void onSuccess(DocumentReference documentReference) {
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(getApplicationContext(), "Registration Fail", Toast.LENGTH_SHORT).show();
                                            }
                                        });

                                //add empty tips table
                                Map<String, Object> tips = new HashMap<>();
                                tips.put("user_id", documentReference.getId());
                                tips.put("q1", "-1");
                                tips.put("q2", "-1");
                                tips.put("q3", "-1");
                                tips.put("q4", "-1");
                                tips.put("q5", "-1");
                                tips.put("q6", "-1");
                                tips.put("q7", "-1");
                                tips.put("q8", "-1");
                                tips.put("q9", "-1");
                                tips.put("q10", "-1");

                                db.collection("tips_draft")
                                        .add(tips)
                                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                            @Override
                                            public void onSuccess(DocumentReference documentReference) {
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(getApplicationContext(), "Registration Fail", Toast.LENGTH_SHORT).show();
                                            }
                                        });

                                //add empty tips table
                                Map<String, Object> dealing = new HashMap<>();
                                dealing.put("user_id", documentReference.getId());
                                dealing.put("q1", "-1");
                                dealing.put("q2", "-1");
                                dealing.put("q3", "-1");
                                dealing.put("q4", "-1");
                                dealing.put("q5", "-1");
                                dealing.put("q6", "-1");
                                dealing.put("q7", "-1");
                                dealing.put("q8", "-1");
                                dealing.put("q9", "-1");
                                dealing.put("q10", "-1");

                                db.collection("dealing_draft")
                                        .add(dealing)
                                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                            @Override
                                            public void onSuccess(DocumentReference documentReference) {
                                                //Go  back to login page if register success
                                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                                startActivity(intent);
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(getApplicationContext(), "Registration Fail", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(), "Registration Fail", Toast.LENGTH_SHORT).show();
                            }
                        });

                }
            }
        });
    }
}
