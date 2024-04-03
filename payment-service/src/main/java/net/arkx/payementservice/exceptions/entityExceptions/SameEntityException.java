package net.arkx.payementservice.exceptions.entityExceptions;

public class SameEntityException extends RuntimeException{
    public SameEntityException(String message) {
        super(message);
    }
}
