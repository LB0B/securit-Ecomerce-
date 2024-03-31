package net.arkx.payementservice.exceptions.orders;

public class SameOrderIdException extends RuntimeException{
    public SameOrderIdException(String message) {
        super(message);
    }
}
