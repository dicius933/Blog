package com.teamtreehouse.blog.model;

import com.github.slugify.Slugify;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class BlogEntry {


    private String title;
    private String entry;
    private String slug;
    private String date;
    private DateFormat dateFormat;
    private List<Comment> comments;
    private Date time;
    private Comment comment;





    public BlogEntry(String title, String entry) {
        this.title = title;
        this.entry = entry;
        dateFormat = new SimpleDateFormat("MMMM dd, yyyy  HH:mm ");
        date = dateFormat.format(time =new Date());
        setDate(date);
        comments = new ArrayList<>();
        Slugify slugify = new Slugify();
        slug = slugify.slugify(title);

    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setEntry(String entry) {
        this.entry = entry;
    }
    public boolean addComment(Comment comment) {
        return comments.add(comment);
    }
    public void setComment(Comment comment) {
        this.comment = comment;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getTitle() {
        return title;
    }
    public String getEntry() {
        return entry;
    }
    public String getDate() {
        return date;
    }
    public String getSlug() {
        return slug;
    }
    public List<Comment> getComments(){
        return comments;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BlogEntry blogEntry = (BlogEntry) o;

        if (title != null ? !title.equals(blogEntry.title) : blogEntry.title != null) return false;
        if (entry != null ? !entry.equals(blogEntry.entry) : blogEntry.entry != null) return false;
        return date != null ? date.equals(blogEntry.date) : blogEntry.date == null;

    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (entry != null ? entry.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }
}
