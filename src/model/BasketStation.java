package model;

public class BasketStation implements Amenity {
    public void interact(Shopper shopper, Supermarket market) {
        shopper.equipBasket();
    }

    public char getSymbol() { 
        return 'B'; 
    }
    public boolean isPassable() { 
        return true; 
    }
}
