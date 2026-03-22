package micronaut_test_music.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
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
    public Map<String, Object> showUploadFormDto(@QueryValue("error") Optional<String> errorParam) {
        Map<String, Object> model = new HashMap<>();
        model.put("pageTitle", "Загрузить музыку");
        errorParam.ifPresent(error -> model.put("errors", error));
        log.info("есть ли ошибка = " + errorParam.isPresent());
        return model;
    }
    
    @Post(value = "/add_music_api_dto", consumes = MediaType.MULTIPART_FORM_DATA)
	public HttpResponse<Object> uploadMusicDto(@Part("songFile") CompletedFileUpload songFile) {//,  (@Valid @Body SongUploadDto songUploadDto)
    	boolean isSave = false;
    	String error = null;
	    try {
	    	error = upload(songFile);
	    	if(error != null)
	    		log.error(error);
	    	isSave = error == null;
	    } catch (IOException e) {
	    	error = "Проблема с чтением загруженного файла: " + e.getMessage();
	    	log.error(error, e);
	    } catch (Exception e) {
	    	error = "Проблема с распознанием метаданных файла: " + e.getMessage();
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
    
//    // обработчик ошибок валидации контроллера
//    @Error(exception = ConstraintViolationException.class)
//    @View("add_music_dto")
//    public Map<String, Object> onValidationError(HttpRequest<?> request, ConstraintViolationException ex) {
//        Map<String, Object> model = new HashMap<>();
//        
//        List<String> errors = ex.getConstraintViolations().stream()
//        		.map(v -> v.getPropertyPath() + ": " + v.getMessage())
//        		.collect(Collectors.toList());
//        model.put("errors", String.join("; ", errors));
//        
//        request.getBody(SongUploadDto.class).ifPresent(song -> model.put("song", song));
//        SongUploadDto s = (SongUploadDto)model.get("song");
//        log.info("Песня есть: {} - {}", s.getArtist(), s.getTitle());
//        return model;
//    }

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