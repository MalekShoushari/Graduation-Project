package com.example.tourisminjordan;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class entry_info extends AppCompatActivity {
    ProgressBar progressBar;
    DatabaseReference myRef_amman = null;
    DatabaseReference myRef_Salt = null;
    DatabaseReference myRef_zarqa = null;
    DatabaseReference myRef_mafraq = null;
    DatabaseReference myRef_maan = null;
    DatabaseReference myRef_ajloun = null;
    DatabaseReference myRef_irbid = null;
    DatabaseReference myRef_jerash = null;
    DatabaseReference myRef_aqaba = null;
    DatabaseReference myRef_madaba = null;
    DatabaseReference myRef_tafilah = null;
    DatabaseReference myRef_karak = null;
    DatabaseReference myRef_antiquities = null;
    DatabaseReference myRef_museums = null;
    DatabaseReference myRef_places = null;

    private static final int REQUEST_CODE_PICK_IMAGE = 1;
    private Uri selectedImageUri;
    private static final int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_info);
        progressBar = findViewById(R.id.applicationProgressBar);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef_amman = database.getReference("Provinces").child("Amman");
        myRef_Salt = database.getReference("Provinces").child("Salt");
        myRef_zarqa = database.getReference("Provinces").child("Zarqa");
        myRef_mafraq = database.getReference("Provinces").child("Mafraq");
        myRef_maan = database.getReference("Provinces").child("Maan");
        myRef_ajloun = database.getReference("Provinces").child("Ajloun");
        myRef_irbid = database.getReference("Provinces").child("Irbid");
        myRef_jerash = database.getReference("Provinces").child("Jerash");
        myRef_aqaba = database.getReference("Provinces").child("Aqaba");
        myRef_madaba = database.getReference("Provinces").child("Madaba");
        myRef_tafilah = database.getReference("Provinces").child("Tafilah");
        myRef_karak = database.getReference("Provinces").child("Karak");
        myRef_antiquities = database.getReference("Provinces").child("Antiquities");
        myRef_museums = database.getReference("Provinces").child("Museums");
        myRef_places = database.getReference("Provinces").child("Places");
        Button buttonChooseImage = findViewById(R.id.button_choose_image);
        Button save_btn = findViewById(R.id.save_btn);
        EditText the_place_name = findViewById(R.id.the_place_name);
        EditText about_the_place = findViewById(R.id.about_the_place);
        EditText description_of_the_place = findViewById(R.id.description_of_the_place);
        EditText working_hours = findViewById(R.id.working_hours);
        EditText location_link = findViewById(R.id.location_link);
        String[] items = new String[]{
                getString(R.string.choose_the_province),
                getString(R.string.amman),
                getString(R.string.al_salt),
                getString(R.string.al_zarqa),
                getString(R.string.mafraq),
                getString(R.string.maan),
                getString(R.string.ajloun),
                getString(R.string.irbid),
                getString(R.string.jerash),
                getString(R.string.aqaba),
                getString(R.string.madaba),
                getString(R.string.tafilah),
                getString(R.string.karak)
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinner = findViewById(R.id.spinner);
        spinner.setAdapter(adapter);
        String[] itemPlaceCategory = new String[]{
                getString(R.string.choose_place_category),
                getString(R.string.museums),
                getString(R.string.antiquities),
                getString(R.string.tourist_places)
        };
        ArrayAdapter<String> adapterPlaceCategory = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, itemPlaceCategory);
        adapterPlaceCategory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinnerPlaceCategory = findViewById(R.id.spinner_category);
        spinnerPlaceCategory.setAdapter(adapterPlaceCategory);
        save_btn.setOnClickListener(v -> {
            String city_name = the_place_name.getText().toString();
            String about_place = about_the_place.getText().toString();
            String description_of_place = description_of_the_place.getText().toString();
            String working_hour = working_hours.getText().toString();
            String location_links = location_link.getText().toString();
            int selected_city_index = spinner.getSelectedItemPosition();
            int selected_place_category = spinnerPlaceCategory.getSelectedItemPosition();
            if (city_name.isEmpty()&&about_place.isEmpty()&& description_of_place.isEmpty()&&working_hour.isEmpty()&&location_links.isEmpty()&&selected_city_index==0&&selected_place_category==0){
                Toast.makeText(this, R.string.please_enter_the_information, Toast.LENGTH_SHORT).show();
            }else {
                progressBar.setVisibility(View.VISIBLE);
                new Handler().postDelayed(() -> {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(this, R.string.added_successfully, Toast.LENGTH_SHORT).show();
                    switch (selected_city_index) {
                        case 1:
                            String amman_places = "Amman_places";
                            String id_amman = myRef_amman.push().getKey();
                            AminoAcidModel aminoAcidModel = new AminoAcidModel(id_amman, city_name, about_place, description_of_place, working_hour, location_links);
                            if (id_amman != null) {
                                myRef_amman.child(id_amman).setValue(aminoAcidModel);
                                uploadImage(id_amman, amman_places);
                            }
                            break;
                        case 2:
                            String salt_places = "Salt_places";
                            String id_salt = myRef_Salt.push().getKey();
                            AminoAcidModel aminoAcidModel1 = new AminoAcidModel(id_salt, city_name, about_place, description_of_place, working_hour, location_links);
                            if (id_salt != null) {
                                myRef_Salt.child(id_salt).setValue(aminoAcidModel1);
                                uploadImage(id_salt, salt_places);

                            }
                            break;
                        case 3:
                            String zarqa_places = "Zarqa_places";
                            String id_zarqa = myRef_zarqa.push().getKey();
                            AminoAcidModel aminoAcidModel2 = new AminoAcidModel(id_zarqa, city_name, about_place, description_of_place, working_hour, location_links);
                            if (id_zarqa != null) {
                                myRef_zarqa.child(id_zarqa).setValue(aminoAcidModel2);
                                uploadImage(id_zarqa, zarqa_places);

                            }
                            break;
                        case 4:
                            String mafraq_places = "Mafraq_places";
                            String id_mafraq = myRef_mafraq.push().getKey();
                            AminoAcidModel aminoAcidModel3 = new AminoAcidModel(id_mafraq, city_name, about_place, description_of_place, working_hour, location_links);
                            if (id_mafraq != null) {
                                myRef_mafraq.child(id_mafraq).setValue(aminoAcidModel3);
                                uploadImage(id_mafraq, mafraq_places);
                            }
                            break;
                        case 5:
                            String maan_places = "maan_places";
                            String id_maan = myRef_maan.push().getKey();
                            AminoAcidModel aminoAcidModel4 = new AminoAcidModel(id_maan, city_name, about_place, description_of_place, working_hour, location_links);
                            if (id_maan != null) {
                                myRef_maan.child(id_maan).setValue(aminoAcidModel4);
                                uploadImage(id_maan, maan_places);
                            }
                            break;
                        case 6:
                            String ajloun_places = "Ajloun_places";
                            String id_ajloun = myRef_ajloun.push().getKey();
                            AminoAcidModel aminoAcidModel5 = new AminoAcidModel(id_ajloun, city_name, about_place, description_of_place, working_hour, location_links);
                            if (id_ajloun != null) {
                                myRef_ajloun.child(id_ajloun).setValue(aminoAcidModel5);
                                uploadImage(id_ajloun, ajloun_places);
                            }
                            break;
                        case 7:
                            String irbid_places = "Irbid_places";
                            String id_irbid = myRef_irbid.push().getKey();
                            AminoAcidModel aminoAcidModel6 = new AminoAcidModel(id_irbid, city_name, about_place, description_of_place, working_hour, location_links);
                            if (id_irbid != null) {
                                myRef_irbid.child(id_irbid).setValue(aminoAcidModel6);
                                uploadImage(id_irbid, irbid_places);
                            }
                            break;
                        case 8:
                            String jerash_places = "Jerash_places";
                            String id_jerash = myRef_jerash.push().getKey();
                            AminoAcidModel aminoAcidModel7 = new AminoAcidModel(id_jerash, city_name, about_place, description_of_place, working_hour, location_links);
                            if (id_jerash != null) {
                                myRef_jerash.child(id_jerash).setValue(aminoAcidModel7);
                                uploadImage(id_jerash, jerash_places);
                            }
                            break;
                        case 9:
                            String aqaba_places = "Aqaba_places";
                            String id_aqaba = myRef_aqaba.push().getKey();
                            AminoAcidModel aminoAcidModel8 = new AminoAcidModel(id_aqaba, city_name, about_place, description_of_place, working_hour, location_links);
                            if (id_aqaba != null) {
                                myRef_aqaba.child(id_aqaba).setValue(aminoAcidModel8);
                                uploadImage(id_aqaba, aqaba_places);
                            }
                            break;
                        case 10:
                            String madaba_places = "Madaba_places";
                            String id_madaba = myRef_madaba.push().getKey();
                            AminoAcidModel aminoAcidModel9 = new AminoAcidModel(id_madaba, city_name, about_place, description_of_place, working_hour, location_links);
                            if (id_madaba != null) {
                                myRef_madaba.child(id_madaba).setValue(aminoAcidModel9);
                                uploadImage(id_madaba, madaba_places);
                            }
                            break;
                        case 11:
                            String tafilah_places = "Tafilah_places";
                            String id_tafilah = myRef_tafilah.push().getKey();
                            AminoAcidModel aminoAcidModel10 = new AminoAcidModel(id_tafilah, city_name, about_place, description_of_place, working_hour, location_links);
                            if (id_tafilah != null) {
                                myRef_tafilah.child(id_tafilah).setValue(aminoAcidModel10);
                                uploadImage(id_tafilah, tafilah_places);
                            }
                            break;
                        case 12:
                            String karak_places = "Karak_places";
                            String id_karak = myRef_karak.push().getKey();
                            AminoAcidModel aminoAcidModel11 = new AminoAcidModel(id_karak, city_name, about_place, description_of_place, working_hour, location_links);
                            if (id_karak != null) {
                                myRef_karak.child(id_karak).setValue(aminoAcidModel11);
                                uploadImage(id_karak, karak_places);
                            }
                            break;
                        default:
                            break;
                    }
                    switch (selected_place_category) {
                        case 1:
                            String Museums = "Museums";
                            String id_Museums = myRef_museums.push().getKey();
                            AminoAcidModel aminoAcidModel_Museums = new AminoAcidModel(id_Museums, city_name, about_place, description_of_place, working_hour, location_links);
                            if (id_Museums != null) {
                                myRef_museums.child(id_Museums).setValue(aminoAcidModel_Museums);
                                uploadImage(id_Museums, Museums);
                            }
                            break;
                        case 2:
                            String Antiquities = "Antiquities";
                            String id_Antiquities = myRef_antiquities.push().getKey();
                            AminoAcidModel aminoAcidModel_Antiquities = new AminoAcidModel(id_Antiquities, city_name, about_place, description_of_place, working_hour, location_links);
                            if (id_Antiquities != null) {
                                myRef_antiquities.child(id_Antiquities).setValue(aminoAcidModel_Antiquities);
                                uploadImage(id_Antiquities, Antiquities);
                            }
                            break;
                        case 3:
                            String Tourist = "Tourist";
                            String id_Tourist = myRef_places.push().getKey();
                            AminoAcidModel aminoAcidModel_Tourist = new AminoAcidModel(id_Tourist, city_name, about_place, description_of_place, working_hour, location_links);
                            if (id_Tourist != null) {
                                myRef_places.child(id_Tourist).setValue(aminoAcidModel_Tourist);
                                uploadImage(id_Tourist, Tourist);
                            }
                            break;
                    }
                    the_place_name.setText("");
                    about_the_place.setText("");
                    description_of_the_place.setText("");
                    working_hours.setText("");
                    location_link.setText("");
                    spinner.setSelection(0);
                }, 2000);
            }
        });
        buttonChooseImage.setOnClickListener(v -> openFileChooser());

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_PICK_IMAGE) {
            selectedImageUri = data.getData();
        }
    }
    private void uploadImage(String id,String name) {
        StorageReference storageRef = FirebaseStorage.getInstance().getReference().child(name).child(id+".jpg");
        storageRef.putFile(selectedImageUri)
                .addOnSuccessListener(taskSnapshot -> {
                    storageRef.getDownloadUrl().addOnSuccessListener(uri -> Toast.makeText(this, "The image has been uploaded successfully!", Toast.LENGTH_SHORT).show());
                })
                .addOnFailureListener(exception -> Toast.makeText(this, "An error occurred. Make sure the image is loaded correctly, or try again later", Toast.LENGTH_SHORT).show());
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }


    public void cancel(View view) {
        progressBar.setVisibility(View.VISIBLE);
        new Handler().postDelayed(() -> {
            progressBar.setVisibility(View.INVISIBLE);
            Intent intent = new Intent(entry_info.this, dashboard_admin.class);
            startActivity(intent);
            finish();
        }, 1000);
    }
}