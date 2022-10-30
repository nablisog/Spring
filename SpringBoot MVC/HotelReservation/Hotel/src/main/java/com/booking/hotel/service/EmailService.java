package com.booking.hotel.service;

import com.booking.hotel.model.Reservation;
import com.booking.hotel.model.User;
import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    public void sendEmail(String email, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("hotelresevationjava.app@gmail.com");
        message.setTo(email);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);

    }

    public void sendUpdateMail(User user, Reservation update){
        sendEmail(user.getEmail(),"Reservation ",
                "Confirmation of Updated Reservation for: " +
                        update.getUser().getLastName()+
                        " "+update.getUser().getFirstName() +
                        " Room Type:- "+ update.getRoom().getType()+
                        " from:- "+ update.getFrom() +
                        " to:- "+ update.getTo());
    }


}
