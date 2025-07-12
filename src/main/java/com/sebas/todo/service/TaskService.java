
package com.sebas.todo.service;

import com.sebas.todo.model.Task;

import java.util.List;


public class TaskService {
    public final TaskRepository repo;

    public TaskService(TaskRepository repo) {
        this.repo = repo;
    }
    
    public List<Task> getAll(){
        return repo.findAll();
    }
    
    public void add(Task task){
        List<Task> all= repo.findAll();
        all.add(task);
        repo.saveAll(all);
    }
    
    public void remove(Task task){
        List<Task> all=repo.findAll();
        all.removeIf(t->t.getId().equals(task.getId()));
        repo.saveAll(all);
    }
    
    public void update(Task updated){
        List<Task> all= repo.findAll();
        for(int i=0; i<all.size();i++){
            if(all.get(i).getId().equals(updated.getId())){
                all.set(i,updated);
                break;
            }
        }
        repo.saveAll(all);
    }
    
}
