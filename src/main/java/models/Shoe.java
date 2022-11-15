package models;

public class Shoe extends Product{
    private int size;

    public Shoe(int id, String name, double prize, double cost, int stock, int size, boolean promotion){
        super(id, name, prize, cost, stock, promotion);
        if (promotion) this.applyDiscount();
        setSize(size);
    }

    public int getSize(){
        return this.size;
    }

    public String getType() { return "Shoe"; }
    @Override
    public void applyDiscount() {
        setPrice(getPrice() - getPrice() * 0.2);
    }

    public void setSize(int size) {
        try {
            if (size < 36 || size > 50) throw new IllegalArgumentException("Wrong shoe size (" + size +")!");
            this.size = size;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }
}