
package com.sebas.todo.ui;

import com.sebas.todo.model.Task;
import com.sebas.todo.service.JsonTaskRepository;
import com.sebas.todo.service.TaskRepository;
import com.sebas.todo.service.TaskService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.layout.HBox;
import javafx.geometry.Pos;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.material2.Material2AL;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import javafx.scene.paint.Color;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.List;

public class MainViewController {
    
    @FXML private ImageView logoView;
    
    @FXML private ToggleButton toggleDark;
    private static final String LIGHT_CSS = "/style.css";
    private static final String DARK_CSS  = "/dark.css";
    
    private final FontIcon iconSun   = new FontIcon(FontAwesomeSolid.SUN);
    private final FontIcon iconMoon  = new FontIcon(FontAwesomeSolid.MOON);
    
    
    @FXML private RadioButton rbAll;
    @FXML private RadioButton rbPending;
    @FXML private RadioButton rbDone;
    @FXML private TextField txtSearch;
    private ToggleGroup filterGroup;
    
    @FXML private Button btnNew;
    @FXML private Button btnDeleteAll;
    @FXML private TextField txtTask;
    @FXML private ListView<Task> taskList;
    
    private final TaskService service;
    private final ObservableList<Task> masterTasks= FXCollections.observableArrayList();
    private final FilteredList<Task> filteredTasks =new FilteredList<>(masterTasks, t->true);

    public MainViewController() {
        TaskRepository repo=new JsonTaskRepository();
        this.service= new TaskService(repo);
    }
    
    @FXML
    public void initialize(){
        
        List<Task> loaded= service.getAll();
        masterTasks.setAll(loaded);
        
        filterGroup=new ToggleGroup();
        rbAll.setToggleGroup(filterGroup);
        rbPending.setToggleGroup(filterGroup);
        rbDone.setToggleGroup(filterGroup);
        
        Image logo = new Image(getClass().getResourceAsStream("/icons/logo.png"));
        logoView.setImage(logo);
        
        iconSun.setIconSize(18);
        iconMoon.setIconSize(18);
        toggleDark.setGraphic(iconMoon);
        
        taskList.setItems(filteredTasks);
        filterGroup.selectedToggleProperty().addListener((obs,oldToggle,newToggle)->updateFilter());
        txtSearch.textProperty().addListener((obs,old,nw)-> updateFilter());
        setupCellFactory();
        updateFilter();
    }

    private void setupCellFactory(){
    
        taskList.setCellFactory(lv->new ListCell<>(){
            
            private final FontIcon editIcon   = new FontIcon(Material2AL.EDIT);
            private final FontIcon deleteIcon = new FontIcon(Material2AL.DELETE);
            private final Button btnDeleteCell = new Button("",deleteIcon);
            private final Button btnEdit= new Button("",editIcon);
            private final CheckBox chk = new CheckBox();
            private final Region spacer = new Region();
            private final HBox hbox = new HBox(8, chk,spacer,btnEdit, btnDeleteCell);
            
                {
                    HBox.setHgrow(spacer,Priority.ALWAYS);
                    hbox.setAlignment(Pos.CENTER_LEFT);
                    
                    editIcon.setIconSize(18);
                    deleteIcon.setIconSize(18);
                    editIcon.getStyleClass().add("icon-white");
                    deleteIcon.getStyleClass().add("icon-white");
                    chk.setStyle("-fx-font-size= 13px;");
                    btnDeleteCell.getStyleClass().addAll("icon-button","delete-icon");
                    btnEdit.getStyleClass().addAll("icon-button","edit-icon");
                    
                    chk.setOnAction(evt->{
                        Task item=getItem();
                        if( item != null){
                            item.setCompleted(chk.isSelected());
                            service.update(item);
                        }
                    
                    });
                    
                    btnDeleteCell.setOnAction(evt->{
                        Task item=getItem();
                        if(item != null && confirm(String.format("¿Eliminar la tarea \"%s\"?",
                                item.getDescription()))){
                            service.remove(item);
                            masterTasks.remove(item);
                        }
                    });
                    
                    btnEdit.setOnAction(evt->{
                        Task item=getItem();
                        if(item != null){
                            TextInputDialog dialog= new TextInputDialog(item.getDescription());
                            dialog.setTitle("Editar tarea");
                            dialog.setHeaderText(null);
                            dialog.setContentText("Nueva Descripción de la tarea: ");
                            dialog.showAndWait().ifPresent(desc ->{
                                String d = desc.trim();
                                if(!d.isEmpty()){
                                    item.setDescription(d);
                                    service.update(item);
                                    taskList.refresh();
                                }
                            });
                        }
                    
                    });
                }
            
            @Override
            protected void updateItem(Task item, boolean empty){
                super.updateItem(item, empty);
                if(empty || item == null){
                    setGraphic(null);
                }else{
                    chk.setText(item.getDescription());
                    chk.setSelected(item.isCompleted());
                    setGraphic(hbox);
                }
            };
        });
    
    };
    
    private void updateFilter(){
        String text= txtSearch.getText().trim().toLowerCase();
        
        boolean showAll= rbAll.isSelected();
        boolean showPending= rbPending.isSelected();
        boolean showCompleted= rbDone.isSelected();
        
        filteredTasks.setPredicate(task -> {
            
            if(!showAll){
                if(showPending && task.isCompleted()) return false;
                if(showCompleted && !task.isCompleted()) return false;
            }
            
            return text.isEmpty() || task.getDescription().toLowerCase().contains(text);
        });
    };
    
    @FXML
    private void onSearchKey(){
        updateFilter();    
    }
    
    
    @FXML
    public void handleAddTask(){
        String desc= txtTask.getText().trim();
        if(!desc.isEmpty()){
            Task task= new Task(desc);
            service.add(task);
            masterTasks.add(task);
            txtTask.clear();
            updateFilter();
        }
    
    }
    
    @FXML
    public void handleDeleteAllTasks(){  
        if(confirm("¿Estás seguro que quiere eliminar todas las tareas?")){
            service.removeAll();
            masterTasks.clear();
            updateFilter();
        }
    }
    
    public boolean confirm(String msg){
        Alert a= new Alert(Alert.AlertType.CONFIRMATION,msg,ButtonType.OK,ButtonType.CANCEL);
        a.setHeaderText(null);
        return a.showAndWait().filter(b->b==ButtonType.OK).isPresent();
    };
    
    @FXML
    public void onToggleDarkMode() {
       
        Scene scene = txtTask.getScene();
        if (toggleDark.isSelected()) {
            scene.getStylesheets().setAll(
                getClass().getResource(DARK_CSS).toExternalForm()
            );
            iconSun.setIconColor(Color.WHITE); 
            toggleDark.setGraphic(iconSun);  
        } else {
            scene.getStylesheets().setAll(
                getClass().getResource(LIGHT_CSS).toExternalForm()
            );
            iconMoon.setIconColor(Color.BLACK);
            toggleDark.setGraphic(iconMoon);
        }
    }
    
}
