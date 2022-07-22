package com.lucent.backend.Notifications;

import com.lucent.backend.domain.AppUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Slf4j @Service
public class EmailService {

    @Autowired private JavaMailSender javaMailSender;

    /**
     * Sends verification url to the specified email
     * @param user AppUser object
     * @param siteurl String object
     */
    public void sendVerificationCode(AppUser user, String siteurl)  {

        String toAddress = user.getPhone();
        String fromAddress = "noreply@alvereduan.me";
        String senderName = "Lucent";
        String subject = "Verification Code";
        String content = "Dear [[name]],<br>"
                + "Please click the link below to verify your registration:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
                + "Thank you,<br>"
                + "Lucent.";

        content = content.replace("[[name]]", user.getName());
        String verifyURL = "http://" + siteurl + "/user/verify?code=" + user.getVerificationCode() + "&email=[[EMAIL]]";
        content = content.replace("[[URL]]", verifyURL);
        content = content.replace("[[EMAIL]]", user.getPhone());

        try{
            MimeMessage msg = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(msg, true);

            helper.setTo(toAddress);
            helper.setFrom(fromAddress);
            helper.setSubject(subject);
            helper.setText(content, true);

            javaMailSender.send(msg);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
