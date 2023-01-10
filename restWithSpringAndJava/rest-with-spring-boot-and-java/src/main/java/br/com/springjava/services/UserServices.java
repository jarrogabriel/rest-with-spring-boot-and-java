package br.com.springjava.services;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.springjava.data.vo.v1.PersonVO;
import br.com.springjava.model.User;
import br.com.springjava.repositories.UserRepository;

@Service
public class UserServices implements UserDetailsService {

	private Logger logger = Logger.getLogger(UserServices.class.getName());

	@Autowired
	UserRepository userRepository;

	public UserServices(UserRepository userRepository) {
		this.userRepository = userRepository;
	}


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		logger.info("Finding one user by name " + username + "!");
		User user = userRepository.findByUsername(username);

		if (user != null) {
			return user;
		} else {
			throw new UsernameNotFoundException("Username" + username + "not found!");
		}

	}

}
