package moviebuddy.domain;


import com.github.benmanes.caffeine.cache.Caffeine;
import moviebuddy.data.CachingMovieReader;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.*;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import java.util.concurrent.TimeUnit;

@Configuration
@PropertySource("/application.properties")
@ComponentScan(basePackages = "moviebuddy")
@Import({
        MovieBuddyFactory.DataSourceModuleConfig.class,
        MovieBuddyFactory.DomainModuleConfig.class
})
public class MovieBuddyFactory {

    @Configuration
    public static class DomainModuleConfig{

    }
    @Configuration
    public static class DataSourceModuleConfig{
        @Bean
        public CaffeineCacheManager ehCacheCacheManager(){
            CaffeineCacheManager cacheManager = new CaffeineCacheManager();
            cacheManager.setCaffeine(Caffeine.newBuilder().expireAfterWrite(3,TimeUnit.SECONDS));
            return cacheManager;
        }

        @Primary
        @Bean
        public MovieReader cachingMovieReader(CacheManager cacheManager , MovieReader target){
            return  new CachingMovieReader(cacheManager,target);
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
