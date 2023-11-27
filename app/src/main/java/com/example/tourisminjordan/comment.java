package com.example.tourisminjordan;

import android.net.Uri;

public class comment {
  String Id = null;
  String Comment = null;
  String time = null;
  String user_name;
  Uri image;
  public comment(){

  }
    public comment(String Id, String Comment, String Time) {
        this.Id = Id;
        this.Comment = Comment;
        this.time = Time;
    }
    public comment( String user_name, String Id, String Comment, String Time) {
        this.user_name = user_name;
        this.Id = Id;
        this.Comment = Comment;
        this.time = Time;
    }
    public comment(Uri image, String user_name, String Id, String Comment, String Time) {
        this.image = image;
        this.user_name = user_name;
        this.Id = Id;
        this.Comment = Comment;
        this.time = Time;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public String getId() {
         return Id;
     }
    public String getUser_name() {
        return user_name;
    }
    public Uri getImage() {
        return image;
    }

    public String getComment() {
         return Comment;
     }
     public String getTime() {
         return time;
     }

 }
