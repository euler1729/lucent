package com.lucent.backend;

import com.lucent.backend.api.dto.AppUserRequest;
import com.lucent.backend.domain.AppUser;
import com.lucent.backend.domain.Role;
import com.lucent.backend.service.AppUserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class LucentBackendApplication {
	public static void main(String[] args) {
		SpringApplication.run(LucentBackendApplication.class, args);
	}

	@Bean
	PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}

	@Bean
	CommandLineRunner run(AppUserService appUserService){
		return args -> {
			Role adminRole = appUserService.saveRole(new Role(null, "ROLE_ADMIN"));
			Role managerRole = appUserService.saveRole(new Role(null, "ROLE_MANAGER"));
			Role donorRole = appUserService.saveRole(new Role(null, "ROLE_DONOR"));

			AppUserRequest donorUser = new AppUserRequest();
			donorUser.setPhone("01782267068");
			donorUser.setName("Admin User");
			donorUser.setPassword("admin");
			appUserService.saveUser(donorUser, "localhost:8080");
		};
	}
}
