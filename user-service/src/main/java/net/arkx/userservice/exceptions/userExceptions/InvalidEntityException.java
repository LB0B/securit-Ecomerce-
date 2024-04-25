package net.arkx.userservice.exceptions.userExceptions;

public class InvalidEntityException extends RuntimeException{
    public InvalidEntityException(String message) {
        super(message);
    }
}
