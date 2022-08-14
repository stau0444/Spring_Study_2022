package moviebuddy.domain;


import com.github.benmanes.caffeine.cache.Caffeine;
import moviebuddy.cache.CachingAdvice;
import moviebuddy.data.CachingMovieReader;
import org.aopalliance.aop.Advice;
import org.springframework.aop.Advisor;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import javax.cache.annotation.CacheResult;
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
        @Bean
        public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator(){
            return new DefaultAdvisorAutoProxyCreator();
        }

        //자동 프락시 생성을 위한 Advisor 빈
        @Bean
        public Advisor cachingAdvisor(CacheManager cacheManager){
            AnnotationMatchingPointcut pointcut = new AnnotationMatchingPointcut(null, CacheResult.class);
            Advice advice = new CachingAdvice(cacheManager);
            //Advisor = PointCut(대상선정) + Advice(부가기능)
            return new DefaultPointcutAdvisor(pointcut,advice);
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
