package micronaut_test_music.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import micronaut_test_music.MusicConfig;
import micronaut_test_music.model.Song;
import micronaut_test_music.repo.SongRepository;

@MicronautTest
public class SongServiceTest {
	@Inject
	private MusicConfig musicConfig;
	
	@Inject
    SongService songService;
	@Inject
	SongRepository songRepository;
	
	@BeforeEach
	void removeAudioBefore() throws IOException {
		songRepository.deleteAll();
	}
	
	@Test
    void testSave() throws Exception {
		ClassLoader cl = getClass().getClassLoader();
    	try(InputStream isSong = cl.getResourceAsStream(SongParserTest.TEST_SONG)) {
    		Assertions.assertEquals(0, songRepository.count());
    		
    		Song song = songService.addSong(isSong.readAllBytes(), "my_test.mp3");
    		String filename = song.getFilename();
    		
    		Assertions.assertNotNull(filename);
    		System.out.println(filename);
    		String musicUrl = musicConfig.getMusicUrl();
    		Assertions.assertTrue(filename.matches(musicUrl + "\\d+" + SongService.AUDIO_SUFFIX + ".mp3"));
    		
    		String musicPath = musicConfig.getMusicPath();
    		String filePath = musicPath + "/" + filename.substring(filename.lastIndexOf('/')+1);
    		System.out.println(filePath);
    		Path uploadPath = Paths.get(filePath);
    		Assertions.assertTrue(Files.exists(uploadPath));
    		
    		Assertions.assertEquals(1, songRepository.count());
    	}
	}
	
	@AfterEach
	void removeAudio() throws IOException {
		String musicPath = musicConfig.getMusicPath();
		Path uploadPath = Paths.get(musicPath);
		if(!Files.exists(uploadPath))
			return;
		
		Files.walk(uploadPath)
			.sorted(Comparator.reverseOrder()) // удаляем сначала вложенные файлы/папки, если есть
			.forEach(path -> {
				try {
					Files.delete(path);
				} catch (IOException e) {
					throw new RuntimeException("Failed to delete " + path, e);
				}
			});
		songRepository.deleteAll();
	}

}
