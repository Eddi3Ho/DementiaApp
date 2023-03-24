package com.example.dementiaapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;

public class HostQuiz extends AppCompatActivity {

    boolean isOpen = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.host_quiz);
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


        Intent intent = getIntent();
        if(intent.hasExtra("whereto")){
            int whereto = (int) intent.getSerializableExtra("whereto");
            if(whereto == 1){
                replaceFragment(new FragmentQuizSymptom());
            } else if (whereto == 2) {
                replaceFragment(new FragmentQuizTips());
            } else {
                replaceFragment(new FragmentQuizDealing());
            }
        }else{
//            replaceFragment(new FragmentSymptomPage1());
        }

        ExtendedFloatingActionButton fabback = findViewById(R.id.back_btn);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        User user = User.getInstance();


        fabback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HostQuiz.this, QuizActivity.class);
                startActivity(intent);
            }
        });

    }

    private void replaceFragment(Fragment fragment){

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.host_quiz_symptom, fragment);
        fragmentTransaction.commit();
    }

}
