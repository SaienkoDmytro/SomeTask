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
import androidx.fragment.app.Fragment;

public class MyFragment1 extends Fragment implements View.OnClickListener {

    private static final String KEY_MSG_1 = "FRAGMENT1_MSG";
    TextView textMsg, textAction;
    EditText editText;

    Data1PassListener mCallback;

    public interface Data1PassListener{
        void pass1Data(String data);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment1, null);
        textMsg = view.findViewById(R.id.textViewFragment1ShowText);
        textAction = view.findViewById(R.id.textViewFragment1ShowAction);
        editText = view.findViewById(R.id.editTextFragment1EnterText);

        Bundle bundle = getArguments();
        if (bundle != null) {
            String msg = bundle.getString(KEY_MSG_1);
            if (msg != null) {
                textMsg.setText(msg);
            }
        }

        Button button1 = view.findViewById(R.id.buttonFragment1SendText);
        button1.setOnClickListener(this);

        return view;
    }

    public void updateTextView (String textInputFrag2) {
        textAction.setText(textInputFrag2);
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


    public void setMsg(String msg) {
        textMsg.setText(msg);
    }

// не знаю верно ли добавить в онклик замену фрагмента и каким методом replace или add? или кинуть в onAttach?
    @Override
    public void onClick(View v) {
        Toast.makeText(getActivity(), "Вы нажали на кнопку",
                Toast.LENGTH_SHORT).show();
        String input = editText.getText().toString();
        mCallback.pass1Data(input);
    }
}