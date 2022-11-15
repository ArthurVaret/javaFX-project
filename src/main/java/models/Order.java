package models;

import java.util.Date;

public class Order {
    private int id;
    private String name;
    private String orderType;
    private int quantity;
    private Date dateOrder;

    public Order(int id, String name, String orderType, int quantity, Date dateOrder) {
        this.id = id;
        this.name = name;
        this.orderType = orderType;
        this.quantity = quantity;
        this.dateOrder = dateOrder;
    }

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

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Date getDateOrder() {
        return dateOrder;
    }

    public void setDateOrder(Date dateOrder) {
        this.dateOrder = dateOrder;
    }

    @Override
    public String toString() {
        return orderType.toUpperCase() +" (" + quantity + ")" + " : " + dateOrder.toString() + " / " + name;
    }
}
