package com.example.javafxproject;

public class Accessories extends product{

    public Accessories(String name, double price, int nbItems) {
        super(name, price, nbItems);
    }

    @Override
    public String toString() {
        return "Accessories{" +
                "number=" + this.getNumber() +
                ", name='" + this.getName() + '\'' +
                ", price=" + this.getPrice() +
                ", nbItems=" + this.getNbItems() +
                "}";
    }
    public void applyDiscount(){
        setPrice(getPrice()*(1-0.5));
    }
}
