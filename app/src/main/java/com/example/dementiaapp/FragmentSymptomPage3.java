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
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class FragmentSymptomPage3 extends Fragment {

    View view;
    TextView descriptionTextView, descriptionTextView2, descriptionTextView3;
    LinearLayout layout, layout2, layout3;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_symptpm_page3, container, false);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        User user = User.getInstance();

        descriptionTextView = view.findViewById(R.id.description_textview);
        layout = view.findViewById(R.id.layout);
        descriptionTextView2 = view.findViewById(R.id.description_textview2);
        layout2 = view.findViewById(R.id.layout2);
        descriptionTextView3 = view.findViewById(R.id.description_textview3);
        layout3 = view.findViewById(R.id.layout3);

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

        return view;
    }
    public void expand(View view) {
        int v = (descriptionTextView.getVisibility() == View.GONE)? View.VISIBLE: View.GONE;
        TransitionManager.beginDelayedTransition(layout, new AutoTransition());
        descriptionTextView.setVisibility(v);
    }

    public void expand2(View view) {
        int v = (descriptionTextView2.getVisibility() == View.GONE)? View.VISIBLE: View.GONE;
        TransitionManager.beginDelayedTransition(layout2, new AutoTransition());
        descriptionTextView2.setVisibility(v);
    }
    public void expand3(View view) {
        int v = (descriptionTextView3.getVisibility() == View.GONE)? View.VISIBLE: View.GONE;
        TransitionManager.beginDelayedTransition(layout3, new AutoTransition());
        descriptionTextView3.setVisibility(v);
    }
}
