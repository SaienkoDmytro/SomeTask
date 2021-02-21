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

public class MyFragment1 extends Fragment implements View.OnClickListener {

    private static final String INCOMING_TEXT_KEY = "BUNDLE_TEXT_KEY";
    private TextView textAction;
    private EditText editText;
    private Data1PassListener mCallback;

    static Bundle createArgs(String incomingText) {
        Bundle bundle = new Bundle();
        bundle.putString(INCOMING_TEXT_KEY, incomingText);
        return bundle;
    }

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

    @Override
    public void onClick(View v) {
        String input = editText.getText().toString();
        mCallback.pass1Data(input);
    }
}