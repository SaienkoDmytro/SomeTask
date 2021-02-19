package com.example.sometask;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.preference.CheckBoxPreference;
import androidx.preference.PreferenceFragmentCompat;

import com.google.android.material.snackbar.Snackbar;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.settings);

        final CheckBoxPreference checkboxPref = getPreferenceManager().findPreference("enabled");
  // не знаю как сделать отмену CheckBoxPreference через снекбар. enable/disable блокируют выбор и все
        assert checkboxPref != null;
        checkboxPref.
                setOnPreferenceChangeListener((preference, newValue) -> {
                    if (newValue.toString().equals("true")) {
                        showSnackbar(getView());
                    } else {
                        showSnackbar(getView());
                    }
                    return true;
                });
    }
    // делаем снекбар для оповещения
    public static void showSnackbar(View view) {
        Snackbar snackbar = Snackbar
                .make(view, "Вернуть изменения", Snackbar.LENGTH_LONG)
                .setAction("Да", view1 -> {
                    Snackbar snackbar1 = Snackbar
                            .make(view1, "Отменено", Snackbar.LENGTH_LONG);
                    snackbar1.show();
                });

        // цвет текста снека
        snackbar.setActionTextColor(Color.BLUE);

        // цвет кнопки снека
        View sbView = snackbar.getView();
        TextView textView = sbView.findViewById(R.id.snackbar_text);
        textView.setTextColor(Color.YELLOW);

        snackbar.show();
    }
}
