package net.arkx.payementservice.exceptions.shipping;

public class ShippingNotFoundException extends RuntimeException{
    public ShippingNotFoundException(String message) {
        super(message);
    }
}
