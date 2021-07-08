package com.edmundo.todolist.util;

import java.util.function.Consumer;

import org.springframework.stereotype.Component;

@Component
public class PatchUtil {
	
	// Pass to the setter if the value entered is not null
	public static <T> void updateIfNotNull(Consumer<T> setterMethod, T value) {
        if (value != null){
            setterMethod.accept(value);
        }
    }
	
}	
