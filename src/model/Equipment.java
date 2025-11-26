public abstract class Equipment {
    protected int capacity;
    protected List<Product> items = new ArrayList<>();

    public Equipment(int c) {
        capacity = c; 
    }

    public boolean addItem(Product p) {
        if (items.size() >= capacity) {
            return false;
        }
        items.add(p);
        return true;
    }

    public List<Product> getItems() {
        return new ArrayList<>(items);
    }

    public void clear() {
        items.clear();
    }
}
