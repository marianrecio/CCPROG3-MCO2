public class Stair implements Amenity {
    public void interact(Shopper s, Supermarket m) {
        System.out.println("Using stairs...");
        m.switchFloor();  
    }
    
    public char getSymbol() { 
      return 'U'; 
    }
    
    public boolean isPassable() { 
      return true; 
    }
}
