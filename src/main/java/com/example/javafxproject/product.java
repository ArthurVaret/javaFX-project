package com.example.javafxproject;

import com.example.javafxproject.Discount;

import java.util.concurrent.atomic.AtomicInteger;

public abstract class product implements Discount, Comparable<product>{
    private static AtomicInteger count = new AtomicInteger(1);
    private static double income = 0;
    private int number;
    private String name;
    private double price;
    private int nbItems;
    public product(String name, double price, int nbItems){
        this.number = count.getAndIncrement();
        this.name=name;
        setPrice(price);
        setNbItems(nbItems);
    }
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
        product.income = income;
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

    @Override
    public String toString() {
        return "Product{" +
                "number=" + number +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", nbItems=" + nbItems +
                '}';
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
    @Override
    public int compareTo(product p){
        if(this.price<p.price){
            return -1;
        }
        else if(this.price==p.price){
            return 0;
        }
        else{
            return 1;
        }
    }
}

