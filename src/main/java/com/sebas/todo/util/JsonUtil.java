
package com.sebas.todo.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.sebas.todo.model.Task;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class JsonUtil {
    private static final ObjectMapper MAPPER= new ObjectMapper().registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    
     private static String defaultFilePath = "tasks.json";
     
     public static void setDefaultFile(String filePath){
         defaultFilePath=filePath;
     };
    
    public static void saveTasks(List<Task> tasks){
        saveTasks(defaultFilePath,tasks);
    };
    
    public static void saveTasks(String filePath,List<Task>tasks){
        try{
            MAPPER.writerWithDefaultPrettyPrinter()
                    .writeValue(new File(filePath), tasks);
        }catch (IOException e){
                e.printStackTrace();
        }
    
    }
     
    public static List<Task> loadTasks(){
        return loadTasks(defaultFilePath);
    };
     
    public static List<Task> loadTasks(String filePath){
       try{
           File f= new File(filePath);
           if(!f.exists()) return new ArrayList<>();
           return MAPPER.readValue(f,new TypeReference<List<Task>>(){});
       }catch (IOException e){
           e.printStackTrace();
           return new ArrayList<>();
       }
    };
    
   
    
}
