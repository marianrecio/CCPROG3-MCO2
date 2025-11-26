package model;

public class CartStation implements Amenity {
    public void interact(Shopper shopper, Supermarket market) {
        shopper.equipCart();
    }

    public char getSymbol() { 
        return 'R'; 
    }
    public boolean isPassable() { 
        return true; 
    }
}
