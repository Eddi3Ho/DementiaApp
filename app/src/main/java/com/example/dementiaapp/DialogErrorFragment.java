package com.example.dementiaapp;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class DialogErrorFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // Create a new AlertDialog Builder
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        // Inflate the layout for the custom dialog
        View customDialogView = inflater.inflate(R.layout.dialog_error, null);

        // Set the custom view for the dialog
        builder.setView(customDialogView);

        TextView text = (TextView) customDialogView.findViewById(R.id.text_dialog);
        text.setText("You must answer all question to proceed");

        Button dialogButton = (Button) customDialogView.findViewById(R.id.btn_dialog);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        // Create the custom dialog
        AlertDialog customDialog = builder.create();

        // Return the custom dialog
        return customDialog;
    }
}