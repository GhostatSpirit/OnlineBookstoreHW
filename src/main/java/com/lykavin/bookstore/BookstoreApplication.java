package com.lykavin.bookstore;

import com.lykavin.bookstore.model.RoleEntity;
import com.lykavin.bookstore.model.UserEntity;
import com.lykavin.bookstore.repository.RoleRepository;
import com.lykavin.bookstore.repository.UserRepository;
import com.lykavin.bookstore.utility.SecurityUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.Collection;

@SpringBootApplication
public class BookstoreApplication implements CommandLineRunner {

	@Autowired
	RoleRepository roleRepository;
	@Autowired
	UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(BookstoreApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception{
		RoleEntity userRole = initializeRole(RoleRepository.userRoleName);
		RoleEntity adminRole = initializeRole(RoleRepository.adminRoleName);

		Collection<RoleEntity> adminRoles = new ArrayList<RoleEntity>();
		adminRoles.add(userRole);
		adminRoles.add(adminRole);
		Collection<RoleEntity> customerRoles = new ArrayList<>();
		customerRoles.add(userRole);


		addAdmin("super", "super", adminRoles);
		addUser("lykavin", "lykavin",
				"lykavin@hotmail.com", "18621588356",
				customerRoles);
		addUser("borderwing", "borderwing",
				"borderwing@hotmail.com", "13213544314",
				customerRoles);

	}

	private RoleEntity initializeRole(final String roleName){
		RoleEntity targetRole = roleRepository.findByName(roleName);
		if(targetRole == null){
			targetRole = new RoleEntity();
			targetRole.setName(roleName);
			targetRole = roleRepository.saveAndFlush(targetRole);
		}
		return targetRole;
	}

	private UserEntity addUser
			(String username, String password,
			 String email, String phone,
			 Collection<RoleEntity> roles){
        UserEntity user = userRepository.findByUsername(username);
		if(user != null) {
			return user;
		}

		user = new UserEntity();
		user.setUsername(username);
		user.setPassword(SecurityUtility.passwordEncoder().encode(password));
		user.setEmail(email);
		user.setPhone(phone);
		user.setRoles(roles);

		user = userRepository.saveAndFlush(user);
		return user;
	}

	private UserEntity addAdmin
			(String username, String password, Collection<RoleEntity> roles){
		UserEntity user = userRepository.findByUsername(username);
		if(user != null) {
			return user;
		}
		user = new UserEntity();
		user.setUsername(username);
		user.setPassword(SecurityUtility.passwordEncoder().encode(password));
		user.setRoles(roles);

		user = userRepository.saveAndFlush(user);
		return user;
	}

}
