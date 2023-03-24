package com.example.dementiaapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class FragmentQuizSymptom extends Fragment {

    RadioGroup[] radioGroups = new RadioGroup[10];
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    User user = User.getInstance();
    View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_quiz_symptom, container, false);


        radioGroups[0] = (RadioGroup) view.findViewById(R.id.q1);
        radioGroups[1] = (RadioGroup) view.findViewById(R.id.q2);
        radioGroups[2] = (RadioGroup) view.findViewById(R.id.q3);
        radioGroups[3] = (RadioGroup) view.findViewById(R.id.q4);
        radioGroups[4] = (RadioGroup) view.findViewById(R.id.q5);
        radioGroups[5] = (RadioGroup) view.findViewById(R.id.q6);
        radioGroups[6] = (RadioGroup) view.findViewById(R.id.q7);
        radioGroups[7] = (RadioGroup) view.findViewById(R.id.q8);
        radioGroups[8] = (RadioGroup) view.findViewById(R.id.q9);
        radioGroups[9] = (RadioGroup) view.findViewById(R.id.q10);
        Button comletebtn = (Button) view.findViewById(R.id.completeButtom);


        //setting draft answer to questions
        db.collection("symptom_draft")
                .whereEqualTo("user_id", user.getUser_id())
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {

                            switch (documentSnapshot.getString("q1")) {
                                case "1": radioGroups[0].check(R.id.a1); break;
                                case "2": radioGroups[0].check(R.id.b1); break;
                                case "3": radioGroups[0].check(R.id.c1); break;
                                case "4": radioGroups[0].check(R.id.d1); break;
                            }
                            switch (documentSnapshot.getString("q2")) {
                                case "1": radioGroups[1].check(R.id.a2); break;
                                case "2": radioGroups[1].check(R.id.b2); break;
                                case "3": radioGroups[1].check(R.id.c2); break;
                                case "4": radioGroups[1].check(R.id.d2); break;
                            }
                            switch (documentSnapshot.getString("q3")) {
                                case "1": radioGroups[2].check(R.id.a3); break;
                                case "2": radioGroups[2].check(R.id.b3); break;
                            }
                            switch (documentSnapshot.getString("q4")) {
                                case "1": radioGroups[3].check(R.id.a4); break;
                                case "2": radioGroups[3].check(R.id.b4); break;
                                case "3": radioGroups[3].check(R.id.c4); break;
                                case "4": radioGroups[3].check(R.id.d4); break;
                            }
                            switch (documentSnapshot.getString("q5")) {
                                case "1": radioGroups[4].check(R.id.a5); break;
                                case "2": radioGroups[4].check(R.id.b5); break;
                                case "3": radioGroups[4].check(R.id.c5); break;
                                case "4": radioGroups[4].check(R.id.d5); break;
                            }
                            switch (documentSnapshot.getString("q6")) {
                                case "1": radioGroups[5].check(R.id.a6); break;
                                case "2": radioGroups[5].check(R.id.b6); break;
                            }
                            switch (documentSnapshot.getString("q7")) {
                                case "1": radioGroups[6].check(R.id.a7); break;
                                case "2": radioGroups[6].check(R.id.b7); break;
                                case "3": radioGroups[6].check(R.id.c7); break;
                                case "4": radioGroups[6].check(R.id.d7); break;
                            }
                            switch (documentSnapshot.getString("q8")) {
                                case "1": radioGroups[7].check(R.id.a8); break;
                                case "2": radioGroups[7].check(R.id.b8); break;
                                case "3": radioGroups[7].check(R.id.c8); break;
                                case "4": radioGroups[7].check(R.id.d8); break;
                            }
                            switch (documentSnapshot.getString("q9")) {
                                case "1": radioGroups[8].check(R.id.a9); break;
                                case "2": radioGroups[8].check(R.id.b9); break;
                            }
                            switch (documentSnapshot.getString("q10")) {
                                case "1": radioGroups[9].check(R.id.a10); break;
                                case "2": radioGroups[9].check(R.id.b10); break;
                                case "3": radioGroups[9].check(R.id.c10); break;
                                case "4": radioGroups[9].check(R.id.d10); break;
                            }
                        }
                    }
                });
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
                                Boolean compelete = document.getBoolean("quiz_complete_symptom");
                                if(compelete){
                                    int color = getResources().getColor(R.color.light_green);
                                    int red_color = getResources().getColor(R.color.light_red);

                                    if(radioGroups[0].getCheckedRadioButtonId() != R.id.b1){
                                        RadioButton a1 = (RadioButton) view.findViewById(R.id.b1);
                                        a1.setBackgroundColor(color);

                                        RadioButton selectedRadioButton = view.findViewById(radioGroups[0].getCheckedRadioButtonId());
                                        selectedRadioButton.setBackgroundColor(red_color);

                                    }
                                    if(radioGroups[1].getCheckedRadioButtonId() != R.id.c2){
                                        RadioButton b1 = (RadioButton) view.findViewById(R.id.c2);
                                        b1.setBackgroundColor(color);

                                        RadioButton selectedRadioButton = view.findViewById(radioGroups[1].getCheckedRadioButtonId());
                                        selectedRadioButton.setBackgroundColor(red_color);
                                    }
                                    if(radioGroups[2].getCheckedRadioButtonId() != R.id.b3){
                                        RadioButton c1 = (RadioButton) view.findViewById(R.id.b3);
                                        c1.setBackgroundColor(color);

                                        RadioButton selectedRadioButton = view.findViewById(radioGroups[2].getCheckedRadioButtonId());
                                        selectedRadioButton.setBackgroundColor(red_color);
                                    }
                                    if(radioGroups[3].getCheckedRadioButtonId() != R.id.b4){
                                        RadioButton d1 = (RadioButton) view.findViewById(R.id.b4);
                                        d1.setBackgroundColor(color);

                                        RadioButton selectedRadioButton = view.findViewById(radioGroups[3].getCheckedRadioButtonId());
                                        selectedRadioButton.setBackgroundColor(red_color);
                                    }
                                    if(radioGroups[4].getCheckedRadioButtonId() != R.id.b5){
                                        RadioButton e1 = (RadioButton) view.findViewById(R.id.b5);
                                        e1.setBackgroundColor(color);

                                        RadioButton selectedRadioButton = view.findViewById(radioGroups[4].getCheckedRadioButtonId());
                                        selectedRadioButton.setBackgroundColor(red_color);
                                    }
                                    if(radioGroups[5].getCheckedRadioButtonId() != R.id.a6){
                                        RadioButton f1 = (RadioButton) view.findViewById(R.id.a6);
                                        f1.setBackgroundColor(color);

                                        RadioButton selectedRadioButton = view.findViewById(radioGroups[5].getCheckedRadioButtonId());
                                        selectedRadioButton.setBackgroundColor(red_color);
                                    }
                                    if(radioGroups[6].getCheckedRadioButtonId() != R.id.b7){
                                        RadioButton g1 = (RadioButton) view.findViewById(R.id.b7);
                                        g1.setBackgroundColor(color);

                                        RadioButton selectedRadioButton = view.findViewById(radioGroups[6].getCheckedRadioButtonId());
                                        selectedRadioButton.setBackgroundColor(red_color);
                                    }
                                    if(radioGroups[7].getCheckedRadioButtonId() != R.id.b8){
                                        RadioButton h1 = (RadioButton) view.findViewById(R.id.b8);
                                        h1.setBackgroundColor(color);

                                        RadioButton selectedRadioButton = view.findViewById(radioGroups[7].getCheckedRadioButtonId());
                                        selectedRadioButton.setBackgroundColor(red_color);
                                    }
                                    if(radioGroups[8].getCheckedRadioButtonId() != R.id.b9){
                                        RadioButton i1 = (RadioButton) view.findViewById(R.id.b9);
                                        i1.setBackgroundColor(color);

                                        RadioButton selectedRadioButton = view.findViewById(radioGroups[8].getCheckedRadioButtonId());
                                        selectedRadioButton.setBackgroundColor(red_color);
                                    }
                                    if(radioGroups[9].getCheckedRadioButtonId() != R.id.b10) {
                                        RadioButton j1 = (RadioButton) view.findViewById(R.id.b10);
                                        j1.setBackgroundColor(color);

                                        RadioButton selectedRadioButton = view.findViewById(radioGroups[9].getCheckedRadioButtonId());
                                        selectedRadioButton.setBackgroundColor(red_color);
                                    }

                                    comletebtn.setVisibility(View.INVISIBLE);

                                    //disable all question
                                    for (int i = 0; i < radioGroups.length; i++) {
                                        radioGroups[i].setEnabled(false);
                                        for (int j = 0; j < radioGroups[i].getChildCount(); j++) {
                                            radioGroups[i].getChildAt(j).setEnabled(false);
                                            RadioButton radioButton = (RadioButton) radioGroups[i].getChildAt(j);
                                            radioButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.black));
                                        }
                                    }
                                }
                            }
                        }
                    }
                });


            comletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(radioGroups[0].getCheckedRadioButtonId() == -1 || radioGroups[1].getCheckedRadioButtonId() == -1 || radioGroups[2].getCheckedRadioButtonId() == -1 || radioGroups[3].getCheckedRadioButtonId() == -1
                        || radioGroups[4].getCheckedRadioButtonId() == -1 || radioGroups[5].getCheckedRadioButtonId() == -1 || radioGroups[6].getCheckedRadioButtonId() == -1 || radioGroups[7].getCheckedRadioButtonId() == -1
                        || radioGroups[8].getCheckedRadioButtonId() == -1 || radioGroups[9].getCheckedRadioButtonId() == -1){
                    DialogErrorFragment dialogErrorFragment = new DialogErrorFragment();
                    dialogErrorFragment.show(getFragmentManager(), "my_custom_dialog");
                }
                else{
                    int score = 0;
                    if(radioGroups[0].getCheckedRadioButtonId() == R.id.b1){ score++; }
                    if(radioGroups[1].getCheckedRadioButtonId() == R.id.c2){ score++; }
                    if(radioGroups[2].getCheckedRadioButtonId() == R.id.b3){ score++; }
                    if(radioGroups[3].getCheckedRadioButtonId() == R.id.b4){ score++; }
                    if(radioGroups[4].getCheckedRadioButtonId() == R.id.b5){ score++; }
                    if(radioGroups[5].getCheckedRadioButtonId() == R.id.a6){ score++; }
                    if(radioGroups[6].getCheckedRadioButtonId() == R.id.b7){ score++; }
                    if(radioGroups[7].getCheckedRadioButtonId() == R.id.b8){ score++; }
                    if(radioGroups[8].getCheckedRadioButtonId() == R.id.b9){ score++; }
                    if(radioGroups[9].getCheckedRadioButtonId() == R.id.b10){ score++; }

                    save_draft();
                    db.collection("users")
                            .document(user.getUser_id())
                            .update(
                                    "score_symptom", String.valueOf(score),
                                    "quiz_complete_symptom", true,
                                    "quiz_p_symptom", "10"
                            ).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Intent intent = new Intent(getActivity(), QuizActivity.class);
                                    startActivity(intent);
                                }
                            });

                }
            }
        });

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        check_complete();
    }

    public void check_complete(){
        db.collection("users")
                .document(user.getUser_id())
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            //if user exist
                            if (document.exists()) {
                                Boolean compelete = document.getBoolean("quiz_complete_symptom");
                                if(!compelete){
                                    save_draft();
                                }
                            }
                        }
                    }
                });
    }

    public void save_draft() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        User user = User.getInstance();

        //check how many question user has done
        int complete = 10;
        for (int i = 0; i < radioGroups.length; i++) {
            if(radioGroups[i].getCheckedRadioButtonId() == -1){ complete--; }
        }

        //update progress
        db.collection("users")
                .document(user.getUser_id())
                .update(
                        "quiz_p_symptom", String.valueOf(complete)
                );

        String[] value = new String[10];
        for (int i = 0; i < radioGroups.length; i++) {
            int selectedId = radioGroups[i].getCheckedRadioButtonId();

            switch (selectedId) {
                case R.id.a1:
                case R.id.a2:
                case R.id.a3:
                case R.id.a4:
                case R.id.a5:
                case R.id.a6:
                case R.id.a7:
                case R.id.a8:
                case R.id.a9:
                case R.id.a10:
                    value[i] = "1";
                    break;
                case R.id.b1:
                case R.id.b2:
                case R.id.b3:
                case R.id.b4:
                case R.id.b5:
                case R.id.b6:
                case R.id.b7:
                case R.id.b8:
                case R.id.b9:
                case R.id.b10:
                    value[i] = "2";
                    break;
                case R.id.c1:
                case R.id.c2:
                case R.id.c4:
                case R.id.c5:
                case R.id.c7:
                case R.id.c8:
                case R.id.c10:
                    value[i] = "3";
                    break;
                case R.id.d1:
                case R.id.d2:
                case R.id.d4:
                case R.id.d5:
                case R.id.d7:
                case R.id.d8:
                case R.id.d10:
                    value[i] = "4";
                    break;
                default:
                    value[i] = "-1";
                    break;
            }
        }

        //update draft
        db.collection("symptom_draft")
                .whereEqualTo("user_id", user.getUser_id())
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            // Update the document data
                            documentSnapshot.getReference().update(
                                    "q1", value[0],
                                    "q2", value[1],
                                    "q3", value[2],
                                    "q4", value[3],
                                    "q5", value[4],
                                    "q6", value[5],
                                    "q7", value[6],
                                    "q8", value[7],
                                    "q9", value[8],
                                    "q10", value[9]

                            );

                        }
                    }
                });
    }


}
