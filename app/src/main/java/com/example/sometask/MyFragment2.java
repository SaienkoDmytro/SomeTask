package com.example.sometask;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class MyFragment2 extends Fragment implements View.OnClickListener{

    private TextView textAction;
    private EditText editText;
    private static final String INCOMING_TEXT_KEY = "BUNDLE_TEXT_KEY";
    private Data2PassListener mCallback;


    public interface Data2PassListener{
        void pass2Data(String data);
    }

    static Bundle createArgs(String incomingText) {
        Bundle bundle = new Bundle();
        bundle.putString(INCOMING_TEXT_KEY, incomingText);
        return bundle;
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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
        String incomingText = getArguments().getString(INCOMING_TEXT_KEY);
        if (incomingText != null) {
            textAction.setText(incomingText);
        }
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Data2PassListener) {
            mCallback = (Data2PassListener) context;
        } else {
            throw new RuntimeException(context.toString() + "must implement Data2PassListener");
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
        mCallback.pass2Data(input);
    }
}
