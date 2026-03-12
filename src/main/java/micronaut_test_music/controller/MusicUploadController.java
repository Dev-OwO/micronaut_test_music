package micronaut_test_music.controller;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import io.micronaut.views.View;
import jakarta.validation.Valid;
import micronaut_test_music.dto.SongUploadDto;
import micronaut_test_music.model.Song;
import micronaut_test_music.service.SongService;

//import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Controller
public class MusicUploadController {
    
    private static final Logger log = LoggerFactory.getLogger(MusicUploadController.class);
    private static final String AUDIO_DIR = "/tmp/micronaut_test_music";
    
    private final SongService songService;

    public MusicUploadController(SongService songService) {
        this.songService = songService;
    }

    @Get("/add_music")
    @View("add_music")
    public Map<String, Object> showUploadForm() {
        Map<String, Object> model = new HashMap<>();
        model.put("pageTitle", "Загрузить музыку");
        return model;
    }

    @Post(value = "/add_music", consumes = MediaType.MULTIPART_FORM_DATA)
    @View("add_music")
    public HttpResponse<?> uploadMusic(@Valid @Body SongUploadDto uploadDto) {
        try {
            // 1. Сохраняем файл
            String filename = saveAudioFile(uploadDto);
            
            // 2. Создаём песню
            Song song = new Song(
                uploadDto.getTitle(),
                uploadDto.getArtist(),
                "/audio/" + filename,
                180, // временно ставим длительность 3:00, потом научимся читать из mp3
                LocalDate.now()
            );
            
            // 3. Сохраняем в БД
            songService.addSong(song);
            
            log.info("Песня успешно загружена: {} - {}", uploadDto.getArtist(), uploadDto.getTitle());
            
            return HttpResponse.redirect(Paths.get("/playlist").toUri());
            
        } catch (Exception e) {
            log.error("Ошибка при загрузке песни", e);
            
            Map<String, Object> model = new HashMap<>();
            model.put("error", "Ошибка при загрузке: " + e.getMessage());
            model.put("pageTitle", "Загрузить музыку");
            
            return HttpResponse.badRequest().body(model);
        }
    }

    private String saveAudioFile(SongUploadDto uploadDto) throws IOException {
        // Создаём директорию, если не существует
        Path uploadPath = Paths.get(AUDIO_DIR);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Генерируем уникальное имя файла
        String originalFilename = uploadDto.getFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String newFilename = System.currentTimeMillis() + "_" + originalFilename;
        
        // Сохраняем файл
        Path filePath = uploadPath.resolve(newFilename);
        Files.write(filePath, uploadDto.getFileData());
        
        return newFilename;
    }
}