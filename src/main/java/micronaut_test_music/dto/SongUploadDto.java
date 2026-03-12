package micronaut_test_music.dto;

import io.micronaut.core.annotation.Introspected;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Introspected
public class SongUploadDto {
    
    @NotBlank(message = "Название песни обязательно")
    private String title;
    
    @NotBlank(message = "Исполнитель обязателен")
    private String artist;
    
    @NotNull(message = "Файл песни обязателен")
    private byte[] fileData;
    
    private String filename;

    // Геттеры и сеттеры
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

    public byte[] getFileData() {
        return fileData;
    }

    public void setFileData(byte[] fileData) {
        this.fileData = fileData;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}