package moviebuddy.domain;
import moviebuddy.Movie;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author springrunner.kr@gmail.com
 */
public class MovieFinderTest {
	final ApplicationContext applicationContext
			= new AnnotationConfigApplicationContext(MovieBuddyFactory.class);

	final MovieFinder movieFinder = applicationContext.getBean(MovieFinder.class);

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
