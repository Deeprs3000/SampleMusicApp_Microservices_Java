package com.musicapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.musicapp.document.Users;
import com.musicapp.repository.UserRepository;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserRepository userRepository;

	@GetMapping("/{id}")
	public Users getUser(@PathVariable String id) {
		return userRepository.findBy_id(id);
	}

	@PutMapping
	public Users updateUser(@RequestBody Users user) {
		Users u = userRepository.save(user);
		u.setPassword(null);
		return u;
	}
	
	@PostMapping
	public Users createUser(@RequestBody Users user) {
		Users u = userRepository.save(user);
		u.setPassword(null);
		return u;
	}
}
