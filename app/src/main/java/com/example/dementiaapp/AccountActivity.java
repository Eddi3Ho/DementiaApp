package com.example.dementiaapp;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class AccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.account);
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}

        EditText fullnametxt = (EditText) findViewById(R.id.fullname_editText);
        EditText emailtxt = (EditText) findViewById(R.id.email_editText);
        EditText usernametxt = (EditText) findViewById(R.id.username_editText);
        Button editProfilebtn = (Button) findViewById(R.id.editProfileButton);
        Button changePassbtn = (Button) findViewById(R.id.changePasswordButton);
        Button inviteFriendbtn = (Button) findViewById(R.id.inviteFriendButton);
        Button logOutdbtn = (Button) findViewById(R.id.logOutButton);

        FirebaseFirestore db = FirebaseFirestore.getInstance();


        //Bottom Navigation Code
        // Initialize and assign variable
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);
        // Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.account);

        // Perform item selected listener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId())
                {
                    case R.id.account:
                        startActivity(new Intent(getApplicationContext(),AccountActivity.class));
                        overridePendingTransition(0,0);
                        return true;
//                    case R.id.home:
//                        return true;
//                    case R.id.about:
//                        startActivity(new Intent(getApplicationContext(),About.class));
//                        overridePendingTransition(0,0);
//                        return true;
                }
                return false;
            }
        });

        User user = User.getInstance();
        String user_id = user.getUser_id();
        String fullname = user.getFull_name();
        String email = user.getEmail();
        String username = user.getUsername();

        fullnametxt.setText(fullname);
        emailtxt.setText(email);
        usernametxt.setText(username);

        editProfilebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                editProfileDialog(AccountActivity.this, user_id, fullname, email, username);
            }
        });

        changePassbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changePasswordDialog(AccountActivity.this);
            }
        });

        inviteFriendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emailDialog(AccountActivity.this);
            }
        });

        logOutdbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logOutDialog(AccountActivity.this, "Are you sure you want to log out?");
            }
        });

    }

    // Dialog for edit Profile
    public void editProfileDialog(Activity activity, String user_id, String fullname, String email, String username){

        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.edit_profile_dialog);
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        EditText d_fullnametxt = (EditText) dialog.findViewById(R.id.d_fullname_editText);
        EditText d_emailtxt = (EditText) dialog.findViewById(R.id.d_email_editText);
        EditText d_usernametxt = (EditText) dialog.findViewById(R.id.d_username_editText);

        d_fullnametxt.setText(fullname);
        d_emailtxt.setText(email);
        d_usernametxt.setText(username);

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
                String new_fullname = d_fullnametxt.getText().toString();
                String new_email = d_emailtxt.getText().toString();
                String new_username = d_usernametxt.getText().toString();

                //check if EditText fields are empty
                if (new_fullname.matches("")) {
                    Toast.makeText(getApplicationContext(), "Please enter your username", Toast.LENGTH_SHORT).show();
                } else if (new_email.matches("")) {
                    Toast.makeText(getApplicationContext(), "Please enter your email", Toast.LENGTH_SHORT).show();
                } else if (new_username.matches("")) {
                    Toast.makeText(getApplicationContext(), "Please enter your full name", Toast.LENGTH_SHORT).show();
                } else {

                    db.collection("users")
                            .document(user_id)
                            .update(
                                    "fullname", new_fullname,
                                    "email", new_email,
                                    "username", new_username
                            );

                    //edit current user session data
                    User user = User.getInstance();
                    user.setUsername(new_username);
                    user.setEmail(new_email);
                    user.setFull_name(new_fullname);
                    dialog.dismiss();
                    showDialog(AccountActivity.this, "Your account information has been edited");


                }
            }
        });

        dialog.show();
    }

    public void changePasswordDialog(Activity activity){

        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.edit_password_dialog);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        User user = User.getInstance();

        EditText old_passwordtxt = (EditText) dialog.findViewById(R.id.oldpassword_editText);
        EditText new_passwordtxt = (EditText) dialog.findViewById(R.id.new_password_editText);
        EditText confirm_passwordtxt = (EditText) dialog.findViewById(R.id.confirm_password_editText);

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
                String old_password = old_passwordtxt.getText().toString();
                String new_password = new_passwordtxt.getText().toString();
                String confirm_password = confirm_passwordtxt.getText().toString();

                //check if EditText fields are empty
                if (old_password.matches("")) {
                    Toast.makeText(getApplicationContext(), "Please enter your old password", Toast.LENGTH_SHORT).show();
                } else if (new_password.matches("")) {
                    Toast.makeText(getApplicationContext(), "Please enter your new password", Toast.LENGTH_SHORT).show();
                } else if (confirm_password.matches("")) {
                    Toast.makeText(getApplicationContext(), "Please enter your confirm password", Toast.LENGTH_SHORT).show();
                } else {

                    //check if user exist with session user id
                    db.collection("users")
                            .document(user.getUser_id())
                            .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                //if user exist
                                if (document.exists()) {
                                    //Check all the conditions
                                    if(!old_password.matches(document.getString("password"))){
                                        Toast.makeText(getApplicationContext(), "Your old password does not match", Toast.LENGTH_SHORT).show();
                                    } else if(new_password.matches(old_password)){
                                        Toast.makeText(getApplicationContext(), "Your new password cannot be your old password", Toast.LENGTH_SHORT).show();
                                    } else if (!new_password.matches(confirm_password)) {
                                        Toast.makeText(getApplicationContext(), "Your new password does not match your confirm password", Toast.LENGTH_SHORT).show();
                                    } else {
                                        //Update password if all conditions are met
                                        db.collection("users")
                                                .document(user.getUser_id())
                                                .update(
                                                        "password", new_password
                                                );
                                        dialog.dismiss();
                                        showDialog(AccountActivity.this, "Your password is changed");
                                    }

                                } else {
                                    Toast.makeText(getApplicationContext(), "Account do not exist, please try again", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "Error occurred, please try again", Toast.LENGTH_LONG).show();
                            }
                        }
                    });


                }
            }
        });

        dialog.show();
    }

    //Logout dialog
    public void logOutDialog(Activity activity, String msg){

        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_question);
        User user = User.getInstance();

        TextView text = (TextView) dialog.findViewById(R.id.text_dialog);
        text.setText(msg);

        Button yesButton = (Button) dialog.findViewById(R.id.yesbutton);
        Button cancelButton = (Button) dialog.findViewById(R.id.cancelbutton);

        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user.unset_user_session();
                Toast.makeText(getApplicationContext(), "test:" + user.getUsername(), Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                Intent intent = new Intent(AccountActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    //Email dialog
    public void emailDialog(Activity activity){

        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.email_friend_dialog);
        User user = User.getInstance();

        Button confirmButton = (Button) dialog.findViewById(R.id.confirmEmailButton);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent myIntent = new Intent(Intent.ACTION_SEND);
                myIntent.setType("text/plain");
                String body = "Your friend " + user.getFull_name() + " is inviting you to learn more about dementia. Download the app now (link)";
                String sub = "Invitation";
                myIntent.putExtra(Intent.EXTRA_SUBJECT,sub);
                myIntent.putExtra(Intent.EXTRA_TEXT,body);
                startActivity(Intent.createChooser(myIntent, "Share Using"));
//                Intent email = new Intent(Intent.ACTION_SEND);
//                email.putExtra(Intent.EXTRA_EMAIL, new String[]{"0134396@kdu-online.com"});
//                email.putExtra(Intent.EXTRA_SUBJECT, "Invitation");
//                email.putExtra(Intent.EXTRA_TEXT, "John is inviting you to learn more about dementia");
//
//                //need this to prompts email client only
//                email.setType("message/rfc822");
//
//                startActivity(Intent.createChooser(email, "Choose an Email client :"));
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
