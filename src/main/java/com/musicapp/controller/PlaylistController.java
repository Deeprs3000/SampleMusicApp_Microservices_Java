package com.musicapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.musicapp.document.Playlist;
import com.musicapp.repository.PlaylistRepository;

@RestController
@RequestMapping("/playlist")
public class PlaylistController {

	@Autowired
	private PlaylistRepository playlistRepository;

	@GetMapping("/{id}")
	public Playlist getPlaylist(@PathVariable String id) {
		return playlistRepository.findBy_id(id);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletePlaylist(@PathVariable String id) {
		Playlist playlistPersisted = playlistRepository.findBy_id(id);
		playlistRepository.delete(playlistPersisted);
		return new ResponseEntity(HttpStatus.OK);
	}

	@GetMapping
	public List<Playlist> getPlaylistByUser(@RequestParam String userId) {
		return playlistRepository.findByUserId(userId);
	}

	@PostMapping
	public ResponseEntity<Playlist> createPlaylist(@RequestBody Playlist playlist) {
		Playlist playlistPersisted = playlistRepository.findBy_id(playlist.getId());
		int size = 0;
		String name = "";
		if (playlistPersisted != null) {
			name = playlistPersisted.getName();
			if (playlistPersisted.getSongs().size() > 0) {
				size = playlistPersisted.getSongs().size();
			}
			if (playlist != null) {
				name = playlist.getName();
				if (playlist.getSongs().size() > 0) {
					size += playlist.getSongs().size();
				}
			}
			playlistPersisted.setName(name);
			playlistPersisted.setLength(size);
			if (playlist.getSongs().size() > 0) {
				playlistPersisted.getSongs().addAll(playlist.getSongs());
			}
			playlistRepository.save(playlist);
		} else {
			playlist.setLength(playlist.getSongs().size());
			playlistRepository.save(playlist);
		}
		return new ResponseEntity<Playlist>(playlist, HttpStatus.CREATED);
	}

}
