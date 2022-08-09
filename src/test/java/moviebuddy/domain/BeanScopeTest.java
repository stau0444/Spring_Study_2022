package moviebuddy.domain;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class BeanScopeTest {

    @Test
    void Equals_MovieFinderBean(){
        ApplicationContext ctx = new AnnotationConfigApplicationContext(MovieBuddyFactory.class);
        MovieFinder movieFinder = ctx.getBean(MovieFinder.class);
        MovieFinder movieFinder2 = ctx.getBean(MovieFinder.class);

        Assertions.assertNotEquals(movieFinder,movieFinder2);
    }
}


