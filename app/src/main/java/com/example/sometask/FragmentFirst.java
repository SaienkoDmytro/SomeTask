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

public class FragmentFirst extends Fragment {

    private static final String INCOMING_TEXT_KEY = "BUNDLE_TEXT_KEY";
    private TextView textAction;
    private EditText editText;
    private DataPassListener mCallback;

    static Bundle createArgs(String incomingText) {
        Bundle bundle = new Bundle();
        bundle.putString(INCOMING_TEXT_KEY, incomingText);
        return bundle;
    }

    public interface DataPassListener{
        void passFirstData(String data);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment1, container, false);
        textAction = view.findViewById(R.id.textViewFragment1ShowAction);
        editText = view.findViewById(R.id.editTextFragment1EnterText);
        Button button1 = view.findViewById(R.id.buttonFragment1SendText);
        button1.setOnClickListener(v -> {
            String input = editText.getText().toString();
            mCallback.passFirstData(input);
        });
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
        if (context instanceof DataPassListener) {
            mCallback = (DataPassListener) context;
        } else {
            throw new RuntimeException(context.toString() + "must implement Data1PassListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

}