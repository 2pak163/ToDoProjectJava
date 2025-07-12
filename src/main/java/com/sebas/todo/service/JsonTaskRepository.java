
package com.sebas.todo.service;

import com.sebas.todo.model.Task;
import com.sebas.todo.util.JsonUtil;
import java.util.List;


public class JsonTaskRepository implements TaskRepository {
   
    private final String filePath;
    
    public JsonTaskRepository(){
        this("tasks.json");
    }
    
    public JsonTaskRepository(String filePath){
        this.filePath=filePath;
    }
    
    @Override
    public List<Task> findAll(){
        return JsonUtil.loadTasks(filePath);
   };
    
    @Override
    public void saveAll(List<Task> tasks){
        JsonUtil.saveTasks(filePath,tasks);
    }
}
