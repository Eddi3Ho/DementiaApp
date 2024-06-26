package com.example.dementiaapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ReportActivity extends AppCompatActivity {

    private ProgressBar progressBar1, progressBar2, progressBar3;
    private TextView textViewProgress1, textViewProgress2, textViewProgress3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.report);
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}
        setStatusBarGradiant(ReportActivity.this);

        //Bottom Navigation Code
        // Initialize and assign variable
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);
        // Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.report);

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

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        User user = User.getInstance();
        progressBar1 = findViewById(R.id.progress_bar1);
        textViewProgress1 = findViewById(R.id.text_view_progress1);
        progressBar2 = findViewById(R.id.progress_bar2);
        textViewProgress2 = findViewById(R.id.text_view_progress2);
        progressBar3 = findViewById(R.id.progress_bar3);
        textViewProgress3 = findViewById(R.id.text_view_progress3);

        db.collection("users")
                .document(user.getUser_id())
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            DocumentSnapshot document = task.getResult();

                            String progressStr1 = document.getString("progress_symptom");
                            String progressStr2 = document.getString("progress_tips");
                            String progressStr3 = document.getString("progress_dealing");
                            if(progressStr1 != null){
                                int progress = Integer.parseInt(progressStr1);
                                int value = (progress * 100) / 3;
                                progressBar1.setProgress(value);
                                textViewProgress1.setText(String.valueOf(value + "%"));
                            }
                            if(progressStr2 != null){
                                int progress2 = Integer.parseInt(progressStr2);
                                int value2 = (progress2 * 100) / 3;
                                progressBar2.setProgress(value2);
                                textViewProgress2.setText(String.valueOf(value2 + "%"));
                            }
                            if(progressStr3 != null){
                                int progress3 = Integer.parseInt(progressStr3);
                                int value3 = (progress3 * 100) / 3;
                                progressBar3.setProgress(value3);
                                textViewProgress3.setText(String.valueOf(value3 + "%"));
                            }
                        }
                    }
                });
    }

    public static void setStatusBarGradiant(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            Drawable background = activity.getResources().getDrawable(R.drawable.blue_gradient_background);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

            window.setStatusBarColor(activity.getResources().getColor(android.R.color.transparent));
            window.setBackgroundDrawable(background);
        }
    }
}
