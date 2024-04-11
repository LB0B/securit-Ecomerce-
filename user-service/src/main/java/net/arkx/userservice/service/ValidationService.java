package net.arkx.userservice.service;

import jakarta.persistence.EntityNotFoundException;
import net.arkx.userservice.entities.User;
import net.arkx.userservice.entities.Validation;
import net.arkx.userservice.repository.ValidationRespository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Random;

import static java.time.temporal.ChronoUnit.MINUTES;

@Service
public class ValidationService {
    private final ValidationRespository validationRespository;
    private final NotificationService notificationService;

    public ValidationService(ValidationRespository validationRespository, NotificationService notificationService) {
        this.validationRespository = validationRespository;
        this.notificationService = notificationService;
    }

    public void register(User user){
        Validation validation = new Validation();
        validation.setUser(user);

        Instant creation = Instant.now();
        validation.setCreation(creation);

        Instant expiration = creation.plus(10, MINUTES);
        validation.setExpiration(expiration);

        Random random = new Random();
        int randomInteger = random.nextInt(999999);
        String code = String.format("%06d",randomInteger);
        validation.setCode(code);

        validationRespository.save(validation);
        notificationService.send(validation);
    }
    public Validation readingBasedOnCode(String code){
        return validationRespository.findByCode(code).orElseThrow(()-> new EntityNotFoundException("Your code is Invalid"));
    }
}
