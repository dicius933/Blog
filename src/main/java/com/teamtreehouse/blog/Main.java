package com.teamtreehouse.blog;

import com.teamtreehouse.blog.dao.BlogDao;
import com.teamtreehouse.blog.dao.SimpleBlogDao;
import com.teamtreehouse.blog.model.BlogEntry;
import com.teamtreehouse.blog.model.Comment;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.*;

import static spark.Spark.*;

/**
 * Created by yeshua on 11/18/2017.
 */
public class Main {
    public static void main(String[] args) {
        staticFileLocation("/public");
        BlogDao dao = new SimpleBlogDao();

        List<BlogEntry> defaultEntries = new ArrayList<>(Arrays.asList(new BlogEntry("The best day I've ever had", "Programming was a Success!"),
                new BlogEntry("The best second day I've ever had", "I learned how to use Spark"),
                new BlogEntry("The best third I've ever had", "I can build my own websites")));
        dao.setBlogEntries(defaultEntries);

        before((req, res) -> {
            if (req.cookie("password") != null) {
                req.attribute("password", req.cookie("password"));
            }
        });

        before("/:slug/edit", (req, res) -> {
            if (req.cookie("password") != null) {
                req.attribute("password", req.cookie("password"));
            } else{
                res.redirect("/password");
                halt();
            }
        });

    //        before("/new", (req, res) -> {
    //            if (req.session().attribute("password") == null) {
    //                res.redirect("/password");
    //                halt();
    //            }
    //        });



        get("/", (req, res) -> {
            Map<String,Object> model = new HashMap<String, Object>();
            model.put("password",req.attribute("password"));
            model.put("blogEntries", dao.findAllEntries());
            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());

        get("/new", (req, res) -> {
            Map<String,Object> model = new HashMap<String, Object>();
            model.put("password",req.attribute("password"));
            return new ModelAndView(model, "new.hbs");
        }, new HandlebarsTemplateEngine());

        get("/password", (req, res) -> {
            Map<String,Object> model = new HashMap<String, Object>();
            model.put("password", req.attribute("password"));
            return new ModelAndView(model, "password.hbs");
        }, new HandlebarsTemplateEngine());

        get("/edit", (req, res) -> {
            Map<String,Object> model = new HashMap<String, Object>();
            model.put("password",req.attribute("password"));
            return new ModelAndView(model, "edit.hbs");
        }, new HandlebarsTemplateEngine());


        get("/:slug", (req, res) -> {
            Map<String,Object> model = new HashMap<>();
            BlogEntry blogEntry = dao.findEntryBySlug(req.params("slug"));
            model.put("password", req.attribute("password"));
            model.put("entry", blogEntry);
            return new ModelAndView(model, "detail.hbs");
        }, new HandlebarsTemplateEngine());

        post("/password", (req, res) -> {
            Map<String,Object> model = new HashMap<String, Object>();
            String password = req.queryParams("password");
            res.cookie("password", password);
            model.put("password", password);
            res.redirect("/edit");
            return null;
        });


        post("/new", (req, res) -> {
            String title = req.queryParams("title");
            String entry = req.queryParams("entry");
            BlogEntry blogEntry = new BlogEntry(title, entry);
            dao.addEntry(blogEntry);
            res.redirect("/");
            return null;
        });



        post("/:slug", (req, res) -> {
            String author = req.queryParams("author");
            String content = req.queryParams("content");
            Comment comment = new Comment(author, content);
            BlogEntry blogEntry = dao.findEntryBySlug(req.params("slug"));
            blogEntry.addComment(comment);
            res.redirect(req.params("slug"));
            return null;
        });

        get("/:slug/edit", (req, res) -> {
            Map<String,Object> model = new HashMap<String, Object>();
            BlogEntry blogEntry = dao.findEntryBySlug(req.params("slug"));
            model.put("entry", blogEntry);
            model.put("password",req.attribute("password"));

            return new ModelAndView(model, "edit.hbs");
        }, new HandlebarsTemplateEngine());

        post("/:slug/edit", (req, res) -> {
            Map<String,Object> model = new HashMap<String, Object>();
            BlogEntry blogEntry = dao.findEntryBySlug(req.params("slug"));
            model.put("password",req.attribute("password"));
            String title = req.queryParams("title");
            String entry =req.queryParams("entry");
            blogEntry.setTitle(title);
            blogEntry.setEntry(entry);
            res.redirect("/");
            return null;
        });







    }
}
