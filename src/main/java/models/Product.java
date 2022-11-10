package models;

import java.util.concurrent.atomic.AtomicInteger;

public abstract class Product implements Discount, Comparable<Product>{
    private static AtomicInteger count = new AtomicInteger(1);
    private static double income = 0;
    private int id;
    private int number;
    private String name;
    private double price;
    private int stock;
    protected Product(int id, String name, double price, int stock){
        this.id = id;
        this.number = count.getAndIncrement();
        this.name=name;
        setPrice(price);
        setStock(stock);
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

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        try {
            if(stock >=0){
                this.stock = stock;
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
    public abstract int getSize();
    @Override
    public String toString() {
        return id + ". " + name;
    }
    public void sell(int nbItems){
        try {
            if(nbItems<= getStock()){
                setIncome(getIncome()+(nbItems*getPrice()));
                setStock(getStock()-nbItems);
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
        setStock(getStock()+nbItems);
    }
    public void applyDiscount(){

    }

    @Override
    public int compareTo(Product p){
        return Double.compare(this.price, p.price);
    }

    @Override
    public boolean equals(Object o){
        if (o == this)                      return true;
        if (!(o instanceof Product p))      return false;
        return  p.getId() == this.id &&
                p.getName().equals(this.name) &&
                p.getType().equals(this.getType()) &&
                p.getPrice() == this.price &&
                p.getStock() == this.stock &&
                p.getSize() == this.getSize()
                ;
    }
}

