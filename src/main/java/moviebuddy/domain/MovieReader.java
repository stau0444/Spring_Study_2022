package moviebuddy.domain;

import moviebuddy.domain.Movie;

import java.io.IOException;
import java.util.List;

public interface MovieReader {
    public List<Movie> loadMovies() ;
}
