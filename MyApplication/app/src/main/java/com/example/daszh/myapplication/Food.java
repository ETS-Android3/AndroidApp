package com.example.daszh.myapplication;

public class Food {
    private String name;
    private String category;
    private String cshort;
    private String nutrition;
    private String color;
    public int collect;
    public Food() {

    }
    public Food(String a, String b, String c, String d, String e) {
        name = a;
        cshort = b;
        category = c;
        nutrition = d;
        color = e;
        collect = 0;
    }
    public Food(Food f) {
        this.name = f.getName();
        this.category = f.getCategory();
        this.cshort = f.getCshort();
        this.nutrition = f.getNutrition();
        this.color = f.getColor();
        this.collect = f.collect;
    }

    public String getCategory() {
        return category;
    }

    public String getColor() {
        return color;
    }

    public String getCshort() {
        return cshort;
    }

    public String getName() {
        return name;
    }

    public String getNutrition() {
        return nutrition;
    }

}
