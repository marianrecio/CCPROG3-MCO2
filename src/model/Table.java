public class Table extends Display {
    public Table(Address address, ProductType type) {
        super(address, type, 4); // ex capacity of 4 products per table
    }

    @Override
    public void interact(Shopper shopper, Supermarket market) {
        System.out.println("Interacting with Table: " + getAddress());

        if (products.isEmpty()) {
            System.out.println("  (empty)");
            return;
        }

        // List available products
        for (int i = 0; i < products.size(); i++) {
            Product p = products.get(i);
            System.out.println(" [" + i + "] " + p.getName() + " - " + p.getPrice());
        }

        // Pick first product automatically
        Product p = products.get(0);
        if (shopper.addProduct(p)) {
            System.out.println("Picked up " + p.getName());
            products.remove(p);
        } else {
            System.out.println("Could not pick up " + p.getName());
        }
    }

    @Override
    public char getSymbol() {
        return 'T';
    }

    @Override
    public boolean isPassable() {
        return true; 
    }
}
