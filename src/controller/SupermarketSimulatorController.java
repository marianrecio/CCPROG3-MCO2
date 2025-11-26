package controller;

import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import model.*;
import view.SupermarketView;
import view.ProductPopup;

public class SupermarketSimulatorController {

    private Supermarket market;
    private Shopper shopper;
    private SupermarketView view;

    public SupermarketSimulatorController(SupermarketView view) {
        this.view = view;

        // Initialize model
        this.market = new Supermarket();
        this.market.initializeDisplays();

        this.shopper = new Shopper("Player", 18, new Position(21, 10));

        attachEventHandlers();
        redraw();
    }

    private void attachEventHandlers() {
        // Keyboard events → movement & turning
        view.getRoot().setOnKeyPressed(this::handleKey);

        // Mouse click → interact with amenity
        view.getCanvas().setOnMouseClicked(this::handleMouse);
    }

    private void handleKey(KeyEvent e) {
        switch (e.getCode()) {
            case W -> shopper.move('W', market);
            case S -> shopper.move('S', market);
            case A -> shopper.move('A', market);
            case D -> shopper.move('D', market);

            case I -> shopper.setFacing(Direction.NORTH);
            case K -> shopper.setFacing(Direction.SOUTH);
            case J -> shopper.setFacing(Direction.WEST);
            case L -> shopper.setFacing(Direction.EAST);

            case SPACE -> {
                Amenity a = market.getAmenityInFront(shopper);
                if (a != null) a.interact(shopper, market);
            }
        }
        redraw();
    }

    private void handleMouse(MouseEvent e) {
        int tileSize = view.getTileSize();

        int col = (int)(e.getX() / tileSize);
        int row = (int)(e.getY() / tileSize);

        Amenity[][] floor = market.getCurrentFloor();
        Amenity clicked = floor[row][col];

        if (clicked instanceof Table table) {
            // Show product popup
            new ProductPopup(table, shopper, this::redraw).show();
        }
    }

    public void redraw() {
        view.drawMap(market, shopper);
    }
}
