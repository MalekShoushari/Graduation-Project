package com.example.tourisminjordan;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Locale;
import java.util.Objects;

public class setting_page extends AppCompatActivity {

Button save;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_page);
        save = findViewById(R.id.save_setting);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String id = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        myRef = database.getReference(id).child("userLang");

        String[] items = new String[]{
                getString(R.string.choose_the_language),
                getString(R.string.arabic),
                getString(R.string.English)
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinner = findViewById(R.id.spinner_language);
        spinner.setAdapter(adapter);
        save.setOnClickListener(v -> {
            int selected_lang_index = spinner.getSelectedItemPosition();
            switch (selected_lang_index) {
                case 1:
                    changeLanguage("ar");
                    String langApp = "1";
                    myRef.child("langApp").setValue(langApp);
                    break;
                case 2:
                    changeLanguage("en");
                    String langAppEn = "2";
                    myRef.child("langApp").setValue(langAppEn);
                    break;
            }
        });
    }
    private void changeLanguage(String lang) {
        Locale newLocale = new Locale(lang);
        Locale.setDefault(newLocale);
        Resources resources = getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(newLocale);
        configuration.setLayoutDirection(newLocale);
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        recreate();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
