package moviebuddy.data;

import moviebuddy.MovieBuddyProfile;
import moviebuddy.domain.Movie;
import moviebuddy.domain.MovieBuddyFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.io.FileNotFoundException;

@ActiveProfiles(MovieBuddyProfile.CSV_MODE)
@SpringJUnitConfig(MovieBuddyFactory.class)
public class CsvMovieReaderTest {

    private final CsvMovieReader csvMovieReader;

    @Autowired
    public CsvMovieReaderTest(CsvMovieReader csvMovieReader) {
        this.csvMovieReader = csvMovieReader;
    }

    @Test
    void Valid_Metadata(){
        csvMovieReader.setMetadata("movie_metadata.csv");
        try {
            csvMovieReader.afterPropertiesSet();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void Invalid_Metadata(){

        Assertions.assertThrows(FileNotFoundException.class , ()->{
            csvMovieReader.setMetadata("wrong_meta.txt");
            csvMovieReader.afterPropertiesSet();
        });
    }
}
