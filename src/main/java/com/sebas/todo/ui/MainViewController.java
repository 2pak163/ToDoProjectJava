
package com.sebas.todo.ui;

import com.sebas.todo.model.Task;
import com.sebas.todo.service.JsonTaskRepository;
import com.sebas.todo.service.TaskRepository;
import com.sebas.todo.service.TaskService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.HBox;
import javafx.geometry.Pos;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.util.List;

public class MainViewController {
    
   
    
    @FXML private Button btnNew;
    
    @FXML private Button btnDeleteAll;
    
    @FXML private TextField txtTask;
    
    @FXML private ListView<Task> taskList;
    
    private final TaskService service;
    
    private final ObservableList<Task> tasks= FXCollections.observableArrayList();
    

    public MainViewController() {
        TaskRepository repo=new JsonTaskRepository();
        this.service= new TaskService(repo);
    }
    
    @FXML
    public void initialize(){
        List<Task> loaded= service.getAll();
        tasks.setAll(loaded);
        taskList.setItems(tasks);
        
        taskList.setCellFactory(lv->new ListCell<>(){
            
            private final Label lbl= new Label();
            private final Button btnDeleteCell = new Button("🗑");
            private final HBox hbox = new HBox(10, lbl, btnDeleteCell);
            
                {
                    
                    btnDeleteCell.setStyle("-fx-font-size:10px; -fx-padding: 2;");
                    hbox.setAlignment(Pos.CENTER_LEFT);
                    
                    btnDeleteCell.setOnAction(evt->{
                        Task item=getItem();
                        String msg= String.format("¿Eliminar la tarea \"%s\"?",item.getDescription());
                        if(item != null && confirm(msg)){
                            service.remove(item);
                            tasks.remove(item);
                        }
                    });
                }
            
            @Override
            protected void updateItem(Task item, boolean empty){
                super.updateItem(item, empty);
                if(empty || item == null){
                    setText(null);
                    setGraphic(null);
                }else{
                    lbl.setText(item.getDescription());
                    setGraphic(hbox);
                }
            };
        });
    }
    
    @FXML
    public void handleAddTask(){
        String desc= txtTask.getText().trim();
        if(!desc.isEmpty()){
            Task task= new Task(desc);
            service.add(task);
            tasks.add(task);
            txtTask.clear();
        }
    
    }
    
    @FXML
    public void handleDeleteAllTasks(){  
        if(confirm("¿Eliminar todas las tareas?")){
            service.removeAll();
            tasks.clear();
        }
    }
    
    public boolean confirm(String msg){
        Alert a= new Alert(Alert.AlertType.CONFIRMATION,msg,ButtonType.OK,ButtonType.CANCEL);
        a.setHeaderText(null);
        return a.showAndWait().filter(b->b==ButtonType.OK).isPresent();
    };
    
}
