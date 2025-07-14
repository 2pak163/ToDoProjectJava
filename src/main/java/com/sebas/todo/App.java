
package com.sebas.todo;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;


public class App extends Application {
    
    @Override
    public void start(Stage stage)throws Exception{
        Parent root= FXMLLoader.load(
                getClass().getResource("/com/sebas/todo/ui/MainView.fxml")
        );
        Scene scene=new Scene(root);
        stage.getIcons().addAll(
            new Image(getClass().getResourceAsStream("/icons/icon-16.png")),
            new Image(getClass().getResourceAsStream("/icons/icon-32.png")),
            new Image(getClass().getResourceAsStream("/icons/icon-48.png")),
            new Image(getClass().getResourceAsStream("/icons/icon-64.png")),
            new Image(getClass().getResourceAsStream("/icons/icon-256.png"))
        );
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        stage.setTitle("To-Do-List");
        stage.setScene(scene);
        stage.show();  
    }
    
    
    public static void main(String[] args) {
        launch(args);
    }
}
