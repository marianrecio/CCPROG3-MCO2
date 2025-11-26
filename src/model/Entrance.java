package model;

public class Entrance implements Amenity {
    public void interact(Shopper s, Supermarket m) {
        System.out.println("At entrance.");
    }
    public char getSymbol() { 
        return '^'; 
    }
    public boolean isPassable() { 
        return true; 
    }
}
