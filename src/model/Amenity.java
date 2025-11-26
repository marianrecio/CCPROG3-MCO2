package model;

interface Amenity {
    void interact(Shopper shopper, Supermarket market);
    char getSymbol();
    boolean isPassable();
}
