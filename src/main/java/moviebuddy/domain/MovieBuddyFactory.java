package moviebuddy.domain;


import moviebuddy.MovieBuddyProfile;
import moviebuddy.data.CsvMovieReader;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.*;
import org.springframework.oxm.Unmarshaller;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.ArrayList;

@Configuration
@ComponentScan(basePackages = "moviebuddy")
@Import({
        MovieBuddyFactory.DataSourceModuleConfig.class,
        MovieBuddyFactory.DomainModuleConfig.class
})
public class MovieBuddyFactory {

    /*
    메서드 콜 방식의 DI
    @Bean
    public MovieFinder movieFinder(){
        return new MovieFinder(movieReader());
    }
    */

    /*
    메서드 파라미터 방식의 DI
    - 스프링이 스스로 MovieReader가 bean으로 등록되어있는지 확인하고 주입해준다.
     */

    @Configuration
    public static class DomainModuleConfig{

    }
    @Configuration
    public static class DataSourceModuleConfig{
        @Profile(MovieBuddyProfile.CSV_MODE)
        @Bean
        public CsvMovieReader csvMovieReader() throws FileNotFoundException, URISyntaxException {
            CsvMovieReader csvMovieReader = new CsvMovieReader();
            csvMovieReader.setMetadata("movie_metadata.csv");
            return csvMovieReader;
        }
    }
    @Bean
    public Jaxb2Marshaller Jaxb2Marshaller(){
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        //지정한 패키지를 스캔하여 xml을 java 객체로 변환하는데 사용할 클래스를 탐색한다
        marshaller.setPackagesToScan("moviebuddy");
        return marshaller;
    }

}
