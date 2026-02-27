package micronaut_test_music.service;

import java.util.ArrayList;
import java.util.List;

import jakarta.inject.Singleton;
import micronaut_test_music.model.Song;

@Singleton
public class SongService {
    private final List<Song> songs = new ArrayList<>();
    private Long nextId = 1L;

    public SongService() {
        songs.add(new Song(nextId++, "Bohemian Rhapsody", "Queen", "bohemian.mp3"));
        songs.add(new Song(nextId++, "Imagine", "John Lennon", "imagine.mp3"));
        songs.add(new Song(nextId++, "Hotel California", "Eagles", "hotel.mp3"));
        songs.add(new Song(nextId++, "Stairway to Heaven", "Led Zeppelin", "stairway.mp3"));
        songs.add(new Song(nextId++, "Smells Like Teen Spirit", "Nirvana", "smells.mp3"));
    }

    public List<Song> getAllSongs() {
        return songs;
    }
}
