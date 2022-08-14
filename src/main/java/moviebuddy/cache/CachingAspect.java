package moviebuddy.cache;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import javax.cache.annotation.CacheResult;
import java.util.Objects;

@Aspect
public class CachingAspect {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final CacheManager cacheManager;

    public CachingAspect(CacheManager cacheManager) {
        this.cacheManager = Objects.requireNonNull(cacheManager);
    }

    // 부가 기능 정의
    @Around("target(moviebuddy.domain.MovieReader)")
    public Object doCachingReturnValue(ProceedingJoinPoint pjp) throws Throwable {
        //캐시 데이터 있으면 , 캐시 데이터 즉시 반환
        Cache cache = cacheManager.getCache(pjp.getThis().getClass().getName());
        Object cachedValue = cache.get(pjp.getSignature().getName(), Object.class);
        if(Objects.nonNull(cachedValue)){
            log.info("returns cached data , [{}]" ,pjp);
            return cachedValue;
        }
        // 없으면 대상 객체에 명령을 위임 , 반환받은 값을 캐시 저장후 반
        cachedValue = pjp.proceed();
        cache.put(pjp.getSignature().getName(), cachedValue);
        log.info("caching return value,[{}]",pjp);
        return cachedValue;
    }


}
