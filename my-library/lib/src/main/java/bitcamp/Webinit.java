package bitcamp;

import javax.servlet.ServletContext;

//서블릿 컨테이너가 실행이 될때 초기화 작업을 담당할 객체의 사용 규칙 정의
public interface Webinit {
  void start(ServletContext ctx);
}
