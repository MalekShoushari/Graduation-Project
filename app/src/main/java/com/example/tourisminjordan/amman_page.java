package com.example.tourisminjordan;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

public class amman_page extends AppCompatActivity  {
    private DatabaseReference myRef;
    private ArrayList<AminoAcidModel> aminoAcidModelsAmman;
    private places_adapter adapter;
    private ListView listView;
    String amman_places;
    int num;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amman_page);
        listView = findViewById(R.id.list_view_amman);
        ImageView add_place=findViewById(R.id.add_place);
        FirebaseDatabase database_admin = FirebaseDatabase.getInstance();
        DatabaseReference myRef_admin = database_admin.getReference("Admin");
        myRef_admin.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String value = dataSnapshot.child("loginAdmin").child("loginValue").getValue(String.class);
                    if (value != null) {
                         num = Integer.parseInt(value);
                         if (num == 1) {
                             add_place.setVisibility(View.VISIBLE);
                         }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        aminoAcidModelsAmman = new ArrayList<>();
        myRef = database.getReference("Provinces").child("Amman");

        listView.setOnItemClickListener((parent, view, position, id) -> {
            AminoAcidModel pos_amman =aminoAcidModelsAmman.get(position);
            Intent info_page_amman = new Intent(getApplicationContext(), places_info_page.class);
            info_page_amman.putExtra("place_name",pos_amman.getThe_place_name());
            info_page_amman.putExtra("place_description",pos_amman.getDescription_of_the_place());
            info_page_amman.putExtra("place_working_hours",pos_amman.getWorking_hours());
            info_page_amman.putExtra("place_location_link",pos_amman.getLocation_link());
            info_page_amman.putExtra("imageName",amman_places);
            info_page_amman.putExtra("id",pos_amman.getId());
            startActivity(info_page_amman);
        });
             listView.setOnItemLongClickListener((parent, view, position, id) -> {
                 if (num == 1) {
                     LayoutInflater layoutInflater = LayoutInflater.from(this);
                     View view_amman = layoutInflater.inflate(R.layout.edit_provinces, null);
                     AlertDialog alertD = new AlertDialog.Builder(this).create();
                     Button edit = view_amman.findViewById(R.id.edit_btn);
                     Button delete = view_amman.findViewById(R.id.cancel_btn);
                     EditText the_place_name = view_amman.findViewById(R.id.the_place_name);
                     EditText about_the_place = view_amman.findViewById(R.id.about_the_place);
                     EditText description_of_the_place = view_amman.findViewById(R.id.description_of_the_place);
                     EditText working_hours = view_amman.findViewById(R.id.working_hours);
                     EditText location_link = view_amman.findViewById(R.id.location_link);
                     AminoAcidModel aminoAcidModel_edit = aminoAcidModelsAmman.get(position);
                     the_place_name.setText(aminoAcidModel_edit.getThe_place_name());
                     about_the_place.setText(aminoAcidModel_edit.getAbout_the_place());
                     description_of_the_place.setText(aminoAcidModel_edit.getDescription_of_the_place());
                     working_hours.setText(aminoAcidModel_edit.getWorking_hours());
                     location_link.setText(aminoAcidModel_edit.getLocation_link());
                     edit.setOnClickListener(v -> {
                         myRef.child(aminoAcidModel_edit.getId());
                         String new_the_place_name = the_place_name.getText().toString();
                         String new_about_the_place = about_the_place.getText().toString();
                         String new_description_of_the_place = description_of_the_place.getText().toString();
                         String new_working_hours = working_hours.getText().toString();
                         String new_location_link = location_link.getText().toString();
                         AminoAcidModel afterUpdate = new AminoAcidModel(aminoAcidModel_edit.getId(), new_the_place_name, new_about_the_place, new_description_of_the_place, new_working_hours, new_location_link);
                         myRef.child(aminoAcidModel_edit.getId()).setValue(afterUpdate);
                         alertD.cancel();
                     });
                     delete.setOnClickListener(v -> {
                         myRef.child(aminoAcidModel_edit.getId()).removeValue();
                         alertD.cancel();
                     });
                     alertD.setView(view_amman);
                     alertD.show();
                 }
                return true;
            });
    }

    @Override
    protected void onStart() {
        super.onStart();
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                aminoAcidModelsAmman.clear();
                if (snapshot.exists()) {
                    for (DataSnapshot amman_info : snapshot.getChildren()) {
                        String id = amman_info.getKey();
                        amman_places = "Amman_places";
                        assert id != null;
                        FirebaseStorage.getInstance().getReference().child(amman_places).child(id+".jpg").getDownloadUrl()
                                .addOnSuccessListener(uri -> Glide.with(getApplicationContext())
                                        .load(uri)
                                        .into(new CustomTarget<Drawable>() {
                                            @Override
                                            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                                String about_the_place = amman_info.child("about_the_place").getValue(String.class);
                                                String the_place_name = amman_info.child("the_place_name").getValue(String.class);
                                                String description_of_place = amman_info.child("description_of_the_place").getValue(String.class);
                                                String working_hour = amman_info.child("working_hours").getValue(String.class);
                                                String location_links = amman_info.child("location_link").getValue(String.class);
                                                AminoAcidModel amman = new AminoAcidModel(resource, id, the_place_name, about_the_place, description_of_place, working_hour, location_links);
                                                aminoAcidModelsAmman.add(amman);
                                                adapter = new places_adapter(getApplicationContext(), aminoAcidModelsAmman, R.layout.card_view);
                                                listView.setAdapter(adapter);
                                            }

                                            @Override
                                            public void onLoadCleared(@Nullable Drawable placeholder) {
                                            }
                                        }))
                                .addOnFailureListener(exception -> {
                                });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "loadPost:onCancelled", error.toException());
            }
        });
    }


    public void BackIconAmmanPage(View view) {
        Intent intentAmman = new Intent(amman_page.this, provinces_page.class);
        startActivity(intentAmman);
        finish();
    }

    public void add_place(View view) {
        Intent intentAmman = new Intent(amman_page.this, entry_info.class);
        startActivity(intentAmman);
        finish();
    }
}
