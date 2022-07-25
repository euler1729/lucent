package com.lucent.backend.Notifications;

import com.lucent.backend.domain.AppUser;
import com.lucent.backend.domain.DonationCollection;
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
    @Value("${sendMsg}")
    private Boolean sendMsg;

    /**
     * Retrives Phone and Format Message Body
     * @param user AppUser object
     */
    public void sendVerificationText(AppUser user)  {
        String message = String.valueOf(user.getVerificationCode()) + "%20is%20your%20OTP%20verification%20code%20for%20Lucent.";
        sendText(user.getPhone(), message);
    }

    public void sendDonationCollectionText(DonationCollection donationCollection, Double collected)  {
//        String message = "Dear [[donor]], [[spending]] tk of your [[available]] tk donation is being spent by [[organization]]. Thank you.";
//        message.replace("[[donor]]", donationCollection.getDonation().getDonor().getName());
//        message.replace("[[spending]]", String.valueOf(donationCollection.getAmount()));
//        message.replace("[[available]]", String.valueOf(donationCollection.getDonation().getAmount()));
//        message.replace("[[organization]]", String.valueOf(donationCollection.getSpending().getOrganization()));
//        message.replace(" ", "%20");

        String message = "Dear " +  donationCollection.getDonation().getDonor().getName() + ", " + String.valueOf(collected + "tk of your " + String.valueOf(donationCollection.getDonation().getAmount()) + "tk donation is being spent by " + String.valueOf(donationCollection.getSpending().getOrganization().getName())) + " as " + donationCollection.getSpending().getDescription() + ". Thank you.";
        message = message.replace(" ", "%20");
        sendText(donationCollection.getDonation().getDonor().getPhone(), message);
    }

    /**
     * Given To and MSG, sends the text
     * @param to Phone Number Comma Separated
     * @param msg String Message to send
     */
    public void sendText(String to, String msg){
        if(sendMsg) {
            String url = "http://66.45.237.70/api.php?" + "username=" + apiUsername + "&password=" + apiPassword + "&number=" + to + "&message=" + msg;
            System.out.println("url = " + url);
            try {
                Unirest.setTimeouts(0, 0);
                HttpResponse<String> response = Unirest.post(url)
                        .header("Content-Type", "application/x-www-form-urlencoded")
                        .body("")
                        .asString();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        else{
            System.out.println("to = " + to);
            System.out.println("msg = " + msg);
        }
    }
}
