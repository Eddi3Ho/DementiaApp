package com.example.dementiaapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class FragmentDealingPage2 extends Fragment {

    View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_dealing_page2, container, false);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        User user = User.getInstance();

        //make sure that progress page for topic can only be increased
        //Exp if user has already read page 2 then it won't saved page 1 as the progress
        db.collection("users")
                .document(user.getUser_id())
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (Integer.parseInt(document.getString("progress_dealing")) < 2){
                                db.collection("users")
                                        .document(user.getUser_id())
                                        .update(
                                                "progress_dealing", "2",
                                                "bookmark_page", "8"
                                        );
                            }
                            else {
                                db.collection("users")
                                        .document(user.getUser_id())
                                        .update(
                                                "bookmark_page", "8"
                                        );
                            }
                        }
                    }
                });

        return view;
    }
}
