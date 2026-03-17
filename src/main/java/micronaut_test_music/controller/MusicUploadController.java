package micronaut_test_music.controller;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.DefaultParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.micronaut.core.annotation.Nullable;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Error;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Part;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.RequestBean;
import io.micronaut.http.multipart.CompletedFileUpload;
import io.micronaut.http.uri.UriBuilder;
import io.micronaut.views.View;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import micronaut_test_music.dto.SongUploadDto;
import micronaut_test_music.service.SongService;

@Controller
public class MusicUploadController {
    
    private static final Logger log = LoggerFactory.getLogger(MusicUploadController.class);
    private static final String AUDIO_DIR = "/tmp/micronaut_test_music";
    
    private final SongService songService;

    public MusicUploadController(SongService songService) {
        this.songService = songService;
    }

    // ----- с отдельными полями, без DTO
//    @Get("/add_music")
//    @View("add_music")
//    public Map<String, Object> showUploadForm() {
//        Map<String, Object> model = new HashMap<>();
//        model.put("pageTitle", "Загрузить музыку");
//        return model;
//    }
//    
//    @Post(value = "/add_music_api", consumes = MediaType.MULTIPART_FORM_DATA)
//	public HttpResponse<Object> uploadMusic(@Part("artist") String artist,
//		    @Part("title") String title, @Part("fileData") FileUpload songFile) {
//	    try {
//	        
//	        log.info("Песня успешно загружена: {} - {}", artist, title);
//	        
//	        return HttpResponse.redirect(UriBuilder.of("/playlist").build());
//	        
//	    } catch (Exception e) {
//	        log.error("Ошибка при загрузке песни", e);
//	        
//	        Map<String, Object> model = new HashMap<>();
//	        model.put("error", "Ошибка при загрузке: " + e.getMessage());
//	        model.put("pageTitle", "Загрузить музыку");
//	        
//	        return HttpResponse.redirect(Paths.get("/playlist").toUri());
//	    }
//	}
    
 // ----- с отдельными полями, DTO
    @Get("/add_music")
    @View("add_music_dto")
    public Map<String, Object> showUploadFormDto() {
        Map<String, Object> model = new HashMap<>();
        model.put("pageTitle", "Загрузить музыку");
//        model.put("song", new SongUploadDto());
        return model;
    }
    
    @Post(value = "/add_music_api_dto", consumes = MediaType.MULTIPART_FORM_DATA)
	public HttpResponse<Object> uploadMusicDto(@Part("songFile") @Nullable CompletedFileUpload songFile) {//,  (@Valid @Body SongUploadDto songUploadDto)
	    try {
	    	
	    	BodyContentHandler handler = new BodyContentHandler();
	        Metadata metadata = new Metadata();
	        ByteArrayInputStream bais = new ByteArrayInputStream(songFile.getBytes());
	        ParseContext pcontext = new ParseContext();
	        
	        //Mp3 parser
	        AutoDetectParser  Mp3Parser = new  org.apache.tika.parser.AutoDetectParser();
	        Mp3Parser.parse(bais, handler, metadata, pcontext);
	        
	        
	        log.info("Contents of the document:" + handler.toString());
	        log.info("Metadata of the document:");
	        String[] metadataNames = metadata.names();

	        for(String name : metadataNames) {		        
	        	log.info(name + ": " + metadata.get(name));
	        }
	    	
//	    	byte[] songBytes = songFile.getBytes();
//	    	log.info("Песня загружена: {}", songBytes == null ? 0 : songBytes.length);
//	    	SongParser mp = new SongParser();
//	    	Metadata m = mp.parseSongMetadata(songBytes);
//	    	log.info(String.join(", ", m.names()));
//	    	log.info(mp.getFirstKey("title", "dc:title", "TIKA_METADATA_TITLE"));
//	    	log.info(mp.getFirstKey("creator", "dc:creator", "author", "artist"));
//	    	log.info(mp.getFirstKey("album"));
//		    log.info(mp.getFirstKey("genre"));
//		    log.info(mp.getFirstKey("date", "year"));
//		    log.info(mp.getFirstKey("duration"));
//	    	
//	    	log.info("Песня успешно загружена: {} - {}", mp.getArtist(), mp.getTitle());
	        
	        return HttpResponse.redirect(UriBuilder.of("/playlist").build());
	        
	    } catch (Exception e) {
	        
	        return HttpResponse.redirect(UriBuilder.of("/playlist").build());
	    }
	}
    
    // обработчик ошибок валидации контроллера
    @Error(exception = ConstraintViolationException.class)
    @View("add_music_dto")
    public Map<String, Object> onValidationError(HttpRequest<?> request, ConstraintViolationException ex) {
        Map<String, Object> model = new HashMap<>();
        
        List<String> errors = ex.getConstraintViolations().stream()
        		.map(v -> v.getPropertyPath() + ": " + v.getMessage())
        		.collect(Collectors.toList());
        model.put("errors", String.join("; ", errors));
        
        request.getBody(SongUploadDto.class).ifPresent(song -> model.put("song", song));
        SongUploadDto s = (SongUploadDto)model.get("song");
        log.info("Песня есть: {} - {}", s.getArtist(), s.getTitle());
        return model;
    }

//    @Post(value = "/add_music_api", consumes = MediaType.MULTIPART_FORM_DATA)
//    public HttpResponse<Object> uploadMusic(@Part("artist") String artist,
//		    @Part("title") String title, @Part("fileData") FileUpload songFile) {
//        try {
//            // 1. Сохраняем файл
//            String filename = saveAudioFile(uploadDto);
//            
//            // 2. Создаём песню
//            Song song = new Song(
//                uploadDto.getTitle(),
//                uploadDto.getArtist(),
//                "/audio/" + filename,
//                180, // временно ставим длительность 3:00, потом научимся читать из mp3
//                LocalDate.now()
//            );
//            
//            // 3. Сохраняем в БД
//            songService.addSong(song);
//            
//            log.info("Песня успешно загружена: {} - {}", uploadDto.getArtist(), uploadDto.getTitle());
//            
//            return HttpResponse.redirect(UriBuilder.of("/playlist").build());
//            
//        } catch (Exception e) {
//            log.error("Ошибка при загрузке песни", e);
//            
//            Map<String, Object> model = new HashMap<>();
//            model.put("error", "Ошибка при загрузке: " + e.getMessage());
//            model.put("pageTitle", "Загрузить музыку");
//            
//            return HttpResponse.redirect(UriBuilder.of("/playlist").build());
////            return HttpResponse.badRequest().body(model);
//        }
//    }

//    private String saveAudioFile(SongUploadDto uploadDto) throws IOException {
//        // Создаём директорию, если не существует
//        Path uploadPath = Paths.get(AUDIO_DIR);
//        if (!Files.exists(uploadPath)) {
//            Files.createDirectories(uploadPath);
//        }
//
//        // Генерируем уникальное имя файла
//        String originalFilename = uploadDto.getFilename();
//        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
//        String newFilename = System.currentTimeMillis() + "_" + originalFilename;
//        
//        // Сохраняем файл
//        Path filePath = uploadPath.resolve(newFilename);
//        Files.write(filePath, uploadDto.getFileData());
//        
//        return newFilename;
//    }
}

class SongParser {
	private String title;
	private String artist;
	private Metadata metadata;
	
	public Metadata parseSongMetadata(byte[] songBytes) throws Exception {
	    metadata = new Metadata();
	    BodyContentHandler handler = new BodyContentHandler(-1);
	    
	    try(ByteArrayInputStream bais = new ByteArrayInputStream(songBytes)) {
		    Parser parser = new AutoDetectParser();
		    parser.parse(
		    		bais,
			        handler,
			        metadata,
			        new ParseContext()
			    );
	    }
	    title = getFirstKey("title", "dc:title", "TIKA_METADATA_TITLE", "xmpDM:trackName");
	    artist = getFirstKey("creator", "dc:creator", "author", "artist", "xmpDM:artist");
	    return metadata;
	}
	
	String getFirstKey(String... keys) {
	    for (String key : keys) {
	        String value = metadata.get(key);
	        if (value != null && !value.trim().isEmpty()) {
	            return value.trim();
	        }
	    }
	    return null;
	}
	
	String getTitle() {
		return title;
	}
	
	String getArtist() {
		return artist;
	}
}