package com.lucent.backend.Notifications;

import com.lucent.backend.domain.AppUser;

public class UserManagement {
    public static void sendVerificationCode(AppUser user){
        System.out.println(user.getName() + "  " + user.getVerificationCode());
    }
}
