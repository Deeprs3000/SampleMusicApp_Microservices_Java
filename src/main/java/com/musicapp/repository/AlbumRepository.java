package com.musicapp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.musicapp.document.Album;

public interface AlbumRepository extends MongoRepository<Album, Integer> {

	Album findBy_id(String id);

}
