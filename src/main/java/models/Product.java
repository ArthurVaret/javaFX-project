package models;

public abstract class Product implements Discount, Comparable<Product>{
    private final int id;
    private String name;
    private double price;
    private double cost;
    private int stock;
    protected Product(int id, String name, double price, double cost, int stock){
        this.id = id;
        this.name = name;
        setPrice(price);
        setCost(cost);
        setStock(stock);
    }
    public int getId() { return id; }

    public String getName(){
        return this.name;
    }
    public void setName(String name){
        this.name=name;
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
            e.printStackTrace();
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
            e.printStackTrace();
        }
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        try {
            if(cost >=0){
                this.cost = cost;
            }
            else{
                throw new IllegalArgumentException("Negative number");
            }
        }
        catch(IllegalArgumentException e){
            e.printStackTrace();
        }
    }

    public abstract String getType();
    public abstract int getSize();
    @Override
    public String toString() {
        return id + ". " + name;
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
                p.getCost() == this.cost &&
                p.getSize() == this.getSize()
                ;
    }
}

