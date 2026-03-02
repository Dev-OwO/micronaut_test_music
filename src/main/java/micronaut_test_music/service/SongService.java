package micronaut_test_music.service;

import java.time.LocalDate;
import java.util.List;

import jakarta.inject.Singleton;
import jakarta.transaction.Transactional;
import micronaut_test_music.model.Song;
import micronaut_test_music.repo.SongRepository;

@Singleton
public class SongService {
	private final SongRepository songRepository;
    
    public SongService(SongRepository songRepository) {
        this.songRepository = songRepository;
        
        // Проверяем, есть ли данные в БД
        if (songRepository.count() == 0) {
            initTestData();
        }
    }

    @Transactional
    void initTestData() {
    	// тест воспроизведения
    	songRepository.save(new Song("Shine Like The Sun", "Luke Bergs", "/audio/Luke-Bergs-Shine-Like-The-Sun(chosic.com).mp3", 
            355, LocalDate.of(2026, 2, 15)));
        
        // Рок-классика (70-80е)
        songRepository.save(new Song("Stairway to Heaven", "Led Zeppelin", "stairway.mp3", 
            482, LocalDate.of(2026, 2, 16)));
        songRepository.save(new Song("Hotel California", "Eagles", "hotel.mp3", 
            391, LocalDate.of(2026, 2, 17)));
        songRepository.save(new Song("Back in Black", "AC/DC", "backinblack.mp3", 
            255, LocalDate.of(2026, 2, 18)));
        songRepository.save(new Song("Sweet Child O' Mine", "Guns N' Roses", "sweetchild.mp3", 
            356, LocalDate.of(2026, 2, 19)));
        
        // Поп-музыка
        songRepository.save(new Song("Billie Jean", "Michael Jackson", "billie.mp3", 
            294, LocalDate.of(2026, 2, 20)));
        songRepository.save(new Song("Like a Prayer", "Madonna", "prayer.mp3", 
            321, LocalDate.of(2026, 2, 21)));
        songRepository.save(new Song("Shape of You", "Ed Sheeran", "shapeofyou.mp3", 
            233, LocalDate.of(2026, 2, 22)));
        songRepository.save(new Song("Blinding Lights", "The Weeknd", "blinding.mp3", 
            200, LocalDate.of(2026, 2, 23)));
        songRepository.save(new Song("Bad Guy", "Billie Eilish", "badguy.mp3", 
            194, LocalDate.of(2026, 2, 24)));
        
        // Классика рока и фолка
        songRepository.save(new Song("Imagine", "John Lennon", "imagine.mp3", 
            183, LocalDate.of(2026, 2, 25)));
        songRepository.save(new Song("Hey Jude", "The Beatles", "heyjude.mp3", 
            431, LocalDate.of(2026, 2, 26)));
        songRepository.save(new Song("Like a Rolling Stone", "Bob Dylan", "rolling.mp3", 
            373, LocalDate.of(2026, 2, 27)));
        songRepository.save(new Song("Wish You Were Here", "Pink Floyd", "wish.mp3", 
            334, LocalDate.of(2026, 2, 28)));
        songRepository.save(new Song("Smells Like Teen Spirit", "Nirvana", "smells.mp3", 
            301, LocalDate.of(2026, 3, 1)));
        
        // R&B и Соул
        songRepository.save(new Song("Superstition", "Stevie Wonder", "superstition.mp3", 
            245, LocalDate.of(2026, 3, 2)));
        songRepository.save(new Song("I Feel Good", "James Brown", "feelgood.mp3", 
            165, LocalDate.of(2026, 3, 3)));
        songRepository.save(new Song("Respect", "Aretha Franklin", "respect.mp3", 
            147, LocalDate.of(2026, 3, 4)));
        songRepository.save(new Song("Let's Stay Together", "Al Green", "staytogether.mp3", 
            198, LocalDate.of(2026, 3, 5)));
        songRepository.save(new Song("Sexual Healing", "Marvin Gaye", "healing.mp3", 
            240, LocalDate.of(2026, 3, 6)));
        
        // Электроника и новые жанры
        songRepository.save(new Song("Around the World", "Daft Punk", "aroundworld.mp3", 
            404, LocalDate.of(2026, 3, 7)));
        songRepository.save(new Song("Sandstorm", "Darude", "sandstorm.mp3", 
            225, LocalDate.of(2026, 3, 8)));
        songRepository.save(new Song("Titanium", "David Guetta ft. Sia", "titanium.mp3", 
            245, LocalDate.of(2026, 3, 9)));
        songRepository.save(new Song("Levels", "Avicii", "levels.mp3", 
            339, LocalDate.of(2026, 3, 10)));
        songRepository.save(new Song("Animals", "Martin Garrix", "animals.mp3", 
            303, LocalDate.of(2026, 3, 11)));
    }

    public List<Song> getAllSongs() {
        return songRepository.findAll();
    }
}
