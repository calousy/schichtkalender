package com.kmeisl.schichtkalender.dataprovider;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kmeisl.schichtkalender.domain.User;
import com.kmeisl.schichtkalender.repository.UserRepository;

@Component
public class UserProvider {

	@Autowired
	private UserRepository userRepository;
	
	public User authenticate(String username, String password)
	{
		//TODO userRepository.findBy
		
		
		User user = new User(1, "Klaus", "Meisl", "", "", "", new Date());
		user.setAdmin(true);
		
		return user;
	}
	
}
