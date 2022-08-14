package moviebuddy.domain;

import moviebuddy.domain.Movie;

import javax.cache.annotation.CacheResult;
import java.io.IOException;
import java.util.List;
public interface MovieReader {
    @CacheResult(cacheName = "movies")
    public List<Movie> loadMovies() ;
}
