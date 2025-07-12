
package com.sebas.todo.service;

import com.sebas.todo.model.Task;

import java.util.List;

public interface TaskRepository {
    List<Task> findAll();
    void saveAll(List<Task> tasks);
}
