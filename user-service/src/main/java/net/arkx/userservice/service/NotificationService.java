package net.arkx.userservice.service;


import net.arkx.userservice.entities.Validation;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
     private final JavaMailSender javaMailSender;

    public NotificationService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }


    public void send (Validation validation){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("no-reply@bob.com");
        mailMessage.setTo(validation.getUser().getEmail());
        mailMessage.setSubject("Your activation code");
        String text = String.format(
                "Hello %s <br/> Your Activation code is %s ; See ya",
                validation.getUser().getFirstName(),
                validation.getCode()
        );
        mailMessage.setText(text);
        javaMailSender.send(mailMessage);
    }
}
