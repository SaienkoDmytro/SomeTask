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

public class FragmentSecond extends Fragment {

    private TextView textAction;
    private EditText editText;
    private static final String INCOMING_TEXT_KEY = "BUNDLE_TEXT_KEY";
    private DataPassListener mCallback;

// pass data trow MainActivity
    public interface DataPassListener{
        void passSecondData(String data);
    }
// save text in Bundle
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
        button2.setOnClickListener(v -> {
            String input = editText.getText().toString();
            mCallback.passSecondData(input);
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle args = getArguments();
        // check saved text from Bundle
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
            throw new RuntimeException(context.toString() + "must implement DataPassListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

}
