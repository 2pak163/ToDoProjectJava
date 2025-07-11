
package com.sebas.todo.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

public class MainViewController {
    
    @FXML
    private Button btnNew;
    
    @FXML
    private ListView<String> taskList;
    
    @FXML
    public void initialize(){
        taskList.getItems().addAll("Comprar pan","Estudiar Java","Revisar Tareas");
    }
    
}
