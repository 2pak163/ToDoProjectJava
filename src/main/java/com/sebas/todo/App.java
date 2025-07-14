
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
        Image icon = new Image(
            getClass().getResourceAsStream("/icons/app-icon.png")
        );
        stage.getIcons().add(icon);
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        stage.setTitle("To-Do-List");
        stage.setScene(scene);
        stage.show();  
    }
    
    
    public static void main(String[] args) {
        launch(args);
    }
}
