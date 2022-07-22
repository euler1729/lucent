package com.lucent.backend.Notifications;

import com.lucent.backend.domain.AppUser;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TextService {

    @Value("${smsapiusername}")
    private String apiUsername;
    @Value("${smsapipassword}")
    private String apiPassword;
    public void sendVerficationText(AppUser user)  {
        String message = String.valueOf(user.getVerificationCode()) + "%20is%20your%20OTP%20verification%20code%20for%20Lucent.";
        String url = "http://66.45.237.70/api.php?" + "username=" + apiUsername + "&password=" + apiPassword + "&number=" + user.getPhone() + "&message=" + message;

        try {
            Unirest.setTimeouts(0, 0);
            System.out.println("url = " + url);
            HttpResponse<String> response = Unirest.post(url)
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .body("")
                    .asString();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }

    }
}
