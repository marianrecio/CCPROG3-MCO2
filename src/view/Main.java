public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        SupermarketView view = new SupermarketView();
        Scene scene = new Scene(view.getRoot());
        stage.setScene(scene);
        stage.setTitle("Supermarket Simulator");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
