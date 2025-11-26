public class Shelf implements Amenity {
    public void interact(Shopper s, Supermarket m) {
        System.out.println("Shelf.");
    }
    public char getSymbol() { 
        return 'S'; 
    }
    public boolean isPassable() { 
        return false; 
    }
}
