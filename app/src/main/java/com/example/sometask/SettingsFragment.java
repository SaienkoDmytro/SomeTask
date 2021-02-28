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

        // changing Pref and showing Snack
        final CheckBoxPreference checkboxPref = getPreferenceManager().findPreference(getString(R.string.enable));
        assert checkboxPref != null;
        checkboxPref.
                setOnPreferenceChangeListener((preference, newValue) -> {
                    if (newValue.toString().equals(getString(R.string.positive))) {
                        showSandbar();
                    }
                    return false;
                });
    }
    // create Snackbar for changes
    private void showSandbar() {
        Snackbar snackbar = Snackbar
                .make(requireView(), R.string.return_changes, Snackbar.LENGTH_LONG)
                .setAction(R.string.yes, view1 -> {
                    Snackbar snackbar1 = Snackbar
                            .make(view1, R.string.cancel, Snackbar.LENGTH_LONG);
                    snackbar1.show();
                });

        // color of text
        snackbar.setActionTextColor(Color.BLUE);

        // color of button
        View sbView = snackbar.getView();
        TextView textView = sbView.findViewById(R.id.snackbar_text);
        textView.setTextColor(Color.YELLOW);

        snackbar.show();
    }
}
