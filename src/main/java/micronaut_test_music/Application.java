package micronaut_test_music;

import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.micronaut.context.event.StartupEvent;
import io.micronaut.runtime.Micronaut;
import io.micronaut.runtime.event.annotation.EventListener;
import jakarta.inject.Singleton;
import micronaut_test_music.service.SongService;

public class Application {

    public static void main(String[] args) {
        Micronaut.run(Application.class, args);
    }
}

@Singleton
class StartInitializer {
	private static final Logger log = LoggerFactory.getLogger(StartInitializer.class);
    private final SongService songService;

    public StartInitializer(SongService songService) {
        this.songService = songService;
    }

    @EventListener
    public void init(StartupEvent event) {
        log.info("Проверка наличия тестовых песен...");
        
        if (songService.getSongCount() > 0) {
        	log.info("База данных уже содержит {} песен", songService.getSongCount());
        	return;
        }
        
        log.info("База данных пуста. Загружаем тестовые песни...");
        
        ClassLoader cl = getClass().getClassLoader();
        try {
        	loadSong("static/audio/Luke-Bergs-Shine-Like-The-Sun(chosic.com).mp3", cl);
        } catch (Exception e) {
        	log.error("Не удалось загрузить тестовую музыку", e);
		}
        
        log.info("Загружено {} тестовых песен", songService.getSongCount());
    }
    
    private void loadSong(String songPath, ClassLoader cl) throws Exception {
    	String fileName = songPath.substring(songPath.lastIndexOf('/')+1);
    	try(InputStream isSong = cl.getResourceAsStream(songPath)) {
        	songService.addSong(isSong.readAllBytes(), fileName);
        }
    }
}