package micronaut_test_music.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

import org.apache.tika.exception.TikaException;
import org.xml.sax.SAXException;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.transaction.Transactional;
import micronaut_test_music.MusicConfig;
import micronaut_test_music.model.Song;
import micronaut_test_music.repo.SongRepository;

@Singleton
public class SongService {
	@Inject
	private MusicConfig musicConfig;
	
	static final String AUDIO_SUFFIX = "_vp";
	
	private final SongRepository songRepository;
	private SongMetadataParser parser;
    
    public SongService(SongRepository songRepository, SongMetadataParser parser) {
        this.songRepository = songRepository;
        this.parser = parser;
    }

    public List<Song> getAllSongs() {
        return songRepository.findAll();
    }

    public long getSongCount() {
        return songRepository.count();
    }
    
    public boolean isSong(byte[] songBytes) throws IOException, SAXException, TikaException {
    	return parser.isAudio(songBytes);
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
    public Song addSong(byte[] songBytes, String fileName) throws Exception {
        if (songBytes == null || songBytes.length == 0)
        	return null;
        
    	Song song = new Song();
		song = parser.parseSongMetadata(songBytes, song);
		if(song.getArtist() == null || song.getTitle() == null)
			throw new IOException("Данные об исполнителе и названии обязательны");
		
		fileName = saveAudioFile(songBytes, fileName);
		String musicUrl = musicConfig.getMusicUrl();
		song.setFilename(musicUrl + fileName);
		song.setAddedDate(LocalDate.now());
		
        songRepository.save(song);
        return song;
    }
    
    private String saveAudioFile(byte[] songBytes, String fileName) throws IOException {
      String musicPath = musicConfig.getMusicPath();
      Path uploadPath = Paths.get(musicPath);
      // Создаём директорию, если не существует
      if (!Files.exists(uploadPath)) {
          Files.createDirectories(uploadPath);
      }

      // Генерируем уникальное имя файла
      String type = fileName.substring(fileName.lastIndexOf("."));
      String newFilename = System.currentTimeMillis() + AUDIO_SUFFIX + type;
      
      // Сохраняем файл
      Path filePath = uploadPath.resolve(newFilename);
      Files.write(filePath, songBytes);
      
      return newFilename;
  }
}
