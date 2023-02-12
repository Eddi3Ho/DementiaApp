package com.example.dementiaapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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


public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.login);
        final EditText usernametxt = (EditText) findViewById(R.id.username_editText);
        final EditText passwordtxt = (EditText) findViewById(R.id.password_editText);
        final TextView btnRegister = (TextView) findViewById(R.id.Registertextview);
        Button btnLogin = (Button) findViewById(R.id.loginButton);
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username = usernametxt.getText().toString();
                String password = passwordtxt.getText().toString();

                //check if EditText fields are empty
                if (username.matches("")) {
                    Toast.makeText(getApplicationContext(), "Please enter your email", Toast.LENGTH_SHORT).show();
                } else if (password.matches("")) {
                    Toast.makeText(getApplicationContext(), "Please enter your password", Toast.LENGTH_SHORT).show();
                }
                else {

                    db.collection("users")
                        .whereEqualTo("username", username).whereEqualTo("password", password)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    //check if user account exist
                                    if(task.getResult().isEmpty()){
                                        Toast.makeText(getApplicationContext(), "Incorrect credential, please try again", Toast.LENGTH_LONG).show();
                                    }
                                    else{
                                        User user = User.getInstance();
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            user.setUser_id(document.getId());
                                            user.setUsername(document.getString("username"));
                                            user.setEmail(document.getString("email"));
                                            user.setFull_name(document.getString("fullname"));

                                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                            startActivity(intent);
                                        }
                                    }
                                }
                                else {
                                    Toast.makeText(getApplicationContext(), "Incorrect credential, please try again", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                }
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);

            }
        });

    }

}
