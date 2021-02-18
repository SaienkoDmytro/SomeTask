package com.example.sometask;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class MyFragment1 extends Fragment implements View.OnClickListener {

    public static final String DATA_RECEIVE = "SOME DATA";
    private static final String KEY_MSG_1 = "FRAGMENT1_MSG";
    TextView textMsg;

    DataPassListener mCallback;

    public interface DataPassListener{
        void passData(String data);
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        // This makes sure that the host activity has implemented the callback interface
        // If not, it throws an exception
        try
        {
            mCallback = (DataPassListener) context;
        }
        catch (ClassCastException e)
        {
            throw new ClassCastException(context.toString()+ " must implement OnImageClickListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment1, null);
        textMsg = view.findViewById(R.id.textViewFragment1ShowText);

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



    public void setMsg(String msg) {
        textMsg.setText(msg);
    }


    @Override
    public void onClick(View v) {
        Toast.makeText(getActivity(), "Вы нажали на кнопку",
                Toast.LENGTH_SHORT).show();
        mCallback.passData("Text to pass FragmentB");

    }
}