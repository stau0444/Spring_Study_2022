package moviebuddy.domain;


import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import moviebuddy.MovieBuddyProfile;
import moviebuddy.data.CsvMovieReader;
import moviebuddy.data.XmlMovieReader;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.EhcacheCoreInit;
import org.checkerframework.checker.units.qual.C;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.cache.ehcache.EhCacheCache;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheFactoryBean;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.oxm.Unmarshaller;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Configuration
@PropertySource("/application.properties")
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
        @Bean
        public CaffeineCacheManager ehCacheCacheManager(){
            CaffeineCacheManager cacheManager = new CaffeineCacheManager();
            cacheManager.setCaffeine(Caffeine.newBuilder().expireAfterWrite(3,TimeUnit.SECONDS));
            return cacheManager;
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
