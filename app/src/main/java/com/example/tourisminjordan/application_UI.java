package com.example.tourisminjordan;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class application_UI extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ImageView nav_menu;
    ProgressBar progressBar;
    TextView userName_nav;
    TextView email_nav;
    ImageView profile_picture,image_profile;
    private EditText searchEditText;
    private List<String> resultsList;
    ListView resultsListView;
    ArrayAdapter<String> adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application_ui);
        searchEditText = findViewById(R.id.searchEditText);
        ImageView searchButton = findViewById(R.id.searchButton);
         resultsListView = findViewById(R.id.resultsListView);
        resultsList = new ArrayList<>();
        resultsList.add("Amman".toLowerCase());
        resultsList.add("Salt".toLowerCase());
        resultsList.add("Zarqa".toLowerCase());
        resultsList.add("Mafraq".toLowerCase());
        resultsList.add("Ma'an".toLowerCase());
        resultsList.add("Ajloun".toLowerCase());
        resultsList.add("Irbid".toLowerCase());
        resultsList.add("Jerash".toLowerCase());
        resultsList.add("Aqaba".toLowerCase());
        resultsList.add("Madaba".toLowerCase());
        resultsList.add("Tafilah".toLowerCase());
        resultsList.add("Karak".toLowerCase());
        resultsList.add("عمان".toLowerCase());
        resultsList.add("السلط".toLowerCase());
        resultsList.add("الزرقاء".toLowerCase());
        resultsList.add("المفرق".toLowerCase());
        resultsList.add("معان".toLowerCase());
        resultsList.add("عجلون".toLowerCase());
        resultsList.add("اربد".toLowerCase());
        resultsList.add("جرش".toLowerCase());
        resultsList.add("العقبة".toLowerCase());
        resultsList.add("مأدبا".toLowerCase());
        resultsList.add("الطفيلة".toLowerCase());
        resultsList.add("الكرك".toLowerCase());
        searchButton.setOnClickListener(v -> search());


        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String searchText = s.toString();
                performSearch(searchText);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        resultsListView.setOnItemClickListener((parent, view, position, id) -> {
            String selectedCity = (String) parent.getItemAtPosition(position);
            navigateToCityPage(selectedCity);
        });


        nav_menu = findViewById(R.id.nav_menu);
        progressBar = findViewById(R.id.applicationProgressBar);
        image_profile = findViewById(R.id.toolBar).findViewById(R.id.profile_image);
        progressBar.setVisibility(View.VISIBLE);
        image_profile.setOnClickListener(v -> {
            Intent intent = new Intent(application_UI.this , Profile_page.class);
            startActivity(intent);
        });

        FirebaseDatabase database_admin = FirebaseDatabase.getInstance();
        DatabaseReference myRef_admin = database_admin.getReference("Admin");
        myRef_admin.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String value = dataSnapshot.child("loginAdmin").child("loginValue").getValue(String.class);
                    if (value != null) {
                        int num = Integer.parseInt(value);
                        if (num == 1){
                            CardView image_card_view = findViewById(R.id.toolBar).findViewById(R.id.image_card_view);
                            nav_menu.setVisibility(View.INVISIBLE);
                            image_card_view.setVisibility(View.INVISIBLE);
                        }else {
                            user_info();
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
        new Handler().postDelayed(() -> progressBar.setVisibility(View.INVISIBLE), 1000);
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.bringToFront();
         navigationView.setNavigationItemSelectedListener(this);
        nav_menu.setOnClickListener(v -> drawerLayout.open());
    }


    private void search() {
        String searchText = searchEditText.getText().toString().trim();
        if (!searchText.isEmpty()){
        performSearch(searchText);}
    }
    private void performSearch(String searchText) {
        List<String> filteredResults = new ArrayList<>();
        if (searchText.isEmpty()) {
            filteredResults.clear();
        } else {
            for (int i = 0; i < resultsList.size(); i++) {
                String item = resultsList.get(i);
                if (item.startsWith(searchText.toLowerCase())) {
                    filteredResults.add(item);
                }
            }

        }
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, filteredResults);
        resultsListView.setAdapter(adapter);

    }

    private void navigateToCityPage(String city) {
        Intent cityIntent = null;
        switch (city) {
            case "amman":
            case "عمان":
                cityIntent = new Intent(application_UI.this, amman_page.class);
                break;
            case "salt":
            case "السلط":
                cityIntent = new Intent(application_UI.this, salt_page.class);
                break;
            case "zarqa":
            case "الزرقاء":
                cityIntent = new Intent(application_UI.this, Zarqa_page.class);
                break;
            case "mafraq":
            case "المفرق":
                cityIntent = new Intent(application_UI.this, Mafraq_page.class);
                break;
            case "ma'an":
            case "معان":
                cityIntent = new Intent(application_UI.this, Maan_page.class);
                break;
            case "ajloun":
            case "عجلون":
                cityIntent = new Intent(application_UI.this, Ajloun_page.class);
                break;
            case "irbid":
            case "اربد":
                cityIntent = new Intent(application_UI.this, Irbid_page.class);
                break;
            case "jerash":
            case "جرش":
                cityIntent = new Intent(application_UI.this, Jerash_page.class);
                break;
            case "aqaba":
            case "العقبة":
                cityIntent = new Intent(application_UI.this, Aqaba_page.class);
                break;
            case "madaba":
            case "مأدبا":
                cityIntent = new Intent(application_UI.this, Madaba_page.class);
                break;
            case "tafilah":
            case "الطفيلة":
                cityIntent = new Intent(application_UI.this, Tafilah_page.class);
                break;
            case "karak":
            case "الكرك":
                cityIntent = new Intent(application_UI.this, Karak_page.class);
                break;
        }

        if (cityIntent != null) {
            startActivity(cityIntent);
        }
    }



    public void user_info(){
         String uid = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("name");
                  userRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                userName_nav = findViewById(R.id.userName_nav);
                                email_nav = findViewById(R.id.email_nav);
                                String name = snapshot.getValue(String.class);
                                String emil = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail();
                                profile_picture = findViewById(R.id.image_profile);
                                image_profile = findViewById(R.id.toolBar).findViewById(R.id.profile_image);
                                userName_nav.setText(name);
                                email_nav.setText(emil);
                                String imageName = "user_profile_images/" + Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid() + ".jpg";
                                FirebaseStorage.getInstance().getReference().child(imageName).getDownloadUrl()
                                        .addOnSuccessListener(uri -> {
                                            Picasso.get().load(uri.toString()).into(image_profile);
                                            Picasso.get().load(uri.toString()).into(profile_picture);
                                        })
                                        .addOnFailureListener(exception -> {
                                            image_profile.setImageResource(R.drawable.ic_person);
                                            profile_picture.setImageResource(R.drawable.ic_person);
                                        });
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });
                    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_account:
                Intent profileIntent = new Intent(application_UI.this,Profile_page.class);
                startActivity(profileIntent);
                break;
            case R.id.nav_setting:
                Intent settingIntent = new Intent(application_UI.this,setting_page.class);
                startActivity(settingIntent);
                break;
            case R.id.nav_about:
                Intent aboutIntent = new Intent(application_UI.this,About_page.class);
                startActivity(aboutIntent);
                break;
            case R.id.logout_menu_item:
                progressBar.setVisibility(View.VISIBLE);
                new Handler().postDelayed(() -> progressBar.setVisibility(View.INVISIBLE), 500);
                String uid = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
                DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("loginUser").child(uid).child("value");
                userRef.setValue("0");
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(this, "Signed out", Toast.LENGTH_SHORT).show();
                Intent logoutIntent = new Intent(application_UI.this , get_started_page.class);
                startActivity(logoutIntent);
                finish();
                break;
            case R.id.nav_Provinces:
                Intent provincesIntent = new Intent(application_UI.this,provinces_page.class);
                startActivity(provincesIntent);
                break;
            case R.id.nav_museum:
                Intent museumIntent = new Intent(application_UI.this,museums_page.class);
                startActivity(museumIntent);
                break;
            case R.id.nav_antiquities:
                Intent antiquitiesIntent = new Intent(application_UI.this,antiquities_page.class);
                startActivity(antiquitiesIntent);
                break;
            case R.id.nav_tourist_places:
                Intent placesIntent = new Intent(application_UI.this,tourists_page.class);
                startActivity(placesIntent);
                break;
        }
        return false;
    }


    public void provinces_linear(View view) {
        Intent intent = new Intent(application_UI.this,provinces_page.class);
        startActivity(intent);
    }

    public void museum(View view) {
        Intent intent = new Intent(application_UI.this,museums_page.class);
        startActivity(intent);
    }

    public void Antiquities(View view) {
        Intent intent = new Intent(application_UI.this,antiquities_page.class);
        startActivity(intent);
    }

    public void Tourists(View view) {
        Intent intent = new Intent(application_UI.this,tourists_page.class);
        startActivity(intent);
    }
}