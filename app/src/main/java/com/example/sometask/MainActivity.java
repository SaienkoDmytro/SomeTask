package com.example.sometask;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.preference.PreferenceManager;


public class MainActivity extends AppCompatActivity implements MyFragment1.Data1PassListener, MyFragment2.Data2PassListener, DialogAbout.DialogAboutListen {

    public FrameLayout container;
    private FragmentManager myFragmentManager;
    private MyFragment1 myFragment1;
    private MyFragment2 myFragment2;
    private DialogAbout dlg1;
    final static String TAG_1 = "FRAGMENT_1";
    final static String TAG_2 = "FRAGMENT_2";

    // activity Main method
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        container = findViewById(R.id.container);
        dlg1 = new DialogAbout();

        Button button1 = findViewById(R.id.buttonActivityMainFrag1);
        Button button2 = findViewById(R.id.buttonActivityMainFrag2);

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        // Begin the transaction
        if (savedInstanceState == null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_container_view, new MainFragment());
            ft.commit();
        }

        myFragmentManager = getSupportFragmentManager();
        myFragment1 = new MyFragment1();
        myFragment2 = new MyFragment2();


// запускаем первый фрагмент
        button1.setOnClickListener(arg0 -> getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.child_fragment_container, myFragment1, TAG_1)
                .addToBackStack(TAG_1)
                .commit());
// запускаем второй фрагмент
        button2.setOnClickListener(arg0 -> getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.child_fragment_container, myFragment2, TAG_2)
                .addToBackStack(TAG_2)
                .commit());

    }

    // запуск фрагмента настроек
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings_menu, menu);
        return true;
    }
// онклик для настроек
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
         if (item.getItemId() == R.id.action_about) {
             dlg1.show(getFragmentManager(), "dlg1");
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
// возвращаем активити по результату с передачей изменений в строках
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(this);
        if (requestCode == 1) {
            boolean enabled = prefs.getBoolean("enabled", false);
            String dark = " Light Theme";
            if (enabled) {
                dark = " Dark Theme";
            }
            String login = prefs.getString("login", "не установлено");
            // закидываем изменения в алерт и выдаем на активити
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Text and Theme");
            alert.setMessage(login + dark);
            alert.setPositiveButton("OK", (dialog, which) -> Toast.makeText(MainActivity.this, "Alert gone",
                    Toast.LENGTH_SHORT).show());
            alert.create().show();
        }
    }

    @Override
    public void pass1Data(String data) {
            Bundle arguments = MyFragment2.createArgs(data);
            myFragment2.setArguments(arguments);
            myFragmentManager.beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.child_fragment_container, myFragment2, TAG_2)
                    .addToBackStack(TAG_2)
                    .commit();
    }

    @Override
    public void pass2Data(String data) {
        Bundle arguments = MyFragment1.createArgs(data);
        myFragment1.setArguments(arguments);
            myFragmentManager.beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.child_fragment_container, myFragment1, TAG_1)
                    .addToBackStack(TAG_1)
                    .commit();
    }

    @Override
    public void applyText(String text) {
        if (text != null) {
            Toast.makeText(this, text,
                    Toast.LENGTH_SHORT).show();
        }
    }
}