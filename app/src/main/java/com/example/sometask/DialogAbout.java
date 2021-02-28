package com.example.sometask;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class DialogAbout extends DialogFragment {

    private EditText editTextDialog;
    private DialogAboutListen listener;

    //create Dialog by Builder
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_about, null);
        builder.setView(view)
                .setTitle(R.string.made_by_sds)
                .setNegativeButton(R.string.cancel, (dialog, which) -> {

                })
                .setPositiveButton(R.string.yes, (dialog, which) -> {
                    String text = editTextDialog.getText().toString();
                    listener.applyText(text);
                });
        editTextDialog = view.findViewById(R.id.editTextDialogAbout);
        return builder.create();


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (DialogAboutListen) context;
        } catch (Exception e){
            throw new RuntimeException(context.toString() + "must implement Listener Dialog");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    // sending text to MainActivity by CallBack
    public interface DialogAboutListen {
        void applyText(String text);
    }
}