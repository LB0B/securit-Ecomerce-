package net.arkx.payementservice.exceptions;

import net.arkx.payementservice.exceptions.entityExceptions.EntityIdNullException;
import net.arkx.payementservice.exceptions.entityExceptions.EntityNotFoundException;
import net.arkx.payementservice.exceptions.entityExceptions.SameEntityException;
import net.arkx.payementservice.exceptions.payments.AmountNotCorrectException;
import net.arkx.payementservice.exceptions.payments.PaymentStatusCompleteException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler({
            EntityIdNullException.class,
            EntityNotFoundException.class,
            SameEntityException.class,
            AmountNotCorrectException.class,
            PaymentStatusCompleteException.class
    })
    public ResponseEntity<ErrorResponse> handleException(Exception exception) {
        int httpStatus;
        String message;

        if (exception instanceof EntityIdNullException || exception instanceof EntityNotFoundException) {
            httpStatus = HttpStatus.NOT_FOUND.value();
            message = "Entity not found";
        } else if (exception instanceof SameEntityException) {
            httpStatus = HttpStatus.BAD_REQUEST.value();
            message = "Duplicate entity detected";
        } else if (exception instanceof AmountNotCorrectException) {
            httpStatus = HttpStatus.BAD_REQUEST.value();
            message = "Invalid payment amount";
        } else if (exception instanceof PaymentStatusCompleteException) {
            httpStatus = HttpStatus.CONFLICT.value();
            message = "Payment already completed";
        } else {
            // Handle unexpected exceptions (consider logging here)
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR.value();
            message = "Internal server error";
        }

        return ResponseEntity.status(httpStatus).body(
                ErrorResponse.builder()
                        .message(message)
                        .status(httpStatus)
                        .build()
        );
    }
}