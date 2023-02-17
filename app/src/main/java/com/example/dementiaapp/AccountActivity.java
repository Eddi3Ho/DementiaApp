package com.example.dementiaapp;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class AccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EditText fullnametxt = (EditText) findViewById(R.id.fullname_editText);
        EditText emailtxt = (EditText) findViewById(R.id.email_editText);
        EditText usernametxt = (EditText) findViewById(R.id.username_editText);
        Button editProfilebtn = (Button) findViewById(R.id.editProfileButton);
        Button changePassbtn = (Button) findViewById(R.id.changePasswordButton);
        Button inviteFriendbtn = (Button) findViewById(R.id.inviteFriendButton);
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        User user = User.getInstance();
        String fullname = user.getFull_name();
        String email = user.getEmail();
        String username = user.getUsername();

        fullnametxt.setText(fullname);
        emailtxt.setText(email);
        usernametxt.setText(username);

        editProfilebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    public class ViewDialog {

        // Dialog for edit Profile
        public void editProfileDialog(Activity activity, String fullname, String email, String username){

            final Dialog dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.edit_profile_dialog);
            FirebaseFirestore db = FirebaseFirestore.getInstance();

            EditText fullnametxt = (EditText) dialog.findViewById(R.id.fullname_editText);
            EditText emailtxt = (EditText) dialog.findViewById(R.id.email_editText);
            EditText usernametxt = (EditText) dialog.findViewById(R.id.username_editText);

            Button confirmButton = (Button) dialog.findViewById(R.id.confirmEditProfileButton);
            ImageView closeButton = (ImageView) dialog.findViewById(R.id.close);

            closeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            confirmButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //check if EditText fields are empty
                    if (username.matches("")) {
                        Toast.makeText(getApplicationContext(), "Please enter your username", Toast.LENGTH_SHORT).show();
                    } else if (email.matches("")) {
                        Toast.makeText(getApplicationContext(), "Please enter your email", Toast.LENGTH_SHORT).show();
                    } else if (fullname.matches("")) {
                        Toast.makeText(getApplicationContext(), "Please enter your full name", Toast.LENGTH_SHORT).show();
                    } else {
//                        db.collection("users")
//                                .update("user")
//                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                                    @Override
//                                    public void onSuccess(DocumentReference documentReference) {
//                                        Toast.makeText(getApplicationContext(), "Registration Complete", Toast.LENGTH_LONG).show();
//                                        //Go  back to login page if register success
//                                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
//                                        startActivity(intent);
//                                    }
//                                })
//                                .addOnFailureListener(new OnFailureListener() {
//                                    @Override
//                                    public void onFailure(@NonNull Exception e) {
//                                        Toast.makeText(getApplicationContext(), "Registration Fail", Toast.LENGTH_SHORT).show();
//                                    }
//                                });
                    }
                }
            });

            dialog.show();
        }

        //Show success dialog
        public void showDialog(Activity activity, String msg){

            final Dialog dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.dialog_correct);

            TextView text = (TextView) dialog.findViewById(R.id.text_dialog);
            text.setText(msg);

            Button dialogButton = (Button) dialog.findViewById(R.id.btn_dialog);
            dialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            dialog.show();

        }
    }

}
