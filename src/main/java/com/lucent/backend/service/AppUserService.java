package com.lucent.backend.service;

import com.lucent.backend.Repo.AppUserRepo;
import com.lucent.backend.Repo.RoleRepo;
import com.lucent.backend.api.Exception.DuplicateEmailException;
import com.lucent.backend.api.dto.AppUserRequest;
import com.lucent.backend.api.dto.AppUserResponse;
import com.lucent.backend.domain.AppUser;
import com.lucent.backend.domain.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;

import static com.lucent.backend.Notifications.UserManagement.sendVerificationCode;

@Service @RequiredArgsConstructor @Transactional @Slf4j
public class AppUserService implements UserDetailsService {
    /**
     * User Service for Custom User : AppUser
     * Handles functionalities from controllers
     */
    private final PasswordEncoder passwordEncoder;
    private final AppUserRepo appUserRepo;
    private final RoleRepo roleRepo;

    /**
     * Maps built in UserDetailService to our custom user model: AppUserService
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AppUser appUser = appUserRepo.findAppUserByEmail(email);
        if(appUser == null){
            log.error("User not found");
            throw new UsernameNotFoundException("User not found");
        }
        else{
            log.info("User found {}", email);
        }

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(appUser.getRole().getName()));
        return new User(appUser.getEmail(), appUser.getPassword(), authorities);
    }

    /**
     * Returns user given the email
     * @param email A String literal
     * @return the user
     */
    public AppUserResponse getUser(String email){
        return new AppUserResponse(appUserRepo.findAppUserByEmail(email));
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

        if(appUserRepo.findAppUserByEmail(userRequest.getEmail()) != null){
            throw new DuplicateEmailException("User with email already exists.");
        }

        AppUser user = new AppUser();
        user.setName(userRequest.getName());
        user.setEmail(userRequest.getEmail());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setRole(this.getRole("ROLE_DONOR"));
        user.setVerificationCode((int)(Math.random()*(999999-100000+1)+100000 ));  // random number between 100000 and 999999

        AppUser savedUser =  appUserRepo.save(user);
        sendVerificationCode(savedUser, siteurl);
        return new AppUserResponse(savedUser);
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
