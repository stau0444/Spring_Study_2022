package moviebuddy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class PropertiesTest {

    @Test
    void Load_PropertiesFile() throws IOException {
        //Properties 클래스는 외부의 파일을 load 하고 읽는데 사용된다.
        Properties config = new Properties();
        config.load(Files.newInputStream(Paths.get("./src/test/resources/config.properties")));
        Assertions.assertEquals(1,config.size());
        Assertions.assertEquals("arawn",config.get("name"));
    }
}
