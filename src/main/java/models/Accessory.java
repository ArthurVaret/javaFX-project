package models;

public class Accessory extends Product{

    public Accessory(int id, String name, double prize, double cost, int stock){
        super(id, name, prize, cost, stock);
    }

    public int getSize(){return 0;}
    public String getType() { return "Accessory"; }
}