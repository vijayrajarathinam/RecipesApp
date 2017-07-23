package com.collegecronista.vijay.recipes;

/**
 * Created by vijay on 15/07/2017.
 */

public class RecipeModel {
    private String title;
    private String description;
    private int id;
    public RecipeModel(int id,String title, String description){
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
