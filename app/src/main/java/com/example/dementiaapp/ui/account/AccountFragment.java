package com.example.dementiaapp.ui.account;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.dementiaapp.R;
import com.example.dementiaapp.User;

//import com.example.dementiaapp.databinding.FragmentNotificationsBinding;

public class AccountFragment extends Fragment {

//    private FragmentNotificationsBinding binding;
    View view;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AccountViewModel accountViewModel =
                new ViewModelProvider(this).get(AccountViewModel.class);

        view = inflater.inflate(R.layout.account, container, false);

        EditText fullnametxt = (EditText) view.findViewById(R.id.fullname_editText);
        EditText emailtxt = (EditText) view.findViewById(R.id.email_editText);
        EditText usernametxt = (EditText) view.findViewById(R.id.username_editText);
        Button editProfilebtn = (Button) view.findViewById(R.id.editProfileButton);
        Button changePassbtn = (Button) view.findViewById(R.id.changePasswordButton);
        Button inviteFriendbtn = (Button) view.findViewById(R.id.inviteFriendButton);

        User user = User.getInstance();
        String fullname = user.getFull_name();
        String email = user.getEmail();
        String username = user.getUsername();

        fullnametxt.setText(fullname);
        emailtxt.setText(email);
        usernametxt.setText(username);

        editProfilebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return view;

//        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
//        View root = binding.getRoot();
//
//        final TextView textView = binding.textNotifications;
//        accountViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
//        return root;
    }

//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        binding = null;
//    }

}

