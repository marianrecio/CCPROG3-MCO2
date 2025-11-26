package view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Product;
import model.Shopper;

import java.util.List;

public class InventoryPopup {

    private Stage stage;

    public InventoryPopup(Shopper shopper) {

        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Inventory");

        VBox root = new VBox(10);
        root.setPadding(new Insets(15));

        Label title = new Label("Your Items:");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        VBox itemsBox = new VBox(10);

        List<Product> all = shopper.getAllProducts();

        if (all.isEmpty()) {
            itemsBox.getChildren().add(new Label("(No items)"));
        } else {
            for (Product p : all) {
                Label l = new Label(p.getName() + " — ₱" + p.getPrice());
                l.setStyle("-fx-font-size: 14px;");
                itemsBox.getChildren().add(l);
            }
        }

        ScrollPane scroll = new ScrollPane(itemsBox);
        scroll.setFitToWidth(true);

        Button close = new Button("Close");
        close.setOnAction(e -> stage.close());

        root.getChildren().addAll(title, scroll, close);

        Scene scene = new Scene(root, 300, 400);
        stage.setScene(scene);
    }

    public void show() {
        stage.showAndWait();
    }
}
