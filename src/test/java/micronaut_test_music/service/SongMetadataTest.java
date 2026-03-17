package micronaut_test_music.service;

import java.io.IOException;
import java.io.InputStream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import micronaut_test_music.model.Song;

@MicronautTest
public class SongMetadataTest {
	private static final String TEST_SONG = "static/audio/Luke-Bergs-Shine-Like-The-Sun(chosic.com).mp3";

    @Inject
    SongMetadataParser parser;

    @Test
    void testFile() throws IOException {
    	ClassLoader cl = getClass().getClassLoader();
    	try(InputStream isSong = cl.getResourceAsStream(TEST_SONG)) {
    		Assertions.assertTrue(isSong != null);
    		
    		byte[] buffer = new byte[4];
            int bytesRead = isSong.read(buffer);
            Assertions.assertTrue(bytesRead > 0);
    	}
    }
    
    @Test
    void testParseTestFile() throws Exception {
    	ClassLoader cl = getClass().getClassLoader();
    	try(InputStream isSong = cl.getResourceAsStream(TEST_SONG)) {
    		byte[] bytes = isSong.readAllBytes();
    		
//    		System.out.println(String.join("; ", parser.parseAllKeys(bytes)));
    		
    		Song song = new Song();
    		song = parser.parseSongMetadata(bytes, song);
    		
    		Assertions.assertEquals("Shine Like The Sun", song.getTitle());
    		Assertions.assertEquals("Luke Bergs", song.getArtist());
    		Assertions.assertEquals(158, song.getDuration());
    	}
    }
}
