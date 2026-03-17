package micronaut_test_music.service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;

import jakarta.inject.Singleton;
import micronaut_test_music.model.Song;

/**
 * Получение метаданных аудиофайлов
 */
@Singleton
class SongMetadataParser {
	
	public Song parseSongMetadata(byte[] songBytes, Song song) throws Exception {
		Metadata metadata = new Metadata();
	    BodyContentHandler handler = new BodyContentHandler(-1);
	    
	    try(InputStream is = new ByteArrayInputStream(songBytes)) {
		    Parser parser = new AutoDetectParser();
		    parser.parse(is, handler, metadata, new ParseContext());
	    }
	    String title = getFirstKey(metadata, "title", "dc:title", "TIKA_METADATA_TITLE", "xmpDM:trackName");
	    String artist = getFirstKey(metadata, "creator", "dc:creator", "author", "artist", "xmpDM:artist");
	    String duration = getFirstKey(metadata, "duration", "dc:duration", "xmpDM:duration");
	    
	    song.setTitle(title);
	    song.setArtist(artist);
	    if(duration != null) {
	    	double d = Double.parseDouble(duration);
	    	song.setDuration((int)d);
	    }
	    return song;
	}
	
	public String[] parseAllKeys(byte[] songBytes) throws Exception {
		Metadata metadata = new Metadata();
	    BodyContentHandler handler = new BodyContentHandler(-1);
	    
	    try(InputStream is = new ByteArrayInputStream(songBytes)) {
		    Parser parser = new AutoDetectParser();
		    parser.parse(is, handler, metadata, new ParseContext());
	    }
	    
	    return metadata.names();
	}
	
	private String getFirstKey(Metadata metadata, String... keys) {
	    for (String key : keys) {
	        String value = metadata.get(key);
	        if (value != null && !value.trim().isEmpty()) {
	            return value.trim();
	        }
	    }
	    return null;
	}
}
