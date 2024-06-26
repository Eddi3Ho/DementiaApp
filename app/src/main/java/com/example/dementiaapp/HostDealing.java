package com.example.dementiaapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;

public class HostDealing extends AppCompatActivity {

    boolean isOpen = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.host_dealing);
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}
        setStatusBarGradiant(HostDealing.this);

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
        if(intent.hasExtra("bookmark")){
            int bookmark = (int) intent.getSerializableExtra("bookmark");
            if(bookmark == 7){
                replaceFragment(new FragmentDealingPage1());
            } else if (bookmark == 8) {
                replaceFragment(new FragmentDealingPage2());
            } else {
                replaceFragment(new FragmentDealingPage3());
            }
        }else{
            replaceFragment(new FragmentDealingPage1());
        }

        FloatingActionButton fabAdd = findViewById(R.id.floatingActionButtonAdd);
        FloatingActionButton fab1 = findViewById(R.id.fabpage1);
        FloatingActionButton fab2 = findViewById(R.id.fabpage2);
        FloatingActionButton fab3 = findViewById(R.id.fabpage3);
        FloatingActionButton fabback = findViewById(R.id.fabback);
        Animation fabOpen = AnimationUtils.loadAnimation(this, R.anim.to_left_anim);
        Animation fabClose = AnimationUtils.loadAnimation(this, R.anim.from_left_anim);
        Animation fabRotateOpen = AnimationUtils.loadAnimation(this, R.anim.rotate_open_anim);
        Animation fabRotateClose = AnimationUtils.loadAnimation(this, R.anim.rotate_close_anim);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        User user = User.getInstance();

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isOpen) {
                    fabAdd.startAnimation(fabRotateClose);
                    fab1.startAnimation(fabClose);
                    fab2.startAnimation(fabClose);
                    fab3.startAnimation(fabClose);
                    fabback.startAnimation(fabClose);
                    fab1.setClickable(false);
                    fab2.setClickable(false);
                    fab3.setClickable(false);
                    fabback.setClickable(false);
                    isOpen = false;
                } else {
                    fabAdd.startAnimation(fabRotateOpen);
                    fab1.startAnimation(fabOpen);
                    fab2.startAnimation(fabOpen);
                    fab3.startAnimation(fabOpen);
                    fabback.startAnimation(fabOpen);
                    fab1.setClickable(true);
                    fab2.setClickable(true);
                    fab3.setClickable(true);
                    fabback.setClickable(true);
                    isOpen = true;
                }

            }
        });

        fabback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HostDealing.this, ReadActivity.class);
                startActivity(intent);
            }
        });

        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(new FragmentDealingPage1());
            }
        });

        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(new FragmentDealingPage2());
            }
        });

        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(new FragmentDealingPage3());
            }
        });

    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.host_fragment_dealing, fragment);
        fragmentTransaction.commit();
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
