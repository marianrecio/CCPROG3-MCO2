package model;

public interface Amenity {
    void interact(Shopper shopper, Supermarket market);
    char getSymbol();
    boolean isPassable();
}
