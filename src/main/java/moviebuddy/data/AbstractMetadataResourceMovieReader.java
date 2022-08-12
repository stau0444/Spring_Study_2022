package moviebuddy.data;

import moviebuddy.ApplicationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.FileNotFoundException;

public abstract class AbstractMetadataResourceMovieReader implements ResourceLoaderAware {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private  ResourceLoader resourceLoader;
    private String metadata;

    public Resource getMedataResource(){
        return resourceLoader.getResource(getMetadata());
    }
    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public String getMetadata() {
        return metadata;
    }
    @Value("${movie.metadata}")
    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    @PostConstruct
    public void afterPropertiesSet() throws Exception {
        //클래스패스 경로상의 자원을 읽어들인다.
        // 파일 혹은 네트워크 상에서의 자원을 읽으려한다면
        // 프로토콜에 따른 URL 생성 방식을 다르게 구현해야 한다.
        Resource resource = getMedataResource();
        if(!resource.exists()){
            throw new FileNotFoundException();
        }
        if(!resource.isReadable()){
            throw new ApplicationException(String.format("cannot read to metadata. [%s]", metadata));
        }
        logger.info(resource + " : resource is Ready");
    }

    @PreDestroy
    public void destroy() throws Exception {
        logger.info("Destroyed bean");
    }
}
