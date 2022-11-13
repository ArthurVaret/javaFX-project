package models;

public class Cloth extends Product{
    private int size;

    public Cloth(int id, String name, double prize, double cost, int stock, int size){
        super(id, name, prize, cost, stock);
        setSize(size);
    }

    public int getSize(){
        return this.size;
    }

    public String getType() { return "Cloth"; }

    public void setSize(int size) {
        try {
            if (size < 34 || size > 54 || size % 2 != 0) throw new IllegalArgumentException("Wrong cloth size (" + size +") !");
            this.size = size;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }
}