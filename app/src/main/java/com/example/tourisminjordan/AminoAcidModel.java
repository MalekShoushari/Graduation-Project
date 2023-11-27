package com.example.tourisminjordan;

import android.graphics.drawable.Drawable;

public class AminoAcidModel {
    String CityName,the_place_name,about_the_place,description_of_the_place,working_hours,location_link;
    int image;
    String loginValue;
    Drawable mImageUri;
    String Id = null;
    public AminoAcidModel(Drawable resource, String id, String the_place_name, String about_the_place, String description_of_place, String working_hour, String location_links) {
    this.mImageUri = resource;
    this.Id = id;
    this.the_place_name = the_place_name;
    this.about_the_place = about_the_place;
    this.description_of_the_place = description_of_place;
    this.working_hours = working_hour;
    this.location_link = location_links;
    }
    public AminoAcidModel(String loginValue) {
        this.loginValue = loginValue;
    }
    public AminoAcidModel(String cityName,int image) {
        CityName = cityName;
        this.image = image;
    }

    public Drawable getmImageUri() {
        return mImageUri;
    }

    public AminoAcidModel(String id, String the_place_name, String about_the_place, String description_of_the_place, String working_hours, String location_link) {
     this.Id = id;
     this.the_place_name = the_place_name;
     this.about_the_place = about_the_place;
     this.description_of_the_place = description_of_the_place;
     this.working_hours = working_hours;
     this.location_link = location_link;
    }
    public AminoAcidModel(String the_place_name,String about_the_place) {
        this.the_place_name = the_place_name;
        this.about_the_place = about_the_place;
    }

    public String getLoginValue() {return loginValue;}

    public String getId() {return Id;}

    public String getThe_place_name() {
        return the_place_name;
    }

    public String getAbout_the_place() {
        return about_the_place;
    }

    public String getDescription_of_the_place() {
        return description_of_the_place;
    }

    public String getWorking_hours() {
        return working_hours;
    }

    public String getLocation_link() {
        return location_link;
    }

    public String getCityName() {
        return CityName;
    }

    public int getImage() {
        return image;
    }




}


