package moviebuddy.domain;

import moviebuddy.Movie;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class MovieFinder {

    //메타데이터를 읽어오는 MovieReader의 읽기 전략을
    // MovieFinder 내부에서 결정함으로서
    // 독립적인 확장이 어렵다.
    // 해당 결정을 외부에서 결정할 필요가 있다.
    private final MovieReader movieReader;

    @Autowired
    public MovieFinder(MovieReader csvReader) {
        this.movieReader = Objects.requireNonNull(csvReader);
    }

    /**
     * 저장된 영화 목록에서 감독으로 영화를 검색한다.
     *
     * @param directedBy 감독
     * @return 검색된 영화 목록
     */
    public List<Movie> directedBy(String directedBy) {
        return movieReader.loadMovies().stream()
                .filter(it -> it.getDirector().toLowerCase().contains(directedBy.toLowerCase()))
                .collect(Collectors.toList());
    }

    /**
     * 저장된 영화 목록에서 개봉년도로 영화를 검색한다.
     *
     * @param releasedYearBy
     * @return 검색된 영화 목록
     */
    public List<Movie> releasedYearBy(int releasedYearBy) {
        return movieReader.loadMovies().stream()
                .filter(it -> Objects.equals(it.getReleaseYear(), releasedYearBy))
                .collect(Collectors.toList());
    }

}
