package com.example.javafxproject;
public class Clothes extends product{
    private int size;

    public Clothes(String name, double price, int nbItems, int size) {
        super(name, price, nbItems);
        setSize(size);
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        try {
            if(54>=size && size>=34){
                this.size = size;
            }
            else{
                throw new IllegalArgumentException("Wrong size");
            }
        }
        catch(IllegalArgumentException e){
            System.out.println(e);
        }
    }
    public void applyDiscount(){
        setPrice(getPrice()*(1-0.3));
    }
    @Override
    public String toString() {
        return "Clothes{" +
                "size=" + size +
                "number=" + this.getNumber() +
                ", name='" + this.getName() + '\'' +
                ", price=" + this.getPrice() +
                ", nbItems=" + this.getNbItems() +
                '}';
    }
}
