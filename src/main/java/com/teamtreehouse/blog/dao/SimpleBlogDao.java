package com.teamtreehouse.blog.dao;

import com.teamtreehouse.blog.exception.NotFoundException;
import com.teamtreehouse.blog.model.BlogEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yeshua on 11/15/2017.
 */
public class SimpleBlogDao implements BlogDao {

    List<BlogEntry> blogEntries;

    public SimpleBlogDao(){
        blogEntries = new ArrayList<>();
    }

    @Override
    public boolean addEntry(BlogEntry blogEntry)
    {
        return blogEntries.add(blogEntry);
    }

    @Override
    public List<BlogEntry> findAllEntries()
    {
        return new ArrayList<>(blogEntries);
    }

    @Override
    public BlogEntry findEntryBySlug(String slug) {
        return blogEntries.stream()
                .filter(entry -> entry.getSlug().equals(slug))
                .findFirst()
                .orElseThrow(NotFoundException::new);
    }

    public void setBlogEntries (List<BlogEntry> blogEntries){
        this.blogEntries=blogEntries;
    }
}
