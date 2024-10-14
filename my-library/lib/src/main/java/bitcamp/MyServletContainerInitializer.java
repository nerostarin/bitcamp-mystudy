package bitcamp;

import java.util.Set;
import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.HandlesTypes;

@HandlesTypes(Webinit.class)
public class MyServletContainerInitializer implements ServletContainerInitializer{

  @Override
  public void onStartup(Set<Class<?>> classes, ServletContext ctx) throws ServletException {
    System.out.println("onStartUp(), 호출됨");

    if(classes == null) {
      return;
    }

    for(Class<?> clazz : classes) {
      try {
        Webinit webinit = (Webinit) clazz.getConstructor().newInstance();
        webinit.start(ctx);
        System.out.println(clazz.getName() + "클래스 호출됨");
      }catch (Exception e) {
        System.out.println(e.getMessage());
      }
    }

  }

}
