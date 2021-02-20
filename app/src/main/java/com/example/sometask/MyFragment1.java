package com.example.sometask;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MyFragment1 extends Fragment implements View.OnClickListener {

    private static final String STATE1 = "some";
    private static final String STATE2 = "text";
    private TextView textAction;
    private EditText editText;

    private Data1PassListener mCallback;

    public interface Data1PassListener{
        void pass1Data(String data);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment1, container, false);
        textAction = view.findViewById(R.id.textViewFragment1ShowAction);
        editText = view.findViewById(R.id.editTextFragment1EnterText);
        Button button1 = view.findViewById(R.id.buttonFragment1SendText);
        button1.setOnClickListener(this);
        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle state1) {
        String save1 = editText.getText().toString();
        String save2 = textAction.getText().toString();
        state1.putString(STATE1, save1);
        state1.putString(STATE2, save2);
        super.onCreate(state1);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState1) {
        super.onCreate(savedInstanceState1);
        if (savedInstanceState1 != null) {
            String edit = savedInstanceState1.getString(STATE1);
            String text = savedInstanceState1.getString(STATE2);
            editText.setText(edit);
            textAction.setText(text);
        }
    }

    public void updateTextView (String textInputFrag) {
        textAction.setText(textInputFrag);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Data1PassListener) {
            mCallback = (Data1PassListener) context;
        } else {
            throw new RuntimeException(context.toString() + "must impliment Data1PassListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }


// не знаю верно ли добавить в онклик замену фрагмента и каким методом replace или add? или кинуть в onAttach?
    @Override
    public void onClick(View v) {
        String input = editText.getText().toString();
        mCallback.pass1Data(input);
    }
}