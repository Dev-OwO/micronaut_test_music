package micronaut_test_music.dto;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.http.multipart.CompletedFileUpload;
import io.micronaut.http.multipart.FileUpload;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Introspected
@Serdeable
public class SongUploadDto {
	@NotBlank(message = "Название обязательно")
	@QueryValue("title")
	private String title;
	@NotBlank(message = "Исполнитель обязателен")
	@QueryValue("artist")
	private String artist;
//	@NotNull(message = "Файл обязателен")
//	private CompletedFileUpload songFile;

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
    
//    public CompletedFileUpload getSongFile() {
//    	return songFile;
//    }
//
//    public void setSongFile(CompletedFileUpload songFile) {
//    	this.songFile = songFile;
//	}
}

//@Introspected
//@Serializable
//public class SongUploadDto {
//    
////    @NotBlank(message = "Название песни обязательно")
//	public String title;
//    
////    @NotBlank(message = "Исполнитель обязателен")
////	public String artist;
//    
////    @NotNull(message = "Файл песни обязателен")
////	public byte[] fileData;
//    
////    private String filename;
//    
//    public SongUploadDto() {}
//
//    // Геттеры и сеттеры
//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
////    public String getArtist() {
////        return artist;
////    }
////
////    public void setArtist(String artist) {
////        this.artist = artist;
////    }
////
////    public byte[] getFileData() {
////        return fileData;
////    }
////
////    public void setFileData(byte[] fileData) {
////        this.fileData = fileData;
////    }
//
////    public String getFilename() {
////        return filename;
////    }
////
////    public void setFilename(String filename) {
////        this.filename = filename;
////    }
//}
