package moviebuddy.domain;


import com.github.benmanes.caffeine.cache.Caffeine;
import moviebuddy.cache.CachingAdvice;
import moviebuddy.cache.CachingAspect;
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
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.cache.interceptor.*;
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
//@EnableAspectJAutoProxy
@EnableCaching
public class MovieBuddyFactory {

    @Configuration
    public static class DomainModuleConfig{

    }
    @Configuration
    public static class DataSourceModuleConfig implements CachingConfigurer {
        @Bean
        public CaffeineCacheManager caffeineCacheManager(){
            CaffeineCacheManager cacheManager = new CaffeineCacheManager();
            cacheManager.setCaffeine(Caffeine.newBuilder().expireAfterWrite(3,TimeUnit.SECONDS));
            return cacheManager;
        }

        @Override
        public CacheManager cacheManager() {
            return caffeineCacheManager();
        }

        @Override
        public CacheResolver cacheResolver() {
            return new SimpleCacheResolver(caffeineCacheManager());
        }

        @Override
        public KeyGenerator keyGenerator() {
            return new SimpleKeyGenerator();
        }

        @Override
        public CacheErrorHandler errorHandler() {
            return new SimpleCacheErrorHandler();
        }
//        @Bean
//        public CachingAspect cachingAspect(CacheManager cacheManager){
//            return new CachingAspect(cacheManager);
//        }
//        @Bean
//        public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator(){
//            return new DefaultAdvisorAutoProxyCreator();
//        }
//
//        //?????? ????????? ????????? ?????? Advisor ???
//        @Bean
//        public Advisor cachingAdvisor(CacheManager cacheManager){
//            AnnotationMatchingPointcut pointcut = new AnnotationMatchingPointcut(null, CacheResult.class);
//            Advice advice = new CachingAdvice(cacheManager);
//            //Advisor = PointCut(????????????) + Advice(????????????)
//            return new DefaultPointcutAdvisor(pointcut,advice);
//        }
    }
    @Bean
    public Jaxb2Marshaller Jaxb2Marshaller(){
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        //????????? ???????????? ???????????? xml??? java ????????? ??????????????? ????????? ???????????? ????????????
        marshaller.setPackagesToScan("moviebuddy");
        return marshaller;
    }

}
