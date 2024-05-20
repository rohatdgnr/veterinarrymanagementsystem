package dev.patika.veterinersistemi.core.config.exception;

public class NotFoundException  extends RuntimeException{
    public NotFoundException(String message) {
        super(message);
    }
}
