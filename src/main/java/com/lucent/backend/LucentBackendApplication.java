package com.lucent.backend;

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

			AppUser admin = new AppUser();
			admin.setEmail("admin@email.com");
			admin.setName("Admin User");
			admin.setPassword("admin");
			admin.setRole(adminRole);
			appUserService.saveUser(admin);
//
//			AppUser manager = new AppUser();
//			manager.setEmail("manager@email.com");
//			manager.setName("Manager User");
//			manager.setPassword("manager");
//			appUserService.saveUser(admin);
//
//			AppUser donor = new AppUser();
//			donor.setEmail("manager@email.com");
//			donor.setName("Manager User");
//			donor.setPassword("manager");
//			appUserService.saveUser(admin);
		};
	}
}
