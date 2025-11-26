public class Supermarket {
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

    // GROUND FLOOR 
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
    groundFloor[15][1] = new Stair();
    groundFloor[15][20] = new Stair();

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

// SECOND FLOOR
  private void initializeSecondFloor() {
  
    // Fill empty spaces
    for (int r = 0; r < ROWS; r++)
      for (int c = 0; c < COLS; c++)
          secondFloor[r][c] = null;

   // WALLS 
   for (int i = 0; i < COLS; i++) {
      secondFloor[0][i] = new Wall();
      secondFloor[ROWS-1][i] = new Wall();
      secondFloor[i][0] = new Wall();
      secondFloor[i][COLS-1] = new Wall();
  }

  // Basket, Refrigerators, Cart
  secondFloor[1][1] = new BasketStation(); 
  for (int i = 3; i <= 6; i++) {
      secondFloor[1][i] = new RefrigeratorAmenity();
  }
  for (int i = 9; i <= 12; i++) {
      secondFloor[1][i] = new RefrigeratorAmenity();
  }
  for (int i = 15; i <= 18; i++) {
    secondFloor[1][i] = new RefrigeratorAmenity();
  }
  secondFloor[1][20] = new CartStation(); 

  // Shelves
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

  // Tables
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

  // Stairs
  secondFloor[15][1] = new Stair();
  secondFloor[15][20] = new Stair();

  // Middle walls 
  for (int r = 16; r <= 17; r++) {
      secondFloor[r][4] = new Wall();
      secondFloor[r][5] = new Wall();
      secondFloor[r][10] = new Wall();
      secondFloor[r][11] = new Wall();
      secondFloor[r][16] = new Wall();
      secondFloor[r][17] = new Wall();
  }

  // Product Search
  secondFloor[20][1] = new ProductSearchStation();
  secondFloor[20][20] = new ProductSearchStation();

  // Bottom row tables 
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


// Floor switch
public void switchFloor() {
    onGroundFloor = !onGroundFloor;
    if (onGroundFloor) {
        System.out.println("Switched to Ground Floor");
    } else {
        System.out.println("Switched to Second Floor");
    }
}

public boolean isPassable(int row, int col) {
    if (row < 0 || row >= ROWS || col < 0 || col >= COLS) {
        return false;
    }

    Amenity[][] current;
    if (onGroundFloor) {
        current = groundFloor;
    } else {
        current = secondFloor;
    }

    if (current[row][col] == null) {
        return true;
    } else {
        return current[row][col].isPassable();
    }
}


  public Amenity getAmenityInFront(Shopper shopper) {
      Position p = shopper.getPosition();
      int r = p.getRow(), c = p.getCol();
      switch (shopper.getFacing()) {
          case NORTH: 
              r--; 
              break;
          case SOUTH: 
              r++; 
              break;
          case WEST: 
              c--; 
              break;
          case EAST: 
              c++; 
              break;
        default:
              return null;
      }
      Amenity[][] current = onGroundFloor ? groundFloor : secondFloor;
      if (r >= 0 && r < ROWS && c >= 0 && c < COLS) {
          return current[r][c];
      }
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
              if (current[r][c] == null) {
                  System.out.print(". ");
              }
              else {
                  System.out.print(current[r][c].getSymbol() + " ");
              }
          }
          System.out.println();
      }
  }

  // Simulation
  public boolean isRunning() { 
      return running; 
  }
  public void stopSimulation() { 
      running = false; 
  }

  // Interactions
  public void checkout(Shopper shopper) {
      Amenity a = getAmenityInFront(shopper);
      if (a instanceof CheckoutCounter) {
          ystem.out.println("Proceeding to checkout...");
      }
      else {
         System.out.println("Move in front of a checkout counter first.");
      }
  }

public void searchProduct(Shopper shopper) {
    ProductSearch searchService = new ProductSearch(allDisplays);
    searchService.interact(shopper, this);
}

    public List<Display> getAllDisplays() { return allDisplays; }
}

public Amenity[][] getCurrentFloor() {
    return onGroundFloor ? groundFloor : secondFloor;
}

public boolean isOnGroundFloor() {
    return onGroundFloor;
}
