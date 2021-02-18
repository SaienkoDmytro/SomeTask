package com.example.sometask;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class MyFragment2 extends Fragment implements View.OnClickListener{

    private static final String KEY_MSG_2 = "FRAGMENT2_MSG";
    final static String DATA_RECEIVE = "data_receive";
    TextView textMsg, textAction;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment2, null);
        textMsg = view.findViewById(R.id.textViewFragment2ShowText);
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

    public void setMsg(String msg) {
        textMsg.setText(msg);
    }

    @Override
    public void onStart() {
        super.onStart();
        Bundle args = getArguments();
        if (args != null) {
            textAction.setText(args.getString(DATA_RECEIVE));
        }
    }

    @Override
        public void onClick(View v) {
        Toast.makeText(getActivity(), "Вы нажали на кнопку",
                Toast.LENGTH_SHORT).show();

    }
}
