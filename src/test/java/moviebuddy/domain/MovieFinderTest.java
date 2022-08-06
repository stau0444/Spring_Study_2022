package moviebuddy.domain;
import moviebuddy.Movie;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author springrunner.kr@gmail.com
 */
public class MovieFinderTest {
	final MovieBuddyFactory factory = new MovieBuddyFactory();
	final MovieFinder movieFinder = factory.movieFinder();

	@Test
	void NotEmpty_directedBy(){

		List<Movie> result = movieFinder.directedBy("Michael Bay");
		assertEquals(3, result.size());

	}
	@Test
	void NotEmpty_releasedYearBy(){


		List<Movie> result = movieFinder.releasedYearBy(2015);
		assertEquals(225, result.size());
	}
}
