package bitcamp.config;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

public class WebInit3 /*extends AbstractDispatcherServletInitializer*/{

  //@Override
  protected WebApplicationContext createRootApplicationContext() {

    // ContextLoaderListener에 사용할 IoC 컨테이너를 리턴한다
    //만약 IoC 컨테이너를 리턴하지 않으면
    //ContextLoaderListener는 생성하지 않는다
    return null;
  }

  //@Override
  protected WebApplicationContext createServletApplicationContext() {

    // DisapatcherServlet이 사용할 IoC컨테이너를 리턴한다
    System.out.println("웹 init3에 createServletApplicationContext호출이 됨");

    AnnotationConfigWebApplicationContext iocContainer =
        new AnnotationConfigWebApplicationContext();
    //iocContainer.setServletContext(ctx);
    iocContainer.register(bitcamp.AppConfig.class);
    return iocContainer;
  }

  //@Override
  protected String[] getServletMappings() {
    return new String[] {"/app/*"};
  }

  //  @Override
  //  public void onStartup(ServletContext ctx) throws ServletException {
  //    System.out.println("웹 init3에 onStart호출이 됨");
  //
  //    //기존의 onStartUp메서드의 기능은 그대로 수행한다
  //    super.onStartup(ctx);
  //
  //  }

}
