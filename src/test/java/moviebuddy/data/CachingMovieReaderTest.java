package moviebuddy.data;


import com.sun.xml.bind.v2.model.impl.ModelBuilder;
import com.sun.xml.bind.v2.model.impl.ModelBuilderI;
import moviebuddy.domain.Movie;
import moviebuddy.domain.MovieReader;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;

import java.util.ArrayList;
import java.util.List;

public class CachingMovieReaderTest {
    @Test
    void caching(){
        CacheManager cacheManager = new ConcurrentMapCacheManager();
        MovieReader target  = new DummyMovieReader();
        CachingMovieReader movieReader = new CachingMovieReader(cacheManager,target);

        Assertions.assertNull(movieReader.getCachedData());

        List<Movie> movies = movieReader.loadMovies();

        Assertions.assertNotNull(movieReader.getCachedData());
        //캐시된 데이터와 캐시에 넣은 데이터 비교
        Assertions.assertSame(movieReader.loadMovies(),movies);
    }
    class DummyMovieReader implements MovieReader{
        @Override
        public List<Movie> loadMovies() {
            return new ArrayList<>();
        }
    }
}
