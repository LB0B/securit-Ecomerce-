package net.arkx.userservice.exceptions;

import net.arkx.userservice.exceptions.userExceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionsHandler {
    @ExceptionHandler({
            AccountNotActiveException.class,
            DuplicateEntityException.class,
            EntityAlreadyExistException.class,
            EntityNotFoundException.class,
            InvalidEntityException.class
    })
    public ResponseEntity<ErrorResponse> handleException(Exception exception) {
        int httpStatus;
        String message;

        if (exception instanceof AccountNotActiveException) {
            httpStatus = HttpStatus.FORBIDDEN.value();
            message = "Account is not active";
        } else if (exception instanceof DuplicateEntityException || exception instanceof EntityAlreadyExistException) {
            httpStatus = HttpStatus.CONFLICT.value();
            message = "Duplicate entity found";
        } else if (exception instanceof EntityNotFoundException) {
            httpStatus = HttpStatus.NOT_FOUND.value();
            message = "Entity not found";
        } else if (exception instanceof InvalidEntityException) {
            httpStatus = HttpStatus.BAD_REQUEST.value();
            message = "Invalid entity data";
        } else {
            // Handle unexpected exceptions (consider logging here)
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR.value();
            message = "Internal server error";
        }

        return ResponseEntity.status(httpStatus).body(
                ErrorResponse.builder() // Assuming you have Lombok configured
                        .message(message)
                        .status(httpStatus)
                        .build()
        );
    }
}
