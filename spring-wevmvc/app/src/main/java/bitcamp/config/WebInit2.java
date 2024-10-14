package bitcamp.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import org.springframework.web.context.WebApplicationContext;

public class WebInit2 /*extends AbstractContextLoaderInitializer*/ {

  //@Override
  protected WebApplicationContext createRootApplicationContext() {

    // ContextLoaderListener에 사용할 IoC 컨테이너를 리턴한다
    //만약 IoC 컨테이너를 리턴하지 않으면
    //ContextLoaderListener는 생성하지 않는다
    return null;
  }

  //@Override
  public void onStartup(ServletContext ctx) throws ServletException {
    System.out.println("웹 init2에 onStart호출이 됨");
    /*
    //기존의 onStartUp메서드의 기능은 그대로 수행한다
    super.onStartup(ctx);

    // DispatcherServlet의 IoC 컨테이너 생성
    AnnotationConfigWebApplicationContext iocContainer =
        new AnnotationConfigWebApplicationContext();
    iocContainer.setServletContext(ctx);
    iocContainer.register(bitcamp.AppConfig.class);

    // DispatcherServlet 객체 생성
    DispatcherServlet frontController = new DispatcherServlet(iocContainer);

    // 서블릿 컨테이너에 등록
    Dynamic options = ctx.addServlet("app", frontController);

    // 서블릿 설정
    options.addMapping("/app/*");
     */

  }


}
