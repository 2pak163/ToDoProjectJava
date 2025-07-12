
package com.sebas.todo.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class JsonUtil {
    private static final ObjectMapper MAPPER= new ObjectMapper().registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    
    private static final String FILE= "tasks.json";
    
    public static List<com.sebas.todo.model.Task> loadTask(){
       try{
           File f= new File(FILE);
           if(!f.exists()) return new ArrayList<>();
           return MAPPER.readValue(f,new TypeReference<List<com.sebas.todo.model.Task>>(){});
       }catch (IOException e){
           e.printStackTrace();
           return new ArrayList<>();
       }
    };
    
    public static void saveTasks(List<com.sebas.todo.model.Task>tasks){
        try{
            MAPPER.writerWithDefaultPrettyPrinter()
                    .writeValue(new File(FILE), tasks);
        }catch (IOException e){
                e.printStackTrace();
        }
    
    }
    
}
