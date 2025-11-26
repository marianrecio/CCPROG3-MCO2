public class ProductSearch extends Service {
    private List<Display> displays;

    public ProductSearch(List<Display> displays) {
        super("Search");
        this.displays = displays;
    }

    public void interact(Shopper shopper, Supermarket market) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter product name to search: ");
        String query = sc.nextLine();

        boolean found = false;

        for (Display d : displays) {
            for (Product p : d.getProducts()) {
                if (p.getName().equalsIgnoreCase(query)) {
                    System.out.println("Found at: " + d.getAddress());
                    found = true;
                }
            }
        }

        if (!found) System.out.println("Product not found.");
    }

    public char getSymbol() { 
        return 'P'; 
    }
}
