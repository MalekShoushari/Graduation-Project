package com.example.tourisminjordan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class provinces_page extends AppCompatActivity implements Recycler_view_interface_provinces{
private final ArrayList<AminoAcidModel> aminoAcidModels = new ArrayList<>();
int[] cityImage = {R.drawable.amman,R.drawable.salt,R.drawable.zarqa
        , R.drawable.qasr_biraqae,R.drawable.khazneh,R.drawable.ajloun_castle
        ,R.drawable.irbid,R.drawable.jerash_ruins,R.drawable.aqaba,
        R.drawable.madba,R.drawable.tafilah_castle,R.drawable.karak};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provinces_page);
        RecyclerView recyclerView = findViewById(R.id.mRecyclerView);
        setUpAminoAcidModels();
        AA_recycel_view_adapter adapter = new AA_recycel_view_adapter(this , aminoAcidModels ,this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
    public void BackIcon(View view) {
        Intent intent = new Intent(provinces_page.this,application_UI.class);
        startActivity(intent);
        finish();
    }
    private void setUpAminoAcidModels(){
        String[] cityName = getResources().getStringArray(R.array.Provinces_Name);
        for (int i = 0 ; i<cityName.length;i++){
            aminoAcidModels.add(new AminoAcidModel(cityName[i],
                    cityImage[i]));
        }
    }

    @Override
    public void onItemClick(int position) {
        switch (position){
            case 0 :
                Intent intentAmman = new Intent(provinces_page.this,amman_page.class);
                startActivity(intentAmman);
                break;
            case 1 :
                Intent intentSalt = new Intent(provinces_page.this,salt_page.class);
                startActivity(intentSalt);
                break;
            case 2:
                Intent intentZarqa = new Intent(provinces_page.this,Zarqa_page.class);
                startActivity(intentZarqa);
                break;
            case 3:
                Intent intentMafraq = new Intent(provinces_page.this,Mafraq_page.class);
                startActivity(intentMafraq);
                break;
            case 4 :
                Intent intentMaan = new Intent(provinces_page.this,Maan_page.class);
                startActivity(intentMaan);
                break;
            case 5 :
                Intent intentAjloun = new Intent(provinces_page.this,Ajloun_page.class);
                startActivity(intentAjloun);
                break;
            case 6 :
                Intent intentIrbid = new Intent(provinces_page.this,Irbid_page.class);
                startActivity(intentIrbid);
                break;
            case 7 :
                Intent intentJerash = new Intent(provinces_page.this,Jerash_page.class);
                startActivity(intentJerash);
                break;
            case 8 :
                Intent intentAqaba = new Intent(provinces_page.this,Aqaba_page.class);
                startActivity(intentAqaba);
                break;
            case 9 :
                Intent intentMadaba = new Intent(provinces_page.this,Madaba_page.class);
                startActivity(intentMadaba);
                break;
            case 10 :
                Intent intentTafilah = new Intent(provinces_page.this,Tafilah_page.class);
                startActivity(intentTafilah);
                break;
            case 11 :
                Intent intentKarak = new Intent(provinces_page.this,Karak_page.class);
                startActivity(intentKarak);
                break;
        }

    }
}