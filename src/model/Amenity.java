interface Amenity {
    void interact(Shopper shopper, Supermarket market);
    char getSymbol();
    boolean isPassable();
}

class BasketStation implements Amenity {
    public void interact(Shopper shopper, Supermarket market) {
        shopper.equipBasket();
    }

    public char getSymbol() { return 'B'; }
    public boolean isPassable() { return true; }
}

class CartStation implements Amenity {
    public void interact(Shopper shopper, Supermarket market) {
        shopper.equipCart();
    }

    public char getSymbol() { return 'R'; }
    public boolean isPassable() { return true; }
}
