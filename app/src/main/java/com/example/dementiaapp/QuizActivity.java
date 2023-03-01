package com.example.dementiaapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


import org.w3c.dom.Document;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class QuizActivity extends AppCompatActivity {

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.quiz);
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}

        //Bottom Navigation Code
        // Initialize and assign variable
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);
        // Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.quiz);

        // Perform item selected listener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId())
                {
                    case R.id.read:
                        startActivity(new Intent(getApplicationContext(),ReadActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.report:
                        startActivity(new Intent(getApplicationContext(),ReportActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.quiz:
                        return true;
                    case R.id.account:
                        startActivity(new Intent(getApplicationContext(),AccountActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        ProgressBar symptombtn = (ProgressBar) findViewById(R.id.progress_symptom);
        ProgressBar tipsbtn = (ProgressBar) findViewById(R.id.progress_tips);
        ProgressBar dealingbtn = (ProgressBar) findViewById(R.id.dealing_tips);
        TextView symptom_score = (TextView) findViewById(R.id.symptom_score);
        TextView tips_score = (TextView) findViewById(R.id.tips_score);
        TextView dealing_score = (TextView) findViewById(R.id.dealing_score);
        TextView symptom_complete = (TextView) findViewById(R.id.symptom_complete);
        TextView tips_complete = (TextView) findViewById(R.id.tips_complete);
        TextView dealing_complete = (TextView) findViewById(R.id.dealing_complete);
        Button certbtn = (Button) findViewById(R.id.generate_cert);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        User user = User.getInstance();

        //Update score
        db.collection("users")
                .document(user.getUser_id())
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            //Complete status
                            int red_color = ContextCompat.getColor(QuizActivity.this, R.color.red);
                            int green_color = ContextCompat.getColor(QuizActivity.this, R.color.green);
                            if(Integer.parseInt(document.getString("quiz_p_symptom")) == 10){
                                symptom_complete.setText("Complete");
                                symptom_complete.setTextColor(green_color);
                                symptom_score.setText("Score: " + document.getString("score_symptom") + "/10");
                            } else {
                                symptom_complete.setText("Incomplete");
                                symptom_complete.setTextColor(red_color);
                            }
                            if(Integer.parseInt(document.getString("quiz_p_tips")) == 10){
                                tips_complete.setText("Complete");
                                tips_complete.setTextColor(green_color);
                                tips_score.setText("Score: " + document.getString("score_tips") + "/10");
                            } else {
                                tips_complete.setText("Incomplete");
                                tips_complete.setTextColor(red_color);
                            }
                            if(Integer.parseInt(document.getString("quiz_p_dealing")) == 10){
                                dealing_complete.setText("Complete");
                                dealing_complete.setTextColor(green_color);
                                dealing_score.setText("Score: " + document.getString("score_dealing") + "/10");
                            } else {
                                dealing_complete.setText("Incomplete");
                                dealing_complete.setTextColor(red_color);
                            }
                            //Progress
                            symptombtn.setProgress(Integer.parseInt(document.getString("quiz_p_symptom")));
                            tipsbtn.setProgress(Integer.parseInt(document.getString("quiz_p_tips")));
                            dealingbtn.setProgress(Integer.parseInt(document.getString("quiz_p_dealing")));

                        } else {
                            Toast.makeText(getApplicationContext(), "Error occurred, please try again", Toast.LENGTH_LONG).show();
                        }
                    }
                });

        symptombtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QuizActivity.this, HostQuiz.class);
                intent.putExtra("whereto", 1);
                startActivity(intent);
            }
        });
        tipsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QuizActivity.this, HostQuiz.class);
                intent.putExtra("whereto", 2);
                startActivity(intent);
            }
        });
        dealingbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QuizActivity.this, HostQuiz.class);
                intent.putExtra("whereto", 3);
                startActivity(intent);
            }
        });

        certbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

    }



}
