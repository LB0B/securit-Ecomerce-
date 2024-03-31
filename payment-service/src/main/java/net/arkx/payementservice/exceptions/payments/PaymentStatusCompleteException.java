package net.arkx.payementservice.exceptions.payments;

public class PaymentStatusCompleteException extends RuntimeException{
    public PaymentStatusCompleteException(String message) {
        super(message);
    }
}
