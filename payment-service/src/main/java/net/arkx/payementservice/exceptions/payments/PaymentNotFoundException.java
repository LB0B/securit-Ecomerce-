package net.arkx.payementservice.exceptions.payments;

public class PaymentNotFoundException extends RuntimeException{
    public PaymentNotFoundException(String message) {
        super(message);
    }
}
