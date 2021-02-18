package com.example.sometask;

import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.preference.PreferenceManager;


public class MainActivity extends AppCompatActivity implements MyFragment1.DataPassListener {

    FrameLayout container;
    FragmentManager myFragmentManager;
    MyFragment1 myFragment1;
    MyFragment2 myFragment2;
    DialogFragment dlg1;
    TextView settingsText;
    boolean enabled;
    String login;
    final static String TAG_1 = "FRAGMENT_1";
    final static String TAG_2 = "FRAGMENT_2";
    final static String KEY_MSG_1 = "FRAGMENT1_MSG";
    final static String KEY_MSG_2 = "FRAGMENT2_MSG";

    // activity Main method
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        container = findViewById(R.id.container);
        dlg1 = new DialogFragment();

        Button button1 = findViewById(R.id.buttonActivityMainFrag1);
        Button button2 = findViewById(R.id.buttonActivityMainFrag2);

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        settingsText = findViewById(R.id.settingsText);

        myFragmentManager = getFragmentManager();
        myFragment1 = new MyFragment1();
        myFragment2 = new MyFragment2();


        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragment_container_view, MyFragment1.class, null)
                    .addToBackStack(null)
                    .commit();
        }

        button1.setOnClickListener(arg0 -> {

            androidx.fragment.app.FragmentManager fragmentManager = getSupportFragmentManager();
            MyFragment1 fragment1 = (MyFragment1) fragmentManager.findFragmentByTag(TAG_1);

            if (fragment1 == null) {

                Bundle bundle = new Bundle();
                bundle.putString(KEY_MSG_1, "Первый фрагмент");
                myFragment1.setArguments(bundle);

                getSupportFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.fragment_container_view, MyFragment1.class, bundle)
                        .addToBackStack(null)
                        .commit();

            } else {
                fragment1.setMsg("Первый фрагмент уже загружен");
            }
        });

        button2.setOnClickListener(arg0 -> {

            androidx.fragment.app.FragmentManager fragmentManager = getSupportFragmentManager();
            MyFragment2 fragment2 = (MyFragment2) fragmentManager.findFragmentByTag(TAG_2);

            if (fragment2 == null) {

                Bundle bundle = new Bundle();
                bundle.putString(KEY_MSG_2, "Второй фрагмент");
                myFragment2.setArguments(bundle);

                getSupportFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.fragment_container_view, MyFragment2.class, bundle)
                        .addToBackStack(null)
                        .commit();

            } else {
                fragment2.setMsg("Второй фрагмент уже загружен");
            }
        });

    }

    @Override
    public void passData(String data) {
        MyFragment1 fragment1 = new MyFragment1();
        Bundle args = new Bundle();
        args.putString(MyFragment1.DATA_RECEIVE, data);
        fragment1 .setArguments(args);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container_view, fragment1)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_about:
                Toast.makeText(this, "About",
                        Toast.LENGTH_SHORT).show();
                dlg1.show(getSupportFragmentManager(), String.valueOf(dlg1));
                return true;

            case R.id.action_settings:
                Toast.makeText(this, "Settings",
                        Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(this);
        enabled = prefs.getBoolean("enabled", false);
        login = prefs.getString("login", "не установлено");
        settingsText.setText(login);
        if(enabled)
            settingsText.setVisibility(View.VISIBLE);
        else
            settingsText.setVisibility(View.INVISIBLE);
    }

}