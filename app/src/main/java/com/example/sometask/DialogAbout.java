package com.example.sometask;

import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;

//конструктор DialogFragment устарел. Замена?
public class DialogAbout extends DialogFragment implements OnClickListener {

    private EditText editTextDialog;
    private DialogAboutListen listener;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().setTitle("Title!");
        View v = inflater.inflate(R.layout.dialog_about, null);
        v.findViewById(R.id.button_about).setOnClickListener(this);
        editTextDialog = v.findViewById(R.id.editTextDialogAbout);
        return v;
    }

    public void onClick(View v) {
        String text = editTextDialog.getText().toString();
        listener.applyText(text);
        dismiss();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (DialogAboutListen) context;
    }

    // отправка текста на мейн активити через интерфейс
    public interface DialogAboutListen {
        void applyText(String text);
    }
}