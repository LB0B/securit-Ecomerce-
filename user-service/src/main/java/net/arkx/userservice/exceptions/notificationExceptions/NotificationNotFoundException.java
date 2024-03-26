package net.arkx.userservice.exceptions.notificationExceptions;

public class NotificationNotFoundException extends RuntimeException{
    public NotificationNotFoundException(String message) {
        super(message);
    }
}
