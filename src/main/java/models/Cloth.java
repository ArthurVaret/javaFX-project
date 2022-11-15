package models;

public class Cloth extends Product{
    private int size;

    public Cloth(int id, String name, double prize, double cost, int stock, int size, boolean promotion){
        super(id, name, prize, cost, stock, promotion);
        if (promotion) this.applyDiscount();
        setSize(size);
    }

    public int getSize(){
        return this.size;
    }

    public String getType() { return "Cloth"; }

    @Override
    public void applyDiscount() {
        setPrice(getPrice() - getPrice() * 0.3);
    }

    public void setSize(int size) {
        try {
            if (size < 34 || size > 54 || size % 2 != 0) throw new IllegalArgumentException("Wrong cloth size (" + size +") !");
            this.size = size;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }
}