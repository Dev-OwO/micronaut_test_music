package micronaut_test_music;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.micronaut.context.event.StartupEvent;
import io.micronaut.runtime.Micronaut;
import io.micronaut.runtime.event.annotation.EventListener;
import jakarta.inject.Singleton;
import micronaut_test_music.model.Song;
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
        List<Song> testSongs = createTestSongs();
        songService.addSongs(testSongs);
        log.info("Загружено {} тестовых песен", testSongs.size());
    }
    
    private List<Song> createTestSongs() {
    	List<Song> songList = new LinkedList<>();
    	// тест воспроизведения
    	songList.add(new Song("Shine Like The Sun", "Luke Bergs", "/audio/Luke-Bergs-Shine-Like-The-Sun(chosic.com).mp3", 
            355, LocalDate.of(2026, 2, 15)));
        
//        // Рок-классика (70-80е)
//        songList.add(new Song("Stairway to Heaven", "Led Zeppelin", "stairway.mp3", 
//            482, LocalDate.of(2026, 2, 16)));
//        songList.add(new Song("Hotel California", "Eagles", "hotel.mp3", 
//            391, LocalDate.of(2026, 2, 17)));
//        songList.add(new Song("Back in Black", "AC/DC", "backinblack.mp3", 
//            255, LocalDate.of(2026, 2, 18)));
//        songList.add(new Song("Sweet Child O' Mine", "Guns N' Roses", "sweetchild.mp3", 
//            356, LocalDate.of(2026, 2, 19)));
//        
//        // Поп-музыка
//        songList.add(new Song("Billie Jean", "Michael Jackson", "billie.mp3", 
//            294, LocalDate.of(2026, 2, 20)));
//        songList.add(new Song("Like a Prayer", "Madonna", "prayer.mp3", 
//            321, LocalDate.of(2026, 2, 21)));
//        songList.add(new Song("Shape of You", "Ed Sheeran", "shapeofyou.mp3", 
//            233, LocalDate.of(2026, 2, 22)));
//        songList.add(new Song("Blinding Lights", "The Weeknd", "blinding.mp3", 
//            200, LocalDate.of(2026, 2, 23)));
//        songList.add(new Song("Bad Guy", "Billie Eilish", "badguy.mp3", 
//            194, LocalDate.of(2026, 2, 24)));
//        
//        // Классика рока и фолка
//        songList.add(new Song("Imagine", "John Lennon", "imagine.mp3", 
//            183, LocalDate.of(2026, 2, 25)));
//        songList.add(new Song("Hey Jude", "The Beatles", "heyjude.mp3", 
//            431, LocalDate.of(2026, 2, 26)));
//        songList.add(new Song("Like a Rolling Stone", "Bob Dylan", "rolling.mp3", 
//            373, LocalDate.of(2026, 2, 27)));
//        songList.add(new Song("Wish You Were Here", "Pink Floyd", "wish.mp3", 
//            334, LocalDate.of(2026, 2, 28)));
//        songList.add(new Song("Smells Like Teen Spirit", "Nirvana", "smells.mp3", 
//            301, LocalDate.of(2026, 3, 1)));
//        
//        // R&B и Соул
//        songList.add(new Song("Superstition", "Stevie Wonder", "superstition.mp3", 
//            245, LocalDate.of(2026, 3, 2)));
//        songList.add(new Song("I Feel Good", "James Brown", "feelgood.mp3", 
//            165, LocalDate.of(2026, 3, 3)));
//        songList.add(new Song("Respect", "Aretha Franklin", "respect.mp3", 
//            147, LocalDate.of(2026, 3, 4)));
//        songList.add(new Song("Let's Stay Together", "Al Green", "staytogether.mp3", 
//            198, LocalDate.of(2026, 3, 5)));
//        songList.add(new Song("Sexual Healing", "Marvin Gaye", "healing.mp3", 
//            240, LocalDate.of(2026, 3, 6)));
//        
//        // Электроника и новые жанры
//        songList.add(new Song("Around the World", "Daft Punk", "aroundworld.mp3", 
//            404, LocalDate.of(2026, 3, 7)));
//        songList.add(new Song("Sandstorm", "Darude", "sandstorm.mp3", 
//            225, LocalDate.of(2026, 3, 8)));
//        songList.add(new Song("Titanium", "David Guetta ft. Sia", "titanium.mp3", 
//            245, LocalDate.of(2026, 3, 9)));
//        songList.add(new Song("Levels", "Avicii", "levels.mp3", 
//            339, LocalDate.of(2026, 3, 10)));
//        songList.add(new Song("Animals", "Martin Garrix", "animals.mp3", 
//            303, LocalDate.of(2026, 3, 11)));
        
        return songList;
    }
}