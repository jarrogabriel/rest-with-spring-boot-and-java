package br.com.springjava.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.springjava.data.vo.v1.secutiry.AccountCredentialsVO;
import br.com.springjava.data.vo.v1.secutiry.TokenVO;
import br.com.springjava.model.User;
import br.com.springjava.repositories.UserRepository;
import br.com.springjava.security.jwt.JwtTokenProvider;

@Service
public class AuthServices {

	@Autowired
	private JwtTokenProvider tokenProvider;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserRepository repository;

	public ResponseEntity signin(AccountCredentialsVO data) {
		try {

			String username = data.getUserName();
			String password = data.getPassword();

			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

			User user = repository.findByUsername(username);

			TokenVO tokenResponse = new TokenVO();

			if (user != null) {
				tokenResponse = tokenProvider.createAccessToken(username, user.getRoles());
			} else {
				throw new UsernameNotFoundException("Username" + username + "not found !");
			}

			return ResponseEntity.ok(tokenResponse);

		} catch (Exception e) {

			throw new BadCredentialsException("Invalid username/password supplied");
		}
	}

	public ResponseEntity refreshToken(String username, String refreshToken) {

		User user = repository.findByUsername(username);

		TokenVO tokenResponse = new TokenVO();

		if (user != null) {
			tokenResponse = tokenProvider.refreshToken(refreshToken);
		} else {
			throw new UsernameNotFoundException("Username" + username + "not found !");
		}

		return ResponseEntity.ok(tokenResponse);

	}

}
