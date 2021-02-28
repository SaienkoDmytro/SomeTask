package com.example.sometask;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;
import androidx.preference.PreferenceManager;


public class MainActivity extends AppCompatActivity implements FragmentFirst.DataPassListener, FragmentSecond.DataPassListener, DialogAbout.DialogAboutListen {

    private FragmentFirst fragmentFirst;
    private FragmentSecond fragmentSecond;
    private final static String TAG_1 = "FRAGMENT_1";
    private final static String TAG_2 = "FRAGMENT_2";
    public final static String SHARED_PREF = "SHARED_PREF";
    public final static String SAVE = "SAVE";
    private final static String ENABLE = "enabled";
    private final static String LOGIN = "login";

    // activity Main method
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentFirst = new FragmentFirst();
        fragmentSecond = new FragmentSecond();

        Button button1 = findViewById(R.id.buttonActivityMainFrag1);
        Button button2 = findViewById(R.id.buttonActivityMainFrag2);

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        // check states and saves
        if (savedInstanceState == null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(R.id.fragment_container_view, new MainFragment());
            ft.commit();
        }

        if (loadState().equals("FRAGMENT_1")) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.child_fragment_container, fragmentFirst, TAG_1)
                    .commit();
        }

        if (loadState().equals("FRAGMENT_2")) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.child_fragment_container, fragmentSecond, TAG_2)
                    .commit();
        }


// create First Fragment
        button1.setOnClickListener(arg0 -> getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.child_fragment_container, fragmentFirst, TAG_1)
                .commit());
        saveState(TAG_1);
// create Second Fragment
        button2.setOnClickListener(arg0 -> getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.child_fragment_container, fragmentSecond, TAG_2)
                .commit());
        saveState(TAG_2);

    }

    // create Settings fragment
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings_menu, menu);
        return true;
    }
// Settings choser with listener on click
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
         if (item.getItemId() == R.id.action_about) {
             DialogAbout dialogAbout = new DialogAbout();
             dialogAbout.show(getFragmentManager(), "dialogAbout");
             return true;
         } else if (item.getItemId() == R.id.action_settings) {
             Intent intent = new Intent(this, SettingsActivity.class);
             // метод устарел, есть ли актуальная замена?
             startActivityForResult(intent, 1);
             return true;
         } else {
             return super.onOptionsItemSelected(item);
         }
    }
// return on MainActivity for Result with showing changes in Alert
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

// get Settings changes
        SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(this);
        if (requestCode == 1) {
            boolean enabled = prefs.getBoolean(ENABLE, false);
            String dark = getString(R.string.light_theme);
            if (enabled) {
                dark = getString(R.string.dark_theme);
            }
            String login = prefs.getString(LOGIN, getString(R.string.not_set));

            //send them to Alert
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle(R.string.setting_alert);
            alert.setMessage(login + dark);
            alert.setPositiveButton(R.string.yes, (dialog, which) -> Toast.makeText(MainActivity.this, getString(R.string.alert_gone),
                    Toast.LENGTH_SHORT).show());
            alert.create().show();
        }
    }

    @Override
    public void passFirstData(String data) {
            Bundle arguments = FragmentSecond.createArgs(data);
            fragmentSecond.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.child_fragment_container, fragmentSecond, TAG_2)
                    .commit();
            saveState(TAG_2);
    }

    @Override
    public void passSecondData(String data) {
        Bundle arguments = FragmentFirst.createArgs(data);
        fragmentFirst.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.child_fragment_container, fragmentFirst, TAG_1)
                    .commit();
            saveState(TAG_1);
    }

    //get text from AlertDialog and show in Toast
    @Override
    public void applyText(String text) {
        if (text != null) {
            Toast.makeText(this, text,
                    Toast.LENGTH_SHORT).show();
        }
    }
// save Fragment on screen
    public void saveState(String tag) {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SAVE, tag);
        editor.apply();
    }

    // load Fragment TAG
    public String loadState() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF, MODE_PRIVATE);
        return sharedPreferences.getString(SAVE, "");
    }
}