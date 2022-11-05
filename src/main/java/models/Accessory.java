package models;

public class Accessory extends Product{

    public Accessory(int id, String name, double prize, int stock){
        super(id, name, prize, stock);
    }

    public String getType() { return "accessory"; }
}