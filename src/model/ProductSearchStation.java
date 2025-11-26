public class ProductSearchStation implements Amenity {
    public void interact(Shopper s, Supermarket m) {
        m.searchProduct(s);
    }
    public char getSymbol() { 
        return 'P'; 
    }
    public boolean isPassable() { 
        return false; 
    }
}
