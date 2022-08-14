package moviebuddy.cache;

import moviebuddy.domain.MovieReader;
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.Pointcut;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.util.ClassUtils;

import java.util.Objects;

public class CachingAdvice implements MethodInterceptor {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final CacheManager cacheManager;
    public CachingAdvice(CacheManager cacheManager) {
        this.cacheManager = Objects.requireNonNull(cacheManager);
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Cache cache = cacheManager.getCache(invocation.getThis().getClass().getName());
        Object cachedValue = cache.get(invocation.getMethod().getName(), Object.class);
        if(Objects.nonNull(cachedValue)){
            logger.info("returns cached data , [{}]",invocation);
            return cachedValue;
        }

        cachedValue = invocation.proceed();
        cache.put(invocation.getMethod().getName(),cachedValue);
        logger.info("caching return value [{}]" , invocation);
        return cachedValue;
    }
}
