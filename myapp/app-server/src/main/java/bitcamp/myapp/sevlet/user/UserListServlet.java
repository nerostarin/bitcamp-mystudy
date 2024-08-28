package bitcamp.myapp.sevlet.user;

import bitcamp.myapp.dao.UserDao;
import bitcamp.myapp.vo.User;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/user/list")
public class UserListServlet implements Servlet {

    private UserDao userDao;
    private ServletConfig config;

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        //웹 브라우저에서 이 서블릿을 실행해 달라고 요청이 들어오면 이 메서드가 호출이 된다
        //누가 호출하는가? 서블릿 컨테이너가 호출한다

        //출력할 콘텐트의 타입을 먼저 지정한 후 출력 스트림을 얻는다
        res.setContentType("text/html; charset=UTF-8");

        //출력 콘텐츠를   어떤 문자 집합으로 인코딩을 할지 지정하지 않고 출력 스트림을 꺼내면
        //출력 문자열(UTF-16BE)은 ISO-8859-1 문자집합으로 인코딩이 된다
        PrintWriter out = res.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<title>Title</title>");
        out.println("<link href='/css/common.css' rel='stylesheet'>");
        out.println("</head>");
        out.println("<body>");
        out.println("<header>");
        out.println("<a href=' / '><img src='/images/home.png' style='vertical-align:middle;'></a>프로젝트 관리 시스템");
        out.println("</header>");
        try {
            out.println("<h1>[회원 목록]</h1>");
            out.println("<p><a href ='/user/form.html'>새 회원</a></p>");
            out.println("<table");
            out.println("<thead>");
            out.println("<tr><th>번호</th><th>이름</th><th>이메일</th></tr>");
            out.println("</thead>");
            out.println("<tbody>");
            for (User user : userDao.list()) {
                out.printf("<tr><td>%d</td><td><a href='/user/view?no=%1$d'>%s</a></td><td>%s</td></tr>\n", user.getNo(), user.getName(), user.getEmail());
            }
            out.println("</tbody>");
            out.println("</table>");
        } catch (Exception e) {
            out.println("목록 조회 중 오류 발생!");
            e.printStackTrace();
        }
        out.println("</body>");
        out.println("</html>");
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        //서블릿 객체를 생성한 후 바로 호출됨 (물론 , 생성자가 먼저 호출이 된다)
        //서블릿이 작업할 사용할 의존 객체를 준비하는 일을 이 메서드가 수행한다
        this.config = config;
        ServletContext ctx = config.getServletContext();
        userDao = (UserDao) ctx.getAttribute("userDao");
    }

    @Override
    public void destroy() {
        //서블릿 컨테이너가 종료되기 전에 해제할 자우너이 있다면 이 메서드에서 수행한다
    }

    @Override
    public String getServletInfo() {
        //서블릿 컨테이너 관리 화면에서 서블릿을 정보를 출력할 때 이 메서드가 호출이된다
        //간단히서블릿에 대한 정보를 문자열로 리턴하면된다
        return "";
    }

    @Override
    public ServletConfig getServletConfig() {
        //내부적으로 서블릿의 정보를 조회할 때 사용할 ServletConfig 객체를 리턴해준다
        // 이 메소드가 리턴할 ServletConfig객체는
        // init메소드가 호출 될 때 파라미터로 넘어온 객체를 잘 보관해 두었다가 리턴하면된다
        //따라서 init 메서드가 호출이 될 떄 ServletConfig 객체를 보관해 두어야 한다
        return null;
    }
}
