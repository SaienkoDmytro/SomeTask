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

public class MyFragment2 extends Fragment implements View.OnClickListener{

    private static final String KEY_MSG_2 = "FRAGMENT2_MSG";
    TextView textMsg;
    TextView textAction;
    EditText editText;

    Data2PassListener mCallback;



    public interface Data2PassListener{
        void pass2Data(String data);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment2, null);
        textMsg = view.findViewById(R.id.textViewFragment2ShowText);
        editText = view.findViewById(R.id.editTextFragment2EnterText);
        textAction = view.findViewById(R.id.textViewFragment2ShowAction);

        Bundle bundle = getArguments();
        if (bundle != null) {
            String msg = bundle.getString(KEY_MSG_2);
            if (msg != null) {
                textMsg.setText(msg);
            }
        }

        Button button2 = view.findViewById(R.id.buttonFragment2SendText);
        button2.setOnClickListener(this);
        return view;

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

    public void updateTextView(String textInputFrag2) {
        textAction.setText(textInputFrag2);
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
        mCallback.pass2Data(input);

    }
}
