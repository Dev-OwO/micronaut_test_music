package micronaut_test_music.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Song {
    private Long id;
    private String title;
    private String artist;
    private String filename;
    private Integer duration; // длительность в секундах
    private LocalDate addedDate;

    public Song(Long id, String title, String artist, String filename, Integer duration, LocalDate addedDate) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.filename = filename;
        this.duration = duration;
        this.addedDate = addedDate;
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
    
    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }
    
    public LocalDate getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(LocalDate addedDate) {
        this.addedDate = addedDate;
    }
    
    // Вспомогательный метод для форматирования длительности в MM:SS
    public String getFormattedDuration() {
        if (duration == null) return "0:00";
        int minutes = duration / 60;
        int seconds = duration % 60;
        return String.format("%d:%02d", minutes, seconds);
    }

    // Вспомогательный метод для форматирования даты
    public String getFormattedDate() {
        if (addedDate == null) return "";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return addedDate.format(formatter);
    }
}
