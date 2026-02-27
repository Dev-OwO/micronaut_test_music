package micronaut_test_music.controller;

import java.util.HashMap;
import java.util.Map;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.views.View;
import micronaut_test_music.service.SongService;

@Controller
public class PlaylistController {
    
    private final SongService songService;

    public PlaylistController(SongService songService) {
        this.songService = songService;
    }

    @Get("/playlist")
    @View("playlist")
    public Map<String, Object> playlist() {
        Map<String, Object> model = new HashMap<>();
        model.put("songs", songService.getAllSongs());
        model.put("pageTitle", "Плейлист VplaY");
        return model;
    }
}
