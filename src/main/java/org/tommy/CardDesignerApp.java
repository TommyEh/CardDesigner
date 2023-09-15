package org.tommy;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class CardDesignerApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/UI.fxml"));

        Scene scene = new Scene(root, 1099, 725);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

        primaryStage.setTitle("Card Designer");
        primaryStage.setScene(scene);
        primaryStage.show();

        Font atkinsonFont = Font.loadFont(getClass().getResourceAsStream("/AtkinsonHyperlegible-Regular.ttf"), 22.5);
        if(atkinsonFont == null) {
            System.out.println("Failed to load Atkinson Hyperlegible Font.");
        } else {
            System.out.println("Font loaded successfully: " + atkinsonFont.getName());
        }

    }

    public static void main(String[] args) {
        launch(args);
    }
}
