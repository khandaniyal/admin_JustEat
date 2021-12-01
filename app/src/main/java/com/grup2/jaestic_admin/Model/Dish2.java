package com.grup2.jaestic_admin.Model;

import java.util.LinkedList;

public class Dish2 {
    private String name;
    private int price;
    private String description;
    private String imagePath;
    private LinkedList<String> ingredients = new LinkedList<>();

    //Empty builder
    public Dish2(){}
    //builder with the option to add ingredients
    public Dish2(String name, int price, String description, String imagePath, LinkedList<String> ingredients) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.imagePath = imagePath;
        this.ingredients = ingredients;
    }

    //builder without the option to add ingredients
    public Dish2(String name, int price, String description, String imagePath) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.imagePath = imagePath;
    }

    /*Getters & Setters*/
    public int getPrice() { return price; }
    public void setPrice(int price) { this.price = price; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getImagePath() { return imagePath; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public LinkedList<String> getIngredients() { return ingredients; }
    public void setIngredients(LinkedList<String> ingredients) { this.ingredients = ingredients; }
    public int getIngredientCount(){ return this.ingredients.size(); }
}
