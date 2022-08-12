package moviebuddy;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

public class CaffeineTests {

    @Test
    void useCache() throws InterruptedException {
        //캐시생성
        Cache<String,Object> cache = Caffeine
                .newBuilder()
                //만료시간 지정
                .expireAfterWrite(200, TimeUnit.MILLISECONDS)
                //최대개수 지정
                .maximumSize(100)
                .build();

        String key = "ugo";
        Object value = new Object();
        //캐시 객체에서 키로 값을 꺼내고 있다.
        Assertions.assertNull(cache.getIfPresent(key));

        //값 저장
        cache.put(key,value);
        Assertions.assertEquals(cache.getIfPresent(key),value);

        //200ms 미만
        TimeUnit.MILLISECONDS.sleep(100);
        Assertions.assertEquals(cache.getIfPresent(key),value);

        //200ms 초과
        TimeUnit.MILLISECONDS.sleep(100);
        Assertions.assertNull(cache.getIfPresent(key));
    }
}
