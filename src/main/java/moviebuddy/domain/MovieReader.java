package moviebuddy.domain;

import moviebuddy.Movie;

import java.util.List;


public interface MovieReader {
    public List<Movie> loadMovies();
}
