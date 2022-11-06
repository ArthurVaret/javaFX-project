package models;

public class Accessory extends Product{

    public Accessory(int id, String name, double prize, int stock){
        super(id, name, prize, stock);
    }

    public int getSize(){return 0;}
    public String getType() { return "accessory"; }
}