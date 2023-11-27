package com.example.tourisminjordan;

public class comment_acide_model {
    String UserName, time,comment;
    int image;
    public comment_acide_model(String UserName, String time, int image ,String comment) {
        this.UserName = UserName;
        this.image = image;
        this.time = time;
        this.comment = comment;
    }

    public String getUserName() {
        return UserName;
    }
    public String getComment() {
        return comment;
    }

    public int getImage() {
        return image;
    }

    public String getTime() {
        return time;
    }
}
