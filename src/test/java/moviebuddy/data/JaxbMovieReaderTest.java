package moviebuddy.data;

import moviebuddy.domain.Movie;
import moviebuddy.domain.MovieBuddyFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;

@SpringJUnitConfig(MovieBuddyFactory.class)
public class JaxbMovieReaderTest{

    @Autowired
    JaxbMovieReader movieReader;

    @Test
    void NotEmpty_LoadedMovies(){

        List<Movie> movies = movieReader.loadMovies();
        Assertions.assertEquals(1375,movies.size());
    }
}