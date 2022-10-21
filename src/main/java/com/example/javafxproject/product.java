package com.example.javafxproject;

import java.awt.*;

public class product {
    private int id;
    private String name;
    private String size;
    private String type;
    private String colour;
    private int stock;
    private float price;
    private float cost;
    private float promo;
    private java.awt.Image image;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public float getPromo() {
        return promo;
    }

    public void setPromo(float promo) {
        this.promo = promo;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public product(int id, String name, String size, String type, String colour, int stock, float price, float cost, float promo, Image image) {
        this.id = id;
        this.name = name;
        this.size = size;
        this.type = type;
        this.colour = colour;
        this.stock = stock;
        this.price = price;
        this.cost = cost;
        this.promo = promo;
        this.image = image;
    }

    public product(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", size='" + size + '\'' +
                ", type='" + type + '\'' +
                ", colour='" + colour + '\'' +
                ", stock=" + stock +
                ", price=" + price +
                ", cost=" + cost +
                ", promo=" + promo +
                ", image=" + image +
                '}';
    }
}
