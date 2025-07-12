
package com.sebas.todo.service;

import com.sebas.todo.model.Task;
import org.junit.jupiter.api.*;
import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class TaskServiceTest {
    
    private static final String TEST_FILE="test-tasks.json";
    private TaskService service;
    
    @BeforeEach
    void setup(){
        TaskRepository repo = new JsonTaskRepository(TEST_FILE);
        service=new TaskService(repo);
        new File(TEST_FILE).delete();
    }
    
    @AfterEach
    void cleanup(){
        new File(TEST_FILE).delete();
    }
    
    @Test
    void addAndRetrieve(){
        Task t1= new Task("Probar tests");
        service.add(t1);
        
        List<Task> list= service.getAll();
        assertEquals(1, list.size());
        assertEquals("Probar tests",list.get(0).getDescription());
    }
    
    @Test
    void removeTask(){
        Task t1= new Task("Tarea 1");
        service.add(t1);
        service.remove(t1);
        
        assertTrue(service.getAll().isEmpty());
    }
}
