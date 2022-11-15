package models;

public class Accessory extends Product{

    public Accessory(int id, String name, double prize, double cost, int stock, boolean promotion){
        super(id, name, prize, cost, stock, promotion);
        if (promotion) this.applyDiscount();
    }

    public int getSize(){return 0;}
    public String getType() { return "Accessory"; }
    @Override
    public void applyDiscount() {
        setPrice(getPrice() - getPrice() * 0.5);
    }
}