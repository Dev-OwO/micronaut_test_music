package micronaut_test_music.model;

public class Song {
    private Long id;
    private String title;
    private String artist;
    private String filename;  // путь к файлу или имя файла

    public Song(Long id, String title, String artist, String filename) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.filename = filename;
    }

    // Геттеры и сеттеры (обязательно для Thymeleaf!)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
