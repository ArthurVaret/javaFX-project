package models;

import java.util.concurrent.atomic.AtomicInteger;

public abstract class Product implements Discount, Comparable<Product>{
    private static AtomicInteger count = new AtomicInteger(1);
    private static double income = 0;
    private int id;
    private int number;
    private String name;
    private double price;
    private int nbItems;
    protected Product(int id, String name, double price, int nbItems){
        this.id = id;
        this.number = count.getAndIncrement();
        this.name=name;
        setPrice(price);
        setNbItems(nbItems);
    }
    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getName(){
        return this.name;
    }
    public void setName(String name){
        this.name=name;
    }

    public static double getIncome() {
        return income;
    }

    public static void setIncome(double income) {
        Product.income = income;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }


    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        try {
            if(price>=0){
                this.price = price;
            }
            else{
                throw new IllegalArgumentException("Negative price");
            }
        }
        catch(IllegalArgumentException e){
            System.out.println(e);
        }

    }

    public int getNbItems() {
        return nbItems;
    }

    public void setNbItems(int nbItems) {
        try {
            if(nbItems>=0){
                this.nbItems = nbItems;
            }
            else{
                throw new IllegalArgumentException("Negative number");
            }
        }
        catch(IllegalArgumentException e){
            System.out.println(e);
        }
    }

    public abstract String getType();

    @Override
    public String toString() {
        return id + ". " + name;
    }
    public void sell(int nbItems){
        try {
            if(nbItems<=getNbItems()){
                setIncome(getIncome()+(nbItems*getPrice()));
                setNbItems(getNbItems()-nbItems);
            }
            else{
                throw new IllegalArgumentException("Product unavailable");
            }
        }
        catch(IllegalArgumentException e) {
            System.out.println(e);
        }
    }
    public void purchase(int nbItems){
        setNbItems(getNbItems()+nbItems);
    }
    public void applyDiscount(){

    }
    public abstract int getSize();
    @Override
    public int compareTo(Product p){
        if (this.price < p.price){
            return -1;
        }
        else if(this.price == p.price){
            return 0;
        }
        else{
            return 1;
        }
    }
}

