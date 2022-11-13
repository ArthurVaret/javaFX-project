package models;

import java.util.Date;

public class Order {
    private int id;
    private int orderType;
    private Date dateOrder;

    public Order(int id, int orderType, Date dateOrder) {
        this.id = id;
        this.orderType = orderType;
        this.dateOrder = dateOrder;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrderType() {
        return orderType;
    }

    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }

    public Date getDateOrder() {
        return dateOrder;
    }

    public void setDateOrder(Date dateOrder) {
        this.dateOrder = dateOrder;
    }

    public String toString() {
        return "Order";
    }
}
