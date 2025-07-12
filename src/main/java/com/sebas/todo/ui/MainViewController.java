
package com.sebas.todo.ui;

import com.sebas.todo.model.Task;
import com.sebas.todo.service.JsonTaskRepository;
import com.sebas.todo.service.TaskRepository;
import com.sebas.todo.service.TaskService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import java.util.List;

public class MainViewController {
    
   
    
    @FXML private Button btnNew;
    
    @FXML private TextField txtTask;
    
    @FXML private ListView<String> taskList;
    
    private final TaskService service;

    public MainViewController() {
        TaskRepository repo=new JsonTaskRepository();
        this.service= new TaskService(repo);
    }
    
    @FXML
    public void initialize(){
        List<Task> tasks= service.getAll();
        tasks.stream().map(Task::getDescription).forEach(taskList.getItems()::add);
    }
    
    @FXML
    public void handleAddTask(){
        String desc= txtTask.getText().trim();
        if(!desc.isEmpty()){
            Task task= new Task(desc);
            service.add(task);
            taskList.getItems().add(desc);
            txtTask.clear();
        }
    
    }
    
}
