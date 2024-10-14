package bitcamp.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class WebInit4 extends AbstractAnnotationConfigDispatcherServletInitializer{

  @Override
  protected String[] getServletMappings() {
    return new String[] {"/app/*"};
  }

  @Override
  protected Class<?>[] getRootConfigClasses() {
    // contextLoaderListener가 사용할
    // IoC컨테이너를 만들 때 주입하는 java 컨피그 클래스흫 리턴한다
    //만약 Ioc 설정 클래스를 리턴하지 않으면
    //IoC 컨테이너를 만들지 않을 것이고
    //IoC 컨테이너를 만들지 않으면
    //ContextLoaderListener생성하지 않을것이다
    return null;
  }

  @Override
  protected Class<?>[] getServletConfigClasses() {
    //DispatcherServlet이 사용할
    //IoC컨테이너를 등록을 한다
    return new Class<?>[] {bitcamp.AppConfig.class};
  }

}
