package com.lucent.backend.api;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lucent.backend.api.Exception.DuplicatePhoneException;
import com.lucent.backend.api.dto.AppUserRequest;
import com.lucent.backend.api.dto.AppUserResponse;
import com.lucent.backend.domain.AppUser;
import com.lucent.backend.domain.Role;
import com.lucent.backend.service.AppUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AppUserController {

    private final AppUserService appUserService;

    @Value("${JWTSECRET}")
    private String jwtSecret;

    /**
     * Retrive site url domain
     * @param request A HttpServletRequest object
     * @return A sting literal : siteurl
     */
    private String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }

    /**
     * Registers a donor user
     * @param user a donor user - JSON object
     * @param request A HttpServletRequest object
     * @return Saved User Response
     * @throws DuplicatePhoneException if user already exists with given email
     */
    @PostMapping("/user/registration")
    public ResponseEntity<AppUserResponse> registerDonor(@RequestBody @Valid AppUserRequest user, HttpServletRequest request) throws DuplicatePhoneException {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/user/registration").toUriString());
        AppUserResponse savedUser = appUserService.saveUser(user, appUserService.getRole("ROLE_MANAGER"), this.getSiteURL(request));
        return ResponseEntity.created(uri).body(savedUser);
    }

    /**
     * Returns User Profile
     * @param request HttpServletRequest object
     * @param response HttpServletRequest object
     * @return User's profile as AppUserResponse
     */
    @GetMapping("/user/profile")
    public ResponseEntity<AppUserResponse> getProfile(HttpServletRequest request, HttpServletResponse response){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        return ResponseEntity.ok().body(appUserService.getUserResponse((String) auth.getPrincipal()));
    }

    /**
     * Verify donor user's email
     * @param code Int value as parameter
     * @param phone String value as parameter
     * @return Success Status
     */
    @PostMapping("/user/verify")
    public ResponseEntity<Map<String, Boolean>> verifyAccount(@RequestParam int code, @RequestParam String phone){
        Boolean success = appUserService.verifyUser(phone, code);

        Map<String, Boolean> successMsg = new HashMap<>();
        successMsg.put("Success", success);
        return ResponseEntity.ok().body(successMsg);
    }

    /**
     * Refreshes Access Token given verification token
     * @param request HttpServletRequest object
     * @param response HttpServletRequest object
     * @throws IOException if something goes wrong
     */
    @GetMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            try {
                String refresh_token = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256(jwtSecret.getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refresh_token);
                String email = decodedJWT.getSubject();
                AppUser user = appUserService.getUser(email);

                Collection<Role> roles = new ArrayList<>();
                roles.add(user.getRole());

                String access_token = JWT.create()
                        .withSubject(user.getPhone())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                        .withIssuer(request.getRequestURI().toString())
                        .withClaim("roles", roles.stream().map(Role::getName).collect(Collectors.toList()))
                        .sign(algorithm);

                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", access_token);
                tokens.put("refresh_token", refresh_token);
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);
            }
            catch (Exception exception){
                log.error("Error logging in: {}", exception.getMessage());
                response.setHeader("error", exception.getMessage());
                response.setStatus(FORBIDDEN.value());
                Map<String, String> error = new HashMap<>();
                error.put("error_message", exception.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }


        } else{
            throw new RuntimeException("Refresh token is missing");
        }
    }

}


