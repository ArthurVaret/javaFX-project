package models;

import java.util.Date;

public class Order {
    private int id;
    private String name;
    private String operation;
    private int quantity;
    private Date date;

    public Order(int id, String name, String operation, int quantity, Date date) {
        this.id = id;
        this.name = name;
        this.operation = operation;
        this.quantity = quantity;
        this.date = date;
    }

    public Integer getId() {
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

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return operation.toUpperCase() +" (" + quantity + ")" + " : " + date.toString() + " / " + name;
    }
}
