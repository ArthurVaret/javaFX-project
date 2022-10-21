package models;

import javafx.scene.image.Image;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public abstract class Product {
    private int id;
    private String name;
    private String colour;
    private int stock;
    private double price;
    private double cost;
    private Image image;

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

    public double getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Product(int id, String name, double price, int stock, String colour, double cost, Image image) {
        this.id = id;
        this.name = name;
        this.colour = colour;
        this.stock = stock;
        this.price = price;
        this.cost = cost;
        this.image = image;
    }

    public Product(int id, String name, double price, int stock) {
        this(id, name, price, stock, "", price, null);
    }

    public Product(){
        this(-1, "",0,0,"",0, null);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", colour='" + colour + '\'' +
                ", stock=" + stock +
                ", price=" + price +
                ", cost=" + cost +
                ", image=" + image +
                '}';
    }
}
