package com.lucent.backend;

import com.lucent.backend.api.dto.AppUserRequest;
import com.lucent.backend.api.dto.OrganizationRequest;
import com.lucent.backend.domain.AppUser;
import com.lucent.backend.domain.Organization;
import com.lucent.backend.domain.Role;
import com.lucent.backend.service.AppUserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableAsync
public class LucentBackendApplication {
	public static void main(String[] args) {
		SpringApplication.run(LucentBackendApplication.class, args);
	}

	@Bean
	PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}

	/**
	 * Saves an admin user while program starts
	 * @param appUserService AppUserService
	 */
	@Bean
	CommandLineRunner run(AppUserService appUserService){
		return args -> {
			Role adminRole = appUserService.saveRole(new Role(null, "ROLE_ADMIN"));
			Role managerRole = appUserService.saveRole(new Role(null, "ROLE_MANAGER"));
			Role donorRole = appUserService.saveRole(new Role(null, "ROLE_DONOR"));

			AppUserRequest adminUser = new AppUserRequest();
			adminUser.setPhone("01924165786");
			adminUser.setName("Admin User");
			adminUser.setPassword("admin");
			appUserService.saveUser(adminUser, appUserService.getRole("ROLE_ADMIN"), "application");

			AppUserRequest donorUser = new AppUserRequest();
			donorUser.setPhone("01782267068");
			donorUser.setName("Donor User");
			donorUser.setPassword("donor");
			appUserService.saveUser(donorUser, appUserService.getRole("ROLE_DONOR"), "application");
		};
	}
}
