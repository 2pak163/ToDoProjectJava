
package com.sebas.todo.util;

import com.sebas.todo.model.Task;
import org.junit.jupiter.api.*;
import java.io.File;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JsonUtilTest {
    
    private static final String FILE="util-test.json";
    
    @AfterEach
    void cleanup(){
        new File(FILE).delete();
    }
    
    @Test
    void writeAndReadTasks(){
        Task t1= new Task("T1");
        Task t2= new Task("T2");
        t2.setCompleted(true);
        
        JsonUtil.setDefaultFile(FILE); 
        JsonUtil.saveTasks(Arrays.asList(t1,t2));
        
        List<Task> loaded = JsonUtil.loadTasks();
        assertEquals(2,loaded.size());
        assertTrue(loaded.get(1).isCompleted());
    }
    
}
