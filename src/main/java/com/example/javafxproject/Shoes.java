package com.example.javafxproject;

public class Shoes extends product{
    private int shoeSize;

    public Shoes(String name, double price, int nbItems, int shoeSize) {
        super(name, price, nbItems);
        this.shoeSize = shoeSize;
    }

    public int getShoeSize() {
        return shoeSize;
    }
    public void applyDiscount(){
        setPrice(getPrice()*(1-0.2));
    }
    public void setShoeSize(int shoeSize) {
        try {
            if(50>=shoeSize && shoeSize>=36){
                this.shoeSize = shoeSize;
            }
            else{
                throw new IllegalArgumentException("Wrong shoe size");
            }
        }
        catch(IllegalArgumentException e){
            System.out.println(e);
        }
    }

    @Override
    public String toString() {
        return "Shoes{" +
                "shoeSize=" + shoeSize +
                "number=" + this.getNumber() +
                ", name='" + this.getName() + '\'' +
                ", price=" + this.getPrice() +
                ", nbItems=" + this.getNbItems() +
                '}';
    }
}
