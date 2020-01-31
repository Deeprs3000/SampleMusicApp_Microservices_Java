package com.musicapp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.musicapp.document.Users;

public interface UserRepository extends MongoRepository<Users, Integer> {

	Users findBy_id(String id);

}
