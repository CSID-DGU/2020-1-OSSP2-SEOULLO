package com.seoullo.seoullotour.Models;

import java.util.List;

public class Comment {

    private String comment;
    private String user_id;
    private List<com.seoullo.seoullotour.Models.Like> likes;
    private String date_created;

    public Comment() {
    }

    public Comment(String comment, String user_id, List<com.seoullo.seoullotour.Models.Like> likes, String date_created) {
        this.comment = comment;
        this.user_id = user_id;
        this.likes = likes;
        this.date_created = date_created;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public List<com.seoullo.seoullotour.Models.Like> getLikes() {
        return likes;
    }

    public void setLikes(List<com.seoullo.seoullotour.Models.Like> likes) {
        this.likes = likes;
    }

    public String getDate_created() {
        return date_created;
    }

    public void setDate_created(String date_created) {
        this.date_created = date_created;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "comment='" + comment + '\'' +
                ", user_id='" + user_id + '\'' +
                ", likes=" + likes +
                ", date_created='" + date_created + '\'' +
                '}';
    }
}
