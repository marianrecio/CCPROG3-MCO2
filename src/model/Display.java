public abstract class Display implements Amenity {
    protected ProductType allowedType;
    protected int capacity;
    protected List<Product> products = new ArrayList<>();
    private Address address;

    public Display(Address address, ProductType type, int capacity) {
        this.address = address;
        this.allowedType = type;
        this.capacity = capacity;
    }

    public boolean canAccept(Product p) {
        return p.getType() == allowedType && products.size() < capacity;
    }

    public boolean addProduct(Product p) {
        if (!canAccept(p)) return false;
        products.add(p);
        return true;
    }

    public List<Product> getProducts() {
        return new ArrayList<>(products);
    }

    public Address getAddress() {
        return address;
    }

    public boolean isPassable() {
        return true;
    }
}
