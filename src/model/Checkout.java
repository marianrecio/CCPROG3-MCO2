package model;

public class Checkout extends Service {

    public Checkout() {
        super("Checkout");
    }

    public void interact(Shopper shopper, Supermarket market) {
        List<Product> items = shopper.getAllProducts();
        if (items.isEmpty()) {
            System.out.println("No products to checkout!");
            return;
        }

        double total = 0;

        for (Product p : items) {
            double price = p.getPrice();

            if (shopper.getAge() >= 60 && p.getType() != ProductType.ALCOHOL) {
                price *= 0.8;
            }

            total += price;
        }

        System.out.println("Checkout total: " + total);
        generateReceipt(shopper, items, total);
        shopper.clearAllProducts();
    }

    private void generateReceipt(Shopper shopper, List<Product> items, double total) {
        try (FileWriter fw = new FileWriter("receipt.txt")) {
            fw.write("Receipt for " + shopper.getName() + "\n");
            fw.write("------------------------\n");
            for (Product p : items) {
                fw.write(p.getName() + " - " + p.getSerialNumber() + " - " + p.getPrice() + "\n");
            }
            fw.write("TOTAL: " + total);
            System.out.println("Receipt generated!");
        } catch (IOException e) {
            System.out.println("Error generating receipt.");
        }
    }

    public char getSymbol() { 
        return 'C'; 
    }
}
