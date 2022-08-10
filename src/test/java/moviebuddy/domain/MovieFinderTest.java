package moviebuddy.domain;
import moviebuddy.MovieBuddyProfile;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author springrunner.kr@gmail.com
 */

//@ExtendWith,@ContextConfiguration 어노테이션을 대체 해준다
@SpringJUnitConfig(MovieBuddyFactory.class)
//Junit 테스트 실행 전략을 확장할 떄 사용하는 애노테이션
//SpringExtension : 스프링 테스트에서 제공하는 Junit 지원 클래스
//Junit 테스트에 필요한 스프링 컨테이너를 구성하고 관리한다.
//@ExtendWith(SpringExtension.class)
//테스트 컨텍스트의 빈구성정보를 지정하는 애노테이션
//@ContextConfiguration(classes = MovieBuddyFactory.class)
@ActiveProfiles(MovieBuddyProfile.CSV_MODE)
public class MovieFinderTest {

	@Autowired MovieFinder movieFinder;

//	@Autowired
//	public MovieFinderTest(MovieFinder movieFinder) {
//		this.movieFinder = movieFinder;
//	}

	@Test
	void NotEmpty_directedBy(){

		List<Movie> result = movieFinder.directedBy("Michael Bay");
		assertEquals(3, result.size());

	}
	@Test
	void NotEmpty_releasedYearBy(){

		List<Movie> result = movieFinder.releasedYearBy(2015);
		assertEquals(225, result.size());
	}
}
