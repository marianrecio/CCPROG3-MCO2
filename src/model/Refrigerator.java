package model;

public class Refrigerator implements Amenity {
    public void interact(Shopper s, Supermarket m) {
        System.out.println("Refrigerator.");
    }
    public char getSymbol() { 
        return 'F'; 
    }
    public boolean isPassable() { 
        return false; 
    }
}
