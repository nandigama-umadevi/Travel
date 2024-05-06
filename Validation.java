package io.nuggets.chicken_nugget.utils;

public class Validation {

    public static void checkEmpty(String value, String parameter) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException(parameter + " cannot be empty or null");
        }
    }
    
}
