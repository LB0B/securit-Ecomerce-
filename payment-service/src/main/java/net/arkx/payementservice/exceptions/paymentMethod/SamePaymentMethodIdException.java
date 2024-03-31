package net.arkx.payementservice.exceptions.paymentMethod;

public class SamePaymentMethodIdException extends RuntimeException{
    public SamePaymentMethodIdException(String message) {
        super(message);
    }
}
