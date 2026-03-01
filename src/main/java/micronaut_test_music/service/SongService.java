package micronaut_test_music.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.inject.Singleton;
import micronaut_test_music.model.Song;

@Singleton
public class SongService {
    private final List<Song> songs = new ArrayList<>();
    private Long nextId = 1L;

    public SongService() {
    	// тест воспроизведения
        songs.add(new Song(nextId++, "Shine Like The Sun", "Luke Bergs", "/audio/Luke-Bergs-Shine-Like-The-Sun(chosic.com).mp3", 
            355, LocalDate.of(2026, 2, 15)));
        
        // Рок-классика (70-80е)
        songs.add(new Song(nextId++, "Stairway to Heaven", "Led Zeppelin", "stairway.mp3", 
            482, LocalDate.of(2026, 2, 16)));
        songs.add(new Song(nextId++, "Hotel California", "Eagles", "hotel.mp3", 
            391, LocalDate.of(2026, 2, 17)));
        songs.add(new Song(nextId++, "Back in Black", "AC/DC", "backinblack.mp3", 
            255, LocalDate.of(2026, 2, 18)));
        songs.add(new Song(nextId++, "Sweet Child O' Mine", "Guns N' Roses", "sweetchild.mp3", 
            356, LocalDate.of(2026, 2, 19)));
        
        // Поп-музыка
        songs.add(new Song(nextId++, "Billie Jean", "Michael Jackson", "billie.mp3", 
            294, LocalDate.of(2026, 2, 20)));
        songs.add(new Song(nextId++, "Like a Prayer", "Madonna", "prayer.mp3", 
            321, LocalDate.of(2026, 2, 21)));
        songs.add(new Song(nextId++, "Shape of You", "Ed Sheeran", "shapeofyou.mp3", 
            233, LocalDate.of(2026, 2, 22)));
        songs.add(new Song(nextId++, "Blinding Lights", "The Weeknd", "blinding.mp3", 
            200, LocalDate.of(2026, 2, 23)));
        songs.add(new Song(nextId++, "Bad Guy", "Billie Eilish", "badguy.mp3", 
            194, LocalDate.of(2026, 2, 24)));
        
        // Классика рока и фолка
        songs.add(new Song(nextId++, "Imagine", "John Lennon", "imagine.mp3", 
            183, LocalDate.of(2026, 2, 25)));
        songs.add(new Song(nextId++, "Hey Jude", "The Beatles", "heyjude.mp3", 
            431, LocalDate.of(2026, 2, 26)));
        songs.add(new Song(nextId++, "Like a Rolling Stone", "Bob Dylan", "rolling.mp3", 
            373, LocalDate.of(2026, 2, 27)));
        songs.add(new Song(nextId++, "Wish You Were Here", "Pink Floyd", "wish.mp3", 
            334, LocalDate.of(2026, 2, 28)));
        songs.add(new Song(nextId++, "Smells Like Teen Spirit", "Nirvana", "smells.mp3", 
            301, LocalDate.of(2026, 3, 1)));
        
        // R&B и Соул
        songs.add(new Song(nextId++, "Superstition", "Stevie Wonder", "superstition.mp3", 
            245, LocalDate.of(2026, 3, 2)));
        songs.add(new Song(nextId++, "I Feel Good", "James Brown", "feelgood.mp3", 
            165, LocalDate.of(2026, 3, 3)));
        songs.add(new Song(nextId++, "Respect", "Aretha Franklin", "respect.mp3", 
            147, LocalDate.of(2026, 3, 4)));
        songs.add(new Song(nextId++, "Let's Stay Together", "Al Green", "staytogether.mp3", 
            198, LocalDate.of(2026, 3, 5)));
        songs.add(new Song(nextId++, "Sexual Healing", "Marvin Gaye", "healing.mp3", 
            240, LocalDate.of(2026, 3, 6)));
        
        // Электроника и новые жанры
        songs.add(new Song(nextId++, "Around the World", "Daft Punk", "aroundworld.mp3", 
            404, LocalDate.of(2026, 3, 7)));
        songs.add(new Song(nextId++, "Sandstorm", "Darude", "sandstorm.mp3", 
            225, LocalDate.of(2026, 3, 8)));
        songs.add(new Song(nextId++, "Titanium", "David Guetta ft. Sia", "titanium.mp3", 
            245, LocalDate.of(2026, 3, 9)));
        songs.add(new Song(nextId++, "Levels", "Avicii", "levels.mp3", 
            339, LocalDate.of(2026, 3, 10)));
        songs.add(new Song(nextId++, "Animals", "Martin Garrix", "animals.mp3", 
            303, LocalDate.of(2026, 3, 11)));
    }

    public List<Song> getAllSongs() {
        return songs;
    }
}
