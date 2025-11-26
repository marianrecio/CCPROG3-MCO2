import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

// ===== ENUMS =====
enum ProductType {
    FRUIT, ALCOHOL
}

enum Direction {
    NORTH, SOUTH, EAST, WEST;

    public static Direction direction(char c) {
        switch (c) {
            case 'I': return NORTH;
            case 'K': return SOUTH;
            case 'J': return WEST;
            case 'L': return EAST;
            default: return NORTH;
        }
    }
}

class Address {
    private String floor;    // "GF" or "2F"
    private String group;    // e.g., "Aisle 21" or "Wall 3"
    private int number;      // unique number within the group

    public Address(String floor, String group, int number) {
        this.floor = floor;
        this.group = group;
        this.number = number;
    }

    public String getFloor() {
        return floor;
    }

    public String getGroup() {
        return group;
    }

    public int getNumber() {
        return number;
    }

    @Override
    public String toString() {
        return floor + ", " + group + ", #" + number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address)) return false;
        Address other = (Address) o;
        return number == other.number &&
               floor.equals(other.floor) &&
               group.equals(other.group);
    }
}


// ===== POSITION =====
class Position {
    private int row, col;

    public Position(int r, int c) {
        row = r;
        col = c;
    }

    public int getRow() { return row; }
    public int getCol() { return col; }
    public void setRow(int r) { row = r; }
    public void setCol(int c) { col = c; }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Position)) return false;
        Position other = (Position) o;
        return row == other.row && col == other.col;
    }
}

// ===== PRODUCT =====
abstract class Product {
    private String serialNumber;
    private String name;
    private double price;
    private ProductType type;

    private static final Random RANDOM = new Random();

    public Product(String name, double price, ProductType type) {
        this.name = name;
        this.price = price;
        this.type = type;
        this.serialNumber = generateSerialNumber();
    }

    public String getSerialNumber() { return serialNumber; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public ProductType getType() { return type; }

    protected String generateSerialNumber() {
        String serial = "";
        List<Integer> takenNumbers = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            int digit;
            do {
                digit = RANDOM.nextInt(10);
            } while (takenNumbers.contains(digit));
            serial += digit;
            takenNumbers.add(digit);
        }
        return serial;
    }
}


class Fruit extends Product {
    public Fruit(String s, String n, double p) {
        super(n, p, ProductType.FRUIT);
    }

    @Override
    public String generateSerialNumber() {
        String serialNumber = "";

        serialNumber += "FRU";
        serialNumber += super.generateSerialNumber();

        return serialNumber;
    }
}

class Alcohol extends Product {
    public Alcohol(String s, String n, double p) {
        super(n, p, ProductType.ALCOHOL);
    }
}

// ===== EQUIPMENT =====
abstract class Equipment {
    protected int capacity;
    protected List<Product> items = new ArrayList<>();

    public Equipment(int c) { capacity = c; }

    public boolean addItem(Product p) {
        if (items.size() >= capacity) return false;
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

class Basket extends Equipment {
    public Basket() { super(15); }
}

class Cart extends Equipment {
    public Cart() { super(30); }
}

// ===== AMENITY =====
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



// ===== DISPLAY =====
abstract class Display implements Amenity {
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

class Table extends Display {
    public Table(Address address, ProductType type) {
        super(address, type, 4); // example capacity of 4 products per table
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
        return true; // shopper can stand on table tile to interact
    }
}


// ===== SERVICES =====
abstract class Service implements Amenity {
    protected String name;

    public Service(String name) {
        this.name = name;
    }

    public boolean isPassable() { return true; }
}

// Checkout Service
class Checkout extends Service {

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

            if (shopper.getAge() >= 60 && p.getType() == ProductType.FRUIT) {
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

    public char getSymbol() { return 'C'; }
}

// Product Search
class ProductSearch extends Service {
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

    public char getSymbol() { return 'P'; }
}

// ===== SHOPPER =====
class Shopper {
    private String name;
    private int age;
    private Position position;
    private Direction facing = Direction.NORTH;
    private Equipment equipment;
    private List<Product> handItems = new ArrayList<>();

    public Shopper(String name, int age, Position start) {
        this.name = name;
        this.age = age;
        this.position = start;
    }

    public String getName() { return name; }
    public int getAge() { return age; }
    public Position getPosition() { return position; }
    public Direction getFacing() { return facing; }

    public void setFacing(Direction d) { facing = d; }

    public boolean addProduct(Product p) {
        if (age < 18 && p.getType() == ProductType.ALCOHOL) {
            System.out.println("You are underage.");
            return false;
        }

        if (equipment != null)
            return equipment.addItem(p);

        if (handItems.size() < 2) {
            handItems.add(p);
            return true;
        }

        System.out.println("Hands are full.");
        return false;
    }

    public List<Product> getAllProducts() {
        List<Product> all = new ArrayList<>();
        if (equipment != null) all.addAll(equipment.getItems());
        all.addAll(handItems);
        return all;
    }

    public void clearAllProducts() {
        if (equipment != null) equipment.clear();
        handItems.clear();
    }

    public void viewInventory() {
        List<Product> all = getAllProducts();

        if (all.isEmpty()) {
            System.out.println("Inventory empty.");
            return;
        }

        System.out.println("Inventory:");
        for (Product p : all) {
            System.out.println("- " + p.getName());
        }  
    }

        public boolean equipBasket() {
        if (equipment == null) {
            equipment = new Basket();
            System.out.println("You picked up a basket.");
            return true;
        } else {
            System.out.println("You already have equipment!");
            return false;
        }
    }

    public boolean equipCart() {
        if (equipment == null) {
            equipment = new Cart();
            System.out.println("You picked up a cart.");
            return true;
        } else {
            System.out.println("You already have equipment!");
            return false;
        }
    }

    public void move(char dirChar, Supermarket market) {
        int newRow = position.getRow();
        int newCol = position.getCol();

        switch (dirChar) {
            case 'W': newRow--; break;
            case 'S': newRow++; break;
            case 'A': newCol--; break;
            case 'D': newCol++; break;
        }

        if (market.isPassable(newRow, newCol)) {
            position.setRow(newRow);
            position.setCol(newCol);
        } else {
            System.out.println("Blocked!");
        }
    }
}

// ===== SUPERMARKET =====
class Supermarket {
    private final int ROWS = 22, COLS = 22;
    private Amenity[][] groundFloor = new Amenity[ROWS][COLS];
    private Amenity[][] secondFloor = new Amenity[ROWS][COLS];
    private List<Display> allDisplays = new ArrayList<>();
    private boolean onGroundFloor = true;
    private boolean running = true;

    // Initialize both floors
    public void initializeDisplays() {
        initializeGroundFloor();
        initializeSecondFloor();
    }

    // ======================= GROUND FLOOR =======================
    private void initializeGroundFloor() {
        // Fill empty spaces
        for (int r = 0; r < ROWS; r++)
            for (int c = 0; c < COLS; c++)
                groundFloor[r][c] = null;

        // Walls
        for (int i = 0; i < COLS; i++) {
            groundFloor[0][i] = new Wall();
            groundFloor[ROWS-1][i] = new Wall();
            groundFloor[i][0] = new Wall();
            groundFloor[i][COLS-1] = new Wall();
        }

        // Refrigerators
        for (int i = 2; i <= 6; i++) groundFloor[1][i] = new RefrigeratorAmenity();
        for (int i = 9; i <= 13; i++) groundFloor[1][i] = new RefrigeratorAmenity();
        for (int i = 15; i <= 21; i++) groundFloor[1][i] = new RefrigeratorAmenity();

        // Shelves
        int[][] shelfRows = {{4,7},{10,13}};
        int[][] shelfCols = {{2,3},{6,7},{14,15},{18,19}};
        for (int[] rowRange : shelfRows) {
            for (int r = rowRange[0]; r <= rowRange[1]; r++) {
                for (int[] colRange : shelfCols) {
                    for (int c = colRange[0]; c <= colRange[1]; c++)
                        groundFloor[r][c] = new ShelfAmenity();
                }
            }
        }

        // Tables
        int[][] tableRows = {{4,7},{10,13}};
        int[] tableCols = {10,11};
        for (int[] rowRange : tableRows) {
            for (int r = rowRange[0]; r <= rowRange[1]; r++) {
                for (int c : tableCols) {
                    // ex: Address tableAddress = new Address("GF", "Aisle 4", 1);
                    Table table = new Table(null, ProductType.FRUIT);
                    table.addProduct(new Fruit("F" + r + c + "1", "Apple", 10));
                    table.addProduct(new Fruit("F" + r + c + "2", "Banana", 8));
                    groundFloor[r][c] = table;
                    allDisplays.add(table);
                }
            }
        }

        // Stairs
        groundFloor[15][1] = new StairAmenity();
        groundFloor[15][20] = new StairAmenity();

        // Product Search
        groundFloor[15][8] = new ProductSearchStation();
        groundFloor[15][13] = new ProductSearchStation();

        // Checkout Counters
        int[] checkoutCols1 = {2,4,6,8};
        int[] checkoutCols2 = {13,15,17,19};
        for (int c : checkoutCols1) groundFloor[18][c] = new CheckoutCounter();
        for (int c : checkoutCols2) groundFloor[18][c] = new CheckoutCounter();

        // Basket and Cart stations
        groundFloor[20][1] = new CartStation();
        groundFloor[20][20] = new BasketStation();
    }

    // ======================= SECOND FLOOR =======================
private void initializeSecondFloor() {
    // Fill empty spaces
    for (int r = 0; r < ROWS; r++)
        for (int c = 0; c < COLS; c++)
            secondFloor[r][c] = null;

    // ===== WALLS (+) =====
    for (int i = 0; i < COLS; i++) {
        secondFloor[0][i] = new Wall();
        secondFloor[ROWS-1][i] = new Wall();
        secondFloor[i][0] = new Wall();
        secondFloor[i][COLS-1] = new Wall();
    }

    // ===== ROW 1: Basket, Refrigerators, Cart =====
    secondFloor[1][1] = new BasketStation(); // B
    for (int i = 3; i <= 6; i++) secondFloor[1][i] = new RefrigeratorAmenity(); // F
    for (int i = 9; i <= 12; i++) secondFloor[1][i] = new RefrigeratorAmenity();
    for (int i = 15; i <= 18; i++) secondFloor[1][i] = new RefrigeratorAmenity();
    secondFloor[1][20] = new CartStation(); // R

    // ===== SHELVES (S) =====
    int[][] shelfRows = {{4,7},{10,13}};
    int[][] shelfCols = {{2,3},{6,7},{14,15},{18,19}};
    for (int[] rowRange : shelfRows) {
        for (int r = rowRange[0]; r <= rowRange[1]; r++) {
            for (int[] colRange : shelfCols) {
                for (int c = colRange[0]; c <= colRange[1]; c++)
                    secondFloor[r][c] = new ShelfAmenity();
            }
        }
    }

    // ===== TABLES (T) =====
    int[][] tableRows = {{4,7},{10,13}};
    int[] tableCols = {10,11};
    for (int[] rowRange : tableRows) {
        for (int r = rowRange[0]; r <= rowRange[1]; r++) {
            for (int c : tableCols) {
                Table table = new Table(null, ProductType.FRUIT);
                table.addProduct(new Fruit("F" + r + c + "1", "Apple", 10));
                table.addProduct(new Fruit("F" + r + c + "2", "Banana", 8));
                secondFloor[r][c] = table;
                allDisplays.add(table);
            }
        }
    }

    // ===== STAIRS (U) =====
    secondFloor[15][1] = new StairAmenity();
    secondFloor[15][20] = new StairAmenity();

    // ===== MIDDLE WALLS =====
    for (int r = 16; r <= 17; r++) {
        secondFloor[r][4] = new Wall();
        secondFloor[r][5] = new Wall();
        secondFloor[r][10] = new Wall();
        secondFloor[r][11] = new Wall();
        secondFloor[r][16] = new Wall();
        secondFloor[r][17] = new Wall();
    }

    // ===== PRODUCT SEARCH (P) =====
    secondFloor[20][1] = new ProductSearchStation();
    secondFloor[20][20] = new ProductSearchStation();

    // ===== BOTTOM ROW TABLES (row 21) =====
    int[][] bottomTableCols = {{3,7},{9,11},{14,18}};
    for (int[] colRange : bottomTableCols) {
        for (int c = colRange[0]; c <= colRange[1]; c++) {
            Table table = new Table(null, ProductType.FRUIT);
            table.addProduct(new Fruit("F21" + c + "1", "Apple", 10));
            table.addProduct(new Fruit("F21" + c + "2", "Banana", 8));
            secondFloor[20][c] = table;
            allDisplays.add(table);
        }
    }
}


    // ======================= FLOOR SWITCH =======================
    public void switchFloor() {
        onGroundFloor = !onGroundFloor;
        System.out.println(onGroundFloor ? "Switched to Ground Floor" : "Switched to Second Floor");
    }

    // ======================= MOVEMENT / MAP =======================
    public boolean isPassable(int row, int col) {
        if (row < 0 || row >= ROWS || col < 0 || col >= COLS) return false;
        Amenity[][] current = onGroundFloor ? groundFloor : secondFloor;
        return current[row][col] == null || current[row][col].isPassable();
    }

    public Amenity getAmenityInFront(Shopper shopper) {
        Position p = shopper.getPosition();
        int r = p.getRow(), c = p.getCol();
        switch (shopper.getFacing()) {
            case NORTH: r--; break;
            case SOUTH: r++; break;
            case WEST: c--; break;
            case EAST: c++; break;
        }
        Amenity[][] current = onGroundFloor ? groundFloor : secondFloor;
        if (r >= 0 && r < ROWS && c >= 0 && c < COLS) return current[r][c];
        return null;
    }

    public void displayMap(Shopper shopper) {
        Amenity[][] current = onGroundFloor ? groundFloor : secondFloor;
        System.out.println("\n=== Supermarket Layout ===");
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                if (shopper.getPosition().getRow() == r && shopper.getPosition().getCol() == c) {
                    System.out.print("@ ");
                    continue;
                }
                if (current[r][c] == null) System.out.print(". ");
                else System.out.print(current[r][c].getSymbol() + " ");
            }
            System.out.println();
        }
    }

    // ======================= SIMULATION CONTROL =======================
    public boolean isRunning() { return running; }
    public void stopSimulation() { running = false; }

    // ======================= INTERACTIONS =======================
    public void checkout(Shopper shopper) {
        Amenity a = getAmenityInFront(shopper);
        if (a instanceof CheckoutCounter) System.out.println("Proceeding to checkout...");
        else System.out.println("Move in front of a checkout counter first.");
    }

public void searchProduct(Shopper shopper) {
    ProductSearch searchService = new ProductSearch(allDisplays);
    searchService.interact(shopper, this);
}

    public List<Display> getAllDisplays() { return allDisplays; }
}




// ===== AMENITIES =====
class ShelfAmenity implements Amenity {
    public void interact(Shopper s, Supermarket m) {
        System.out.println("Shelf.");
    }
    public char getSymbol() { return 'S'; }
    public boolean isPassable() { return false; }
}

class RefrigeratorAmenity implements Amenity {
    public void interact(Shopper s, Supermarket m) {
        System.out.println("Refrigerator.");
    }
    public char getSymbol() { return 'F'; }
    public boolean isPassable() { return false; }
}

class StairAmenity implements Amenity {
    public void interact(Shopper s, Supermarket m) {
        System.out.println("Using stairs...");
        m.switchFloor();  // actually switch floors
    }
    
    public char getSymbol() { return 'U'; }
    
    public boolean isPassable() { return true; }
}


class CheckoutCounter implements Amenity {
    private Checkout checkout = new Checkout();

    public void interact(Shopper s, Supermarket m) {
        checkout.interact(s, m);
    }
    public char getSymbol() { return 'C'; }
    public boolean isPassable() { return false; }
}

class ProductSearchStation implements Amenity {
    public void interact(Shopper s, Supermarket m) {
        m.searchProduct(s);
    }
    public char getSymbol() { return 'P'; }
    public boolean isPassable() { return false; }
}

class EntranceAmenity implements Amenity {
    public void interact(Shopper s, Supermarket m) {
        System.out.println("At entrance.");
    }
    public char getSymbol() { return '^'; }
    public boolean isPassable() { return true; }
}

class ExitAmenity implements Amenity {
    public void interact(Shopper s, Supermarket m) {
        System.out.println("Exiting...");
        m.stopSimulation();
    }
    public char getSymbol() { return 'v'; }
    public boolean isPassable() { return true; }
}

class Wall implements Amenity {
    public void interact(Shopper s, Supermarket m) {}
    public char getSymbol() { return '+'; }
    public boolean isPassable() { return false; }
}

// ===== MAIN =====
public class SupermarketSimulator {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Name: ");
        String name = sc.nextLine();

        System.out.print("Age: ");
        int age = Integer.parseInt(sc.nextLine());

        Shopper shopper = new Shopper(name, age, new Position(21, 10));

        Supermarket market = new Supermarket();
        market.initializeDisplays();

        while (market.isRunning()) {
            market.displayMap(shopper);

            System.out.print("Command (WASD move, IJKL turn, SPACE interact, V inventory, Q quit): ");
            String cmd = sc.nextLine().toUpperCase();

            if (cmd.equals("Q")) {
                market.stopSimulation();
            }
            else if (cmd.equals("V")) {
                shopper.viewInventory();
            }
            else if (cmd.equals(" ")) {
                Amenity a = market.getAmenityInFront(shopper);
                if (a != null) a.interact(shopper, market);
                else System.out.println("Nothing there.");
            }
            else if ("IJKL".contains(cmd)) {
                shopper.setFacing(Direction.direction(cmd.charAt(0)));
            }
            else if ("WASD".contains(cmd)) {
                shopper.move(cmd.charAt(0), market);
            }
        }

        System.out.println("Thanks for playing!");
    }
}
