package com.musicapp.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.musicapp.document.Album;
import com.musicapp.repository.AlbumRepository;

@RestController
@RequestMapping("/music")
public class MusicController {

	@Autowired
	private AlbumRepository albumRepository;

	@GetMapping("/{id}")
	public Album getAlbum(@PathVariable String id) {
		return albumRepository.findBy_id(id);
	}

	@GetMapping
	public List<Album> getAlbums() {
		return albumRepository.findAll();
	}

	@PostMapping
	public ResponseEntity<List<Album>> createAlbum(@RequestBody List<Album> albums) {
		albums.forEach(album -> album.getSongs().forEach(song -> song.setId(UUID.randomUUID().toString())));
		albumRepository.save(albums);
		return new ResponseEntity<List<Album>>(albums, HttpStatus.CREATED);
	}

}
