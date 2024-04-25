package net.arkx.userservice.exceptions.userExceptions;

public class DuplicateEntityException extends RuntimeException{
    public DuplicateEntityException(String message) {
        super(message);
    }
}
