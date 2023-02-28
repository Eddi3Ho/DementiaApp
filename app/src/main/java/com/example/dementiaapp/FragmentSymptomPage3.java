package com.example.dementiaapp;

import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class FragmentSymptomPage3 extends Fragment {

    View view;
    TextView descriptionTextView;
    LinearLayout layout;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_symptpm_page3, container, false);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        User user = User.getInstance();

        descriptionTextView = view.findViewById(R.id.description_textview);
        layout = view.findViewById(R.id.layout);
        CardView cardview1 = view.findViewById(R.id.card_view);

        //make sure that progress page for topic can only be increased
        //Exp if user has already read page 2 then it won't saved page 1 as the progress
        db.collection("users")
                .document(user.getUser_id())
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (Integer.parseInt(document.getString("progress_symptom")) < 3){
                                db.collection("users")
                                        .document(user.getUser_id())
                                        .update(
                                                "progress_symptom", "3",
                                                "bookmark_page", "3"
                                        );
                            }
                            else {
                                db.collection("users")
                                        .document(user.getUser_id())
                                        .update(
                                                "bookmark_page", "3"
                                        );
                            }
                        }
                    }
                });

        cardview1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int v = (descriptionTextView.getVisibility() == View.GONE)? View.VISIBLE: View.GONE;
                TransitionManager.beginDelayedTransition(layout, new AutoTransition());
                descriptionTextView.setVisibility(v);
            }
        });

        return view;
    }
}
