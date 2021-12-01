package com.grup2.jaestic_admin.Model;

public class FoodCategory2 {

    private int id;
    private int imageName;
    private String name;
    private String description;
    private String imgName;

    /*public FoodCategory(String name, String imgName){
        this.name = name;
        this.imgName = imgName;
    }*/
    public FoodCategory2(int imageName, String name){
        this.imageName = imageName;
        this.name = name;
    }
    //public FoodCategory(String name){ this.name = name; }
    /*Getters & Setters*/
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getImage() { return imgName; }
    public void setImage(int image) { this.imgName = imgName; }
    public int getImageName() { return imageName; }
    public void setImageName(int imageName) { this.imageName = imageName; }
}

