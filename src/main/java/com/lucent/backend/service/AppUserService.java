package com.lucent.backend.service;

import com.lucent.backend.Notifications.TextService;
import com.lucent.backend.Repo.AppUserRepo;
import com.lucent.backend.Repo.RoleRepo;
import com.lucent.backend.api.Exception.DuplicateEmailException;
import com.lucent.backend.api.dto.AppUserRequest;
import com.lucent.backend.api.dto.AppUserResponse;
import com.lucent.backend.domain.AppUser;
import com.lucent.backend.domain.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.Collection;

@Service @RequiredArgsConstructor @Transactional @Slf4j
public class AppUserService implements UserDetailsService {
    /**
     * User Service for Custom User : AppUser
     * Handles functionalities from controllers
     */
    private final PasswordEncoder passwordEncoder;
    private final AppUserRepo appUserRepo;
    private final RoleRepo roleRepo;
    @Autowired private TextService textService;

    /**
     * Maps built in UserDetailService to our custom user model: AppUserService
     */
    @Override
    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
        AppUser appUser = appUserRepo.findAppUserByPhone(phone);
        if(appUser == null){
            log.error("User not found");
            throw new UsernameNotFoundException("User not found");
        }
        else{
            log.info("User found {}", phone);
        }

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(appUser.getRole().getName()));
        return new User(appUser.getPhone(), appUser.getPassword(), authorities);
    }

    /**
     * Returns User Response DTO given the email
     * @param phone A String literal
     * @return the user
     */
    public AppUserResponse getUserResponse(String phone){
        return new AppUserResponse(appUserRepo.findAppUserByPhone(phone));
    }

    /**
     * Returns user given the email
     * @param phone A String literal
     * @return the user
     */
    public AppUser getUser(String phone){
        return appUserRepo.findAppUserByPhone(phone);
    }


    /**
     * Saved the AppUser to the database
     * Checks
     *  - If No name or email was given
     *  - If email already exists
     *  - If password is less than 8 characters
     * @param userRequest A AppUserRequest DTO Object
     * @return Saved AppUser
     */
    public AppUserResponse saveUser(AppUserRequest userRequest, String siteurl) throws DuplicateEmailException{

        if(appUserRepo.findAppUserByPhone(userRequest.getPhone()) != null){
            throw new DuplicateEmailException("User with email already exists.");
        }

        AppUser user = new AppUser();
        user.setName(userRequest.getName());
        user.setPhone(userRequest.getPhone());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setRole(this.getRole("ROLE_DONOR"));
        user.setVerificationCode((int)(Math.random()*(999999-100000+1)+100000 ));  // random number between 100000 and 999999

        AppUser savedUser =  appUserRepo.save(user);
//        emailService.sendVerificationCode(savedUser, siteurl);
        textService.sendVerificationText(savedUser);
        return new AppUserResponse(savedUser);
    }

    /**
     * Verify user given email and verification code
     * @param phone User phone
     * @param code Verification Code
     * @return True if succeeded False otherwise
     */
    public Boolean verifyUser(String phone, int code){
        AppUser user = appUserRepo.findAppUserByPhone(phone);
        if(code == user.getVerificationCode()){
            appUserRepo.verifyAppUserByPhone(phone);
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Save a role | Used by Admin
     * @param role role object
     * @return saved role
     */
    public Role saveRole(Role role){
        log.info("Saving new role {}.", role.getName());
        return roleRepo.save(role);
    }

    /**
     * Get the Role given a roleName
     * @param roleName A String literal
     * @return A Role Object
     */
    public Role getRole(String roleName){
        return roleRepo.findRoleByName(roleName);
    }
}
