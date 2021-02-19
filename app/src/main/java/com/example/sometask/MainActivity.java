package com.example.sometask;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.preference.PreferenceManager;


public class MainActivity extends AppCompatActivity implements MyFragment1.Data1PassListener, MyFragment2.Data2PassListener, DialogAbout.DialogAboutListen {

    FrameLayout container;
    private FragmentManager myFragmentManager;
    private MyFragment1 myFragment1;
    private MyFragment2 myFragment2;
    DialogAbout dlg1;
    boolean enabled;
    String login;
    final static String TAG_1 = "FRAGMENT_1";
    final static String TAG_2 = "FRAGMENT_2";
    final static String KEY_MSG_1 = "FRAGMENT1_MSG";
    final static String KEY_MSG_2 = "FRAGMENT2_MSG";
    TextView textView;

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
        textView = findViewById(R.id.a123);

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        myFragmentManager = getSupportFragmentManager();
        myFragment1 = new MyFragment1();
        myFragment2 = new MyFragment2();

 // запускаем фрагмент если активити пустое
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragment_container_view, myFragment1, null)
                    .addToBackStack(null)
                    .commit();
        }
// запускаем первый фрагмент
        button1.setOnClickListener(arg0 -> {

            MyFragment1 fragment1 = (MyFragment1) myFragmentManager.findFragmentByTag(TAG_1);

            if (fragment1 == null) {

                Bundle bundle = new Bundle();
                bundle.putString(KEY_MSG_1, "Первый фрагмент");
                myFragment1.setArguments(bundle);
// тут проблема пересоздания фрагмента повторно
                getSupportFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.fragment_container_view, myFragment1, String.valueOf(bundle))
                        .addToBackStack(null)
                        .commit();
// не работает проверка на наличие, не могу понять почему
            } else {
                fragment1.setMsg("Первый фрагмент уже загружен");
            }
        });

        button2.setOnClickListener(arg0 -> {

            MyFragment2 fragment2 = (MyFragment2) myFragmentManager.findFragmentByTag(TAG_2);

            if (fragment2 == null) {

                Bundle bundle = new Bundle();
                bundle.putString(KEY_MSG_2, "Второй фрагмент");
                myFragment2.setArguments(bundle);
// тут проблема пересоздания фрагмента повторно
                getSupportFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.fragment_container_view, myFragment2, String.valueOf(bundle))
                        .addToBackStack(null)
                        .commit();
// не работает проверка на наличие, не могу понять почему
            } else {
                fragment2.setMsg("Второй фрагмент уже загружен");
            }
        });

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
        switch (item.getItemId()) {
            case R.id.action_about:
                Toast.makeText(this, "About",
                        Toast.LENGTH_SHORT).show();
                dlg1.show(getFragmentManager(), "dlg1");
                return true;

            case R.id.action_settings:
                Toast.makeText(this, "Settings",
                        Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, SettingsActivity.class);

                // метод устарел, есть ли актуальная замена?
                startActivityForResult(intent, 1);
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }
// возвращаем активити по результату с передачей изменений в строках
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //не знаю способ передачи результата с фрагмента настроек кроме SharedPreferences есть варианты?

        SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(this);
        if (requestCode == 1) {
            enabled = prefs.getBoolean("enabled", false);
            String dark = " Light Theme";
            if (enabled) {
                dark = " Dark Theme";
            }
            login = prefs.getString("login", "не установлено");
            // закидываем изменения в алерт и выдаем на активити
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Text and Theme");
            alert.setMessage(login + dark);
            alert.setPositiveButton("OK", (dialog, which) -> Toast.makeText(MainActivity.this, "Alert gone",
                    Toast.LENGTH_SHORT).show());
            alert.create().show();
        }
    }
// интерфейс для передачи данных с 1 фрагмента во 2 - не передает, проблема в бандл?
    @Override
    public void pass1Data(String data) {
        myFragment2.updateTextView(data);

    }
    // интерфейс для передачи данных со 2 фрагмента в 1 - не передает, проблема в бандл?
    @Override
    public void pass2Data(String data) {
        myFragment1.updateTextView(data);
    }

    // интерфейс для передачи данных их диалога About передает, но не знаю как запустить в нем тост или закинуть в onActivityResult верно?
    @Override
    public void applyText(String text) {
        textView.setText(text);
        if (text != null) {
            Toast.makeText(this, text,
                    Toast.LENGTH_SHORT).show();
        }
    }
}