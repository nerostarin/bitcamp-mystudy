package bitcamp.myapp.config;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration;
import java.io.File;

public class WebApplicationInitializerImpl extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        //공통 객체
        return new Class[]{RootConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{AppConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/app/*"};
    }

    @Override
    protected void customizeRegistration(ServletRegistration.Dynamic registration) {
        registration.setMultipartConfig(new MultipartConfigElement(//멀티 파트 설정
                new File("./temp").getAbsolutePath(),//업로드 파일을 임시 보관할 폴더
                1024 * 1024 * 60,
                1024 * 1024 * 100,
                1024 * 1024 * 1));
    }

    @Override
    protected Filter[] getServletFilters() {
        //DispatcherServlet에 붙일 핉터 객체
        return new Filter[]{new CharacterEncodingFilter("UTF-8")};
    }


}
