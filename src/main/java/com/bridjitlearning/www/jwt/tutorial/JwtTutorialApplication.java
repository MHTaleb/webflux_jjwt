package com.bridjitlearning.www.jwt.tutorial;

import java.util.ArrayList;
import java.util.List;

import com.bridjitlearning.www.jwt.tutorial.config.encoder.PBKDF2Encoder;
import com.bridjitlearning.www.jwt.tutorial.domain.Role;
import com.bridjitlearning.www.jwt.tutorial.domain.User;
import com.bridjitlearning.www.jwt.tutorial.repository.ReactiveUserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JwtTutorialApplication implements ApplicationRunner {

	@Autowired
	private ReactiveUserRepository reactiveUserRepository;
	@Autowired
	private PBKDF2Encoder encoder;

	public static void main(String[] args) {
		SpringApplication.run(JwtTutorialApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		if (reactiveUserRepository.count().block() == 0) {

			User admin = new User();
			admin.setUsername("admin");
			admin.setPassword(encoder.encode("admin"));
			admin.setEmail("admin@mail.com");
			List<Role> roles = new ArrayList<Role>();
			roles.add(Role.ROLE_ADMIN);
			admin.setRoles(roles);
			
			User user = new User();
			user.setUsername("user");
			user.setPassword(encoder.encode("user"));
			user.setEmail("user@mail.com");
			roles = new ArrayList<Role>();
			roles.add(Role.ROLE_USER);
			user.setRoles(roles);

			User guest = new User();
			guest.setUsername("guest");
			guest.setPassword(encoder.encode("guest"));
			guest.setEmail("guest@mail.com");
			roles = new ArrayList<Role>();
			roles.add(Role.ROLE_GUEST);
			guest.setRoles(roles);

			reactiveUserRepository.save(admin).subscribe();
			reactiveUserRepository.save(user).subscribe();
			reactiveUserRepository.save(guest).subscribe();

			System.out.println("number of saved users is ; "+reactiveUserRepository.count().block());
			return;
		}
		System.out.println("database already initialized");
	}

}
