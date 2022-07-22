package com.lucent.backend.Notifications;

import com.lucent.backend.domain.AppUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TextService {
    public void sendVerficationText(AppUser user){
        System.out.println(user.getPhone() + " " + user.getVerificationCode());
    }
}
