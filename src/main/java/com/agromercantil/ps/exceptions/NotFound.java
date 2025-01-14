package com.agromercantil.ps.exceptions;

public class NotFound extends RuntimeException {
    public NotFound(Class<?> clazz, Long id) {
        super(clazz.getSimpleName() + " not found with id: " + id);
    }
}
