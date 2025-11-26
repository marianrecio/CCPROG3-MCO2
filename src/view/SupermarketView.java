package view;

import javafx.geometry.Insets;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import model.*;

public class SupermarketView {

    private BorderPane root;
    private Canvas canvas;

    private int tileSize = 32; // tweak if you want larger tiles

    private Button btnInventory;
    private Button btnSwitchFloor;
    private Button btnQuit;

    public SupermarketView() {
        root = new BorderPane();
        canvas = new Canvas(22 * tileSize, 22 * tileSize);

        // BOTTOM BUTTONS
        btnInventory = new Button("Inventory");
        btnSwitchFloor = new Button("Switch Floor");
        btnQuit = new Button("Quit");

        HBox bottomBar = new HBox(10,
                btnInventory, btnSwitchFloor, btnQuit);
        bottomBar.setPadding(new Insets(10));

        root.setCenter(canvas);
        root.setBottom(bottomBar);
    }

    public BorderPane getRoot() {
        return root;
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public int getTileSize() {
        return tileSize;
    }

    public Button getBtnInventory() {
        return btnInventory;
    }

    public Button getBtnSwitchFloor() {
        return btnSwitchFloor;
    }

    public Button getBtnQuit() {
        return btnQuit;
    }

    // Draws the entire map + shopper
    public void drawMap(Supermarket market, Shopper shopper) {
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Clear
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        Amenity[][] floor = market.getCurrentFloor();

        for (int r = 0; r < 22; r++) {
            for (int c = 0; c < 22; c++) {
                double x = c * tileSize;
                double y = r * tileSize;

                gc.setStroke(Color.GRAY);
                gc.strokeRect(x, y, tileSize, tileSize);

                Amenity a = floor[r][c];
                if (a != null) {
                    switch (a.getSymbol()) {
                        case 'F' -> gc.setFill(Color.LIGHTBLUE);   // Fridge
                        case 'S' -> gc.setFill(Color.BURLYWOOD);   // Shelf
                        case 'T' -> gc.setFill(Color.LIGHTGREEN);  // Table
                        case 'C' -> gc.setFill(Color.ORANGE);      // Checkout
                        case 'R' -> gc.setFill(Color.DARKCYAN);    // Cart station
                        case 'B' -> gc.setFill(Color.PINK);        // Basket station
                        case 'U' -> gc.setFill(Color.YELLOW);      // Stairs
                        case 'P' -> gc.setFill(Color.PURPLE);      // Product search
                        case 'W' -> gc.setFill(Color.BLACK);       // Wall (if implemented)
                        default -> gc.setFill(Color.LIGHTGRAY);
                    }
                    gc.fillRect(x, y, tileSize, tileSize);
                }
            }
        }

        // Draw shopper
        gc.setFill(Color.RED);
        gc.fillOval(
                shopper.getPosition().getCol() * tileSize + 4,
                shopper.getPosition().getRow() * tileSize + 4,
                tileSize - 8,
                tileSize - 8
        );
    }
}
