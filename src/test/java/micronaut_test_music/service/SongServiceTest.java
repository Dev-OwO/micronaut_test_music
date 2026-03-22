package micronaut_test_music.service;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import micronaut_test_music.model.Song;

@MicronautTest
public class SongServiceTest {
	
	@Inject
    SongService songService;
	
	@Test
    void testSave() throws Exception {
		ClassLoader cl = getClass().getClassLoader();
    	try(InputStream isSong = cl.getResourceAsStream(SongParserTest.TEST_SONG)) {
    		Song song = songService.addSong(isSong.readAllBytes(), "my_test.mp3");
    		String filename = song.getFilename();
    		
    		Assertions.assertNotNull(filename);
    		Assertions.assertTrue(filename.matches(SongService.AUDIO_DIR + "/\\d+" + SongService.AUDIO_SUFFIX + ".mp3"));
    		Path uploadPath = Paths.get(filename);
    		Assertions.assertTrue(Files.exists(uploadPath));
    	}
	}
	
	@AfterEach
	void removeAudio() {
		Path uploadPath = Paths.get(SongService.AUDIO_DIR);
		// TODO удалить файлы
	}

}
