package controller;

import javafx.application.Platform;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import model.*;
import view.InventoryPopup;
import view.ProductPopup;
import view.SupermarketView;

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

        view.getRoot().setOnKeyPressed(this::handleKey);

        view.getCanvas().setOnMouseClicked(this::handleMouse);

        view.getBtnInventory().setOnAction(e ->
                new InventoryPopup(shopper).show()
        );

        view.getBtnSwitchFloor().setOnAction(e -> {
            market.switchFloor();
            redraw();
        });

        view.getBtnQuit().setOnAction(e ->
                Platform.exit()
        );
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
                if (a != null)
                    a.interact(shopper, market);
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

        if (clicked instanceof Display display) {
            new ProductPopup(display, shopper, this::redraw).show();
        }
    }

    public void redraw() {
        view.drawMap(market, shopper);
    }
}
