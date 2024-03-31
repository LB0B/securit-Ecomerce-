package net.arkx.payementservice.exceptions.payments;

public class AmountNotCorrectException extends RuntimeException{
    public AmountNotCorrectException(String message) {
        super(message);
    }
}
