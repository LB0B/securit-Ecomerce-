package net.arkx.userservice.exceptions.RoleExceptions;

public class RoleAlreadyExistUserException extends RuntimeException{
    public RoleAlreadyExistUserException(String message) {
        super(message);
    }
}
