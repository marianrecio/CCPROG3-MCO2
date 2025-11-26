public class Wall implements Amenity {
    public void interact(Shopper s, Supermarket m) {}
    public char getSymbol() { 
        return '+'; 
    }
    public boolean isPassable() { 
        return false; 
    }
}
