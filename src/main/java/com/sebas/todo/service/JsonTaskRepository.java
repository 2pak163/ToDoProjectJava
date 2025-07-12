
package com.sebas.todo.service;

import com.sebas.todo.model.Task;
import com.sebas.todo.util.JsonUtil;
import java.util.List;


public class JsonTaskRepository implements TaskRepository {
   
    
    @Override
    public List<Task> findAll(){
        return JsonUtil.loadTask();
   };
    
    @Override
    public void saveAll(List<Task> tasks){
        JsonUtil.saveTasks(tasks);
    }
}
