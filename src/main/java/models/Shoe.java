package models;

public class Shoe extends Product{
    private int size;

    public Shoe(int id, String name, double prize, int stock, int size){
        super(id, name, prize, stock);
        setSize(size);
    }

    public int getSize(){
        return this.size;
    }

    public String getType() { return "shoe"; }

    public void setSize(int shoeSize) {
        try {
            if (shoeSize < 36 || shoeSize > 50) throw new IllegalArgumentException("Wrong shoe size !");
            this.size = shoeSize;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }
}