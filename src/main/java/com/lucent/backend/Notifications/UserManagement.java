package com.lucent.backend.Notifications;

import com.lucent.backend.domain.AppUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

@Slf4j
public class UserManagement {

    private JavaMailSender mailSender;

    public static void sendVerificationCode(AppUser user, String siteurl)  {

        String toAddress = user.getEmail();
        String fromAddress = "noreply@alvereduan.me";
        String senderName = "Lucent";
        String subject = "Verification Code";
        String content = "Dear [[name]],<br>"
                + "Please click the link below to verify your registration:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
                + "Thank you,<br>"
                + "Lucent.";

        content = content.replace("[[name]]", user.getName());
        String verifyURL = siteurl + "/user/verify?code=" + user.getVerificationCode();
        content = content.replace("[[URL]]", verifyURL);

        log.info(content);

//        try{
//            MimeMessage message = mailSender.createMimeMessage();
//
//            MimeMessageHelper helper = new MimeMessageHelper(message, true);
//            helper.setFrom(fromAddress);
//            helper.setTo(toAddress);
//            helper.setSubject(subject);
//            helper.setText(content, true);
//            mailSender.send(message);
//
//        }catch(MessagingException e){
//            e.printStackTrace();
//        }

    }
}
