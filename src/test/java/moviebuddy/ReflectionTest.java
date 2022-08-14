package moviebuddy;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectionTest {

    @Test
    void objectCreateAndMethodCall() throws Exception{
        //without reflection
        Duck duck = new Duck();
        duck.quack();

        //with reflection
        Class<?> duckClass = Class.forName("moviebuddy.ReflectionTest$Duck");
        Object duckObject = duckClass.getDeclaredConstructor().newInstance();
        Method quackMethod = duckObject.getClass().getDeclaredMethod("quack", new Class<?>[0]);
        quackMethod.invoke(duckObject);
    }
    static class Duck{
        void quack(){
            System.out.println("꽥 꽥");
        }
    }
}
