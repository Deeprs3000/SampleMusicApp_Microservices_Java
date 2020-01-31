package com.musicapp.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.musicapp.document.Playlist;

public interface PlaylistRepository extends MongoRepository<Playlist, Integer> {

	Playlist findBy_id(String id);

	List<Playlist> findByUserId(String userId);

}
