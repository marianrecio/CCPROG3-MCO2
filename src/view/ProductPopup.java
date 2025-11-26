package view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Display;
import model.Product;
import model.Shopper;

import java.util.List;
import java.util.function.Consumer;

public class ProductPopup {

    private Stage stage;

    public ProductPopup(Display display, Shopper shopper, Runnable onUpdate) {
        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Pick a Product");

        VBox root = new VBox(10);
        root.setPadding(new Insets(15));

        Label title = new Label("Products Available:");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        List<Product> list = display.getProducts();

        if (list.isEmpty()) {
            root.getChildren().add(new Label("(No items available.)"));
        }

        VBox productBox = new VBox(10);

        for (Product p : list) {
            HBox row = new HBox(15);

            Label name = new Label(p.getName() + " — ₱" + p.getPrice());
            name.setStyle("-fx-font-size: 14px;");

            Button choose = new Button("Add");
            choose.setOnAction(e -> {
                if (shopper.addProduct(p)) {
                    display.getProducts().remove(p);
                    onUpdate.run();
                    stage.close();
                }
            });

            row.getChildren().addAll(name, choose);
            productBox.getChildren().add(row);
        }

        ScrollPane scroll = new ScrollPane(productBox);
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
