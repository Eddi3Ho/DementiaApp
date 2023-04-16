package com.example.dementiaapp;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.os.Build.VERSION.SDK_INT;
import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.TextField;


import org.w3c.dom.Document;
import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class QuizActivity extends AppCompatActivity {
    User user = User.getInstance();
    String fullname = user.getFull_name();

    private String stringFilePath = Environment.getExternalStorageDirectory().getPath() + "/Download/Certificate.pdf";
    private File file = new File(stringFilePath);
    private static final int PERMISSION_REQUEST_CODE = 200;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz);
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}
        setStatusBarGradiant(QuizActivity.this);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);

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


        db.collection("users")
                .document(user.getUser_id())
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                boolean quizDealingComplete = document.getBoolean("quiz_complete_dealing");
                                boolean quizSymptomComplete = document.getBoolean("quiz_complete_symptom");
                                boolean quizTipsComplete = document.getBoolean("quiz_complete_tips");

                                if (quizDealingComplete && quizSymptomComplete && quizTipsComplete) {
                                    certbtn.setVisibility(View.VISIBLE);
                                }
                            }

                        } else {
                            Toast.makeText(getApplicationContext(), "Error occurred, please try again", Toast.LENGTH_LONG).show();
                        }
                    }
                });

        certbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generatePDF();
            }
        });

    }

    private void generatePDF() {
        Bitmap bmp, scaledbmp;
        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.certificate);
        scaledbmp = Bitmap.createScaledBitmap(bmp, 4280, 3508, false);

        PdfDocument pdfDocument = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(4280, 3508, 1).create();
        PdfDocument.Page page = pdfDocument.startPage(pageInfo);

        Paint paint = new Paint();
        Canvas canvas = page.getCanvas();
        canvas.drawBitmap(scaledbmp, 56, 40, paint);

        String stringPDF = fullname;
        paint.setTextSize(250);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT_BOLD, Typeface.BOLD));
        paint.setColor(Color.WHITE);
        Rect bounds = new Rect();
        paint.getTextBounds(stringPDF, 0, stringPDF.length(), bounds);
        int x = (pageInfo.getPageWidth() - bounds.width()) / 2;
        int y = 1700;
        canvas.drawText(stringPDF, x, y, paint);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
        String formattedDate = dateFormat.format(calendar.getTime());
        paint.setTextSize(100);
        paint.setColor(Color.WHITE);
        int i = 850;
        int j = 3000;
        canvas.drawText(formattedDate, i, j, paint);

        /*for (String line:stringPDF.split("\n")){
            page.getCanvas().drawText(line,x,y, paint);

            y+=paint.descent()-paint.ascent();
        }*/
        pdfDocument.finishPage(page);
        try {
            pdfDocument.writeTo(new FileOutputStream(file));
            Toast.makeText(getApplicationContext(), "Certificate is created and downloaded.", Toast.LENGTH_LONG).show();
        }
        catch (Exception e){
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Error in Creating", Toast.LENGTH_LONG).show();
        }
        pdfDocument.close();
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
