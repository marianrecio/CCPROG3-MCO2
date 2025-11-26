public class Exit implements Amenity {
    public void interact(Shopper s, Supermarket m) {
        System.out.println("Exiting...");
        m.stopSimulation();
    }
    public char getSymbol() { 
        return 'v'; 
    }
    public boolean isPassable() { 
        return true; 
    }
}
