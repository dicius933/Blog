package com.teamtreehouse.blog.model;

import java.util.Date;

public class Comment {
    private String author;
    private Date date;
    private String content;

    public Comment(String author,String content ) {
        this.author = author;
        this.content = content;
        this.date = date;
    }

    public String getAuthor() {
        return author;
    }

    public Date getDate() {
        return date;
    }

    public String getContent() {
        return content;
    }

}
