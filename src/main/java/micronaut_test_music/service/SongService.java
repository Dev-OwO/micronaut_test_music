package micronaut_test_music.service;

import java.util.List;

import jakarta.inject.Singleton;
import jakarta.transaction.Transactional;
import micronaut_test_music.model.Song;
import micronaut_test_music.repo.SongRepository;

@Singleton
public class SongService {
	private final SongRepository songRepository;
    
    public SongService(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    public List<Song> getAllSongs() {
        return songRepository.findAll();
    }
    
    @Transactional
    public void addSongs(List<Song> songs) {
        if (songs == null || songs.isEmpty()) {
            return;
        }
        
        for (Song song : songs) {
            songRepository.save(song);
        }
    }

    @Transactional
    public void addSong(Song song) {
        if (song != null) {
            songRepository.save(song);
        }
    }

    public long getSongCount() {
        return songRepository.count();
    }
}
