package moviebuddy.domain;
import moviebuddy.Movie;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author springrunner.kr@gmail.com
 */
public class MovieFinderTest {

	@Test
	void find_movie(){
		MovieReader movieReader = new JaxbMovieReader();
		MovieFinder movieFinder = new MovieFinder(movieReader);

		List<Movie> result = movieFinder.directedBy("Michael Bay");
		assertEquals(3, result.size());

		result = movieFinder.releasedYearBy(2015);
		assertEquals(225, result.size());
	}



}
