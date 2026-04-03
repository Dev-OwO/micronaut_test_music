package micronaut_test_music.repo;

import java.util.List;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;
import micronaut_test_music.model.Song;

@Repository
public interface SongRepository extends JpaRepository<Song, Long> {

	@Override
	List<Song> findAll();
	
	List<Song> findByTitleIgnoreCaseAndArtistIgnoreCase(String title, String artist);
}
