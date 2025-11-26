package model;

public class CheckoutCounter implements Amenity {
    private Checkout checkout = new Checkout();

    public void interact(Shopper s, Supermarket m) {
        checkout.interact(s, m);
    }
    public char getSymbol() { 
       return 'C'; 
    }
    public boolean isPassable() { 
      return false; 
    }
}
