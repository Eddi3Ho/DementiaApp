package com.example.dementiaapp;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
        final EditText emailtxt = (EditText) findViewById(R.id.email_edit);
        final EditText passwordtxt = (EditText) findViewById(R.id.password_edit);
        Button btntest = (Button) findViewById(R.id.button);
        Button btnread = (Button) findViewById(R.id.button2);
        Button btnRegister = (Button) findViewById(R.id.btnregister);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        btntest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Map<String, Object> user = new HashMap<>();
                user.put("first", "Ada");
                user.put("last", "Lovelace");
                user.put("born", 1816);

                db.collection("users")
                        .add(user)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(getApplicationContext(),"DocumentSnapshot added with ID: " + documentReference.getId(),Toast.LENGTH_SHORT).show();
//                                Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(), "Fail",Toast.LENGTH_SHORT).show();
//                                Log.w(TAG, "Error adding document", e);
                            }
                        });
            }
        });

        btnread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = emailtxt.getText().toString();
                String password = passwordtxt.getText().toString();

                //check if EditText fields are empty
                if (email.matches("")) {
                    Toast.makeText(getApplicationContext(), "Please enter an email", Toast.LENGTH_SHORT).show();
                } else if (password.matches("")) {
                    Toast.makeText(getApplicationContext(), "Please enter a password", Toast.LENGTH_SHORT).show();
                }
                else {

                    db.collection("users")
                        .whereEqualTo("email", email).whereEqualTo("password", password)
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
                                            Toast.makeText(getApplicationContext(), user.getUser_id() + " , " + user.getUsername() + " , " + user.getEmail(), Toast.LENGTH_LONG).show();
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
