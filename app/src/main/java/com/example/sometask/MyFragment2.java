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

public class MyFragment2 extends Fragment implements View.OnClickListener{

    private static final String STATE1 = "text";
    private TextView textAction;
    private EditText editText;
    private static final String STATE2 = "some";

    private Data2PassListener mCallback;


    public interface Data2PassListener{
        void pass2Data(String data);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment2, container, false);
        editText = view.findViewById(R.id.editTextFragment2EnterText);
        textAction = view.findViewById(R.id.textViewFragment2ShowAction);
        Button button2 = view.findViewById(R.id.buttonFragment2SendText);
        button2.setOnClickListener(this);
        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle state2) {
        String save1 = editText.getText().toString();
        String save2 = textAction.getText().toString();
        state2.putString(STATE1, save1);
        state2.putString(STATE2, save2);
        super.onCreate(state2);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState2) {
        super.onCreate(savedInstanceState2);
        if (savedInstanceState2 != null) {
            String edit = savedInstanceState2.getString(STATE1);
            String text = savedInstanceState2.getString(STATE2);
            editText.setText(edit);
            textAction.setText(text);
        }
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Data2PassListener) {
            mCallback = (Data2PassListener) context;
        } else {
            throw new RuntimeException(context.toString() + "must impliment Data2PassListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

    public void updateTextView(String textInputFrag) {
        textAction.setText(textInputFrag);
    }


    // не знаю верно ли добавить в онклик замену фрагмента и каким методом replace или add? или кинуть в onAttach?
    @Override
        public void onClick(View v) {
        String input = editText.getText().toString();
        mCallback.pass2Data(input);
    }
}
