package dev.patika.veterinersistemi.core.config.exeption;

public class NotFoundException  extends RuntimeException{
    public NotFoundException(String message) {
        super(message);
    }
}
