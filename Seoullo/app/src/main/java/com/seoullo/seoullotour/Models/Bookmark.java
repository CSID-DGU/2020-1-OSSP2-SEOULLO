package com.seoullo.seoullotour.Models;

public class Bookmark {

    private String user_id;
    private String photo_id;
    private String image_name;

    public Bookmark() {
    }

    public Bookmark(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getPhoto_id() { return photo_id; }

    public void setPhoto_id(String photo_id) { this.photo_id = photo_id; }

    public String getImage_name() { return image_name; }

    public void setImage_name(String image_name) { this.image_name = image_name; }
    @Override
    public String toString() {
        return super.toString();
    }
}
