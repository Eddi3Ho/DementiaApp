package com.example.dementiaapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ReadActivity extends AppCompatActivity {

    boolean isOpen = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.read_activity);
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}

        //Bottom Navigation Code
        // Initialize and assign variable
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);
        // Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.read);

        // Perform item selected listener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId())
                {
                    case R.id.read:
                        return true;
                    case R.id.report:
                        startActivity(new Intent(getApplicationContext(),ReportActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.quiz:
                        startActivity(new Intent(getApplicationContext(), QuizActivity.class));
                        overridePendingTransition(0,0);
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
        TextView symptompage = (TextView) findViewById(R.id.symptom_page_progress);
        TextView tipspage = (TextView) findViewById(R.id.tips_page_progress);
        TextView dealingpage = (TextView) findViewById(R.id.dealing_page_progress);
        ExtendedFloatingActionButton bookmarkbtn = (ExtendedFloatingActionButton ) findViewById(R.id.go_to_bookmark_btn);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        User user = User.getInstance();

        //Update topic page number
        db.collection("users")
                .document(user.getUser_id())
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            //Set progress bar and page number
                            symptompage.setText("Progress: " + document.getString("progress_symptom") + "/3 Page");
                            tipspage.setText("Progress: " + document.getString("progress_tips") + "/3 Page");
                            dealingpage.setText("Progress: " + document.getString("progress_dealing") + "/3 Page");
                            symptombtn.setProgress(Integer.parseInt(document.getString("progress_symptom")));
                            tipsbtn.setProgress(Integer.parseInt(document.getString("progress_tips")));
                            dealingbtn.setProgress(Integer.parseInt(document.getString("progress_dealing")));

                        } else {
                            Toast.makeText(getApplicationContext(), "Error occurred, please try again", Toast.LENGTH_LONG).show();
                        }
                    }
                });

        symptombtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ReadActivity.this, HostSymptom.class);
                startActivity(intent);
            }
        });
        tipsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ReadActivity.this, HostTips.class);
                startActivity(intent);
            }
        });
        dealingbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ReadActivity.this, HostDealing.class);
                startActivity(intent);
            }
        });
        bookmarkbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.collection("users")
                        .document(user.getUser_id())
                        .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    int bookmark_number = Integer.parseInt(document.getString("bookmark_page"));
                                    if(bookmark_number > 6){
                                        Intent intent = new Intent(ReadActivity.this, HostDealing.class);
                                        intent.putExtra("bookmark", bookmark_number);
                                        startActivity(intent);
                                    } else if (bookmark_number > 3) {
                                        Intent intent = new Intent(ReadActivity.this, HostTips.class);
                                        intent.putExtra("bookmark", bookmark_number);
                                        startActivity(intent);
                                    } else {
                                        Intent intent = new Intent(ReadActivity.this, HostSymptom.class);
                                        intent.putExtra("bookmark", bookmark_number);
                                        startActivity(intent);
                                    }

                                }
                            }
                        });
            }
        });


    }

}
