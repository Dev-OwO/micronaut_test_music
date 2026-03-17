package micronaut_test_music.service;

import java.time.LocalDate;
import java.util.List;

import jakarta.inject.Singleton;
import jakarta.transaction.Transactional;
import micronaut_test_music.model.Song;
import micronaut_test_music.repo.SongRepository;

@Singleton
public class SongService {
	private final SongRepository songRepository;
	private SongMetadataParser parser;
    
    public SongService(SongRepository songRepository, SongMetadataParser parser) {
        this.songRepository = songRepository;
        this.parser = parser;
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
    
    @Transactional
    public void addSong(byte[] songBytes) throws Exception {
        if (songBytes == null || songBytes.length == 0)
        	return;
        
    	Song song = new Song();
		song = parser.parseSongMetadata(songBytes, song);
		song.setAddedDate(LocalDate.now());
		
        songRepository.save(song);
    }

    public long getSongCount() {
        return songRepository.count();
    }
}
