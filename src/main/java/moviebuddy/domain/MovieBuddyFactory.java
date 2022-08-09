package moviebuddy.domain;


import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Scope;

@Configuration
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
    static class DataSourceModuleConfig{
        @Bean
        public MovieReader movieReader(){
            return new CsvMovieReader();
        }
    }
    @Configuration
    static class DomainModuleConfig{
        @Bean
        @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
        public MovieFinder movieFinder(MovieReader movieReader){
            return new MovieFinder(movieReader);
        }

    }
}
