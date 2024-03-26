package net.arkx.userservice.exceptions.wishlistExcpetions;

public class wishlistNotFoundException extends RuntimeException{
    public wishlistNotFoundException(String message) {
        super(message);
    }
}
