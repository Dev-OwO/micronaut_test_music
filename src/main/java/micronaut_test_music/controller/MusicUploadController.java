package micronaut_test_music.controller;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.apache.tika.exception.TikaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Part;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.http.multipart.CompletedFileUpload;
import io.micronaut.http.uri.UriBuilder;
import io.micronaut.views.View;
import micronaut_test_music.model.Song;
import micronaut_test_music.service.SongService;

@Controller
public class MusicUploadController {
    
    private static final Logger log = LoggerFactory.getLogger(MusicUploadController.class);
    
    private final SongService songService;

    public MusicUploadController(SongService songService) {
        this.songService = songService;
    }
    
    @Get("/add_music")
    @View("add_music")
    public Map<String, Object> showUploadFormDto(@QueryValue("error") Optional<String> errorParam) {
        Map<String, Object> model = new HashMap<>();
        model.put("pageTitle", "Загрузить музыку");
        errorParam.ifPresent(error -> model.put("errors", error));
        log.info("есть ли ошибка = " + errorParam.isPresent());
        return model;
    }
    
    @Post(value = "/add_music_api", consumes = MediaType.MULTIPART_FORM_DATA)
	public HttpResponse<Object> uploadMusicDto(@Part("songFile") CompletedFileUpload songFile) {
    	boolean isSave = false;
    	String error = null;
	    try {
	    	error = upload(songFile);
	    	if(error != null)
	    		log.error(error);
	    	isSave = error == null;
	    } catch (RuntimeException e) {
	    	error = e.getMessage();
	    	log.error(error, e);
	    } catch (Exception e) {
	    	error = "Неизвестная проблема при сохранении файла: " + e.getMessage();
	    	log.error(error, e);
		}
	    
	    if(isSave)
	    	return HttpResponse.redirect(UriBuilder.of("/playlist").build());
	    else {
	    	error = URLEncoder.encode(error, StandardCharsets.UTF_8);
	    	return HttpResponse.redirect(UriBuilder.of("/add_music?error=" + error).build());
	    }
	}
    
    private String upload(CompletedFileUpload songFile) throws SAXException, TikaException, Exception {
    	if(songFile == null) {
    		return "Отсутствует файл";
    	}
    	
    	byte[] fileBytes = songFile.getBytes();
    	if(fileBytes == null || fileBytes.length == 0) {
    		return "Отсутствует файл";
    	}
    	
    	if(!songService.isSong(fileBytes)) {
    		return "Переданный файл не является музыкой";
    	}
    	
    	Song newSong = songService.addSong(songFile.getBytes(), songFile.getFilename());
    	log.info("Песня успешно загружена: {} - {}", newSong.getArtist(), newSong.getTitle());
        
        return null;
    }
}
