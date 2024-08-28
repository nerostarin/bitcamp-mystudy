package bitcamp.myapp.sevlet.auth;

import bitcamp.myapp.dao.UserDao;
import bitcamp.myapp.vo.User;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/auth/login")
public class LoginServlet extends GenericServlet {

    private UserDao userDao;

    @Override
    public void init() throws ServletException {
        userDao = (UserDao) this.getServletContext().getAttribute("userDao");
    }

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html;charset=UTF-8");

        PrintWriter out = res.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("    <meta charset='UTF-8'>");
        out.println("    <title>Title</title>");
        out.println("<link href='/css/common.css' rel='stylesheet'>");
        out.println("</head>");
        out.println("<body>");
        out.println("<header>");
        out.println("<a href=' / '><img src='/images/home.png' style='vertical-align:middle;'></a>프로젝트 관리 시스템");
        out.println("</header>");
        try {
            out.println("<h1>로그인 결과</h1>");

            String email = req.getParameter("email");
            String password = req.getParameter("password");

            User user = userDao.findByEmailAndPassword(email, password);
            if (user == null) {
                out.println("<p>이메일 또는 암호가 맞지 않습니다.</p>");
                out.println("</body>");
                out.println("</html>");
                return;
            }
            //http프로토콜 관련 기능을 사용하려면
            //파라미터로 받은 servlet request 객체를 원래 타입으로 형변환 해야 한다
            //즉 req 래퍼런스는 실제 HTTP servletRequest객체를 가리키고 있다
            HttpServletRequest httpReq = (HttpServletRequest) req;

            //클라이언트 전용보관소를 알아낸다
            HttpSession session = httpReq.getSession();

            //클라이언트 전용 보관소에 로그인 사용자 정보를 보관한다
            session.setAttribute("loginUser", user);

            out.println("<p>로그인이 성공하였습니다</p>");
        } catch (Exception e) {
            out.println("<p>조회 중 오류 발생!</p>");
            e.printStackTrace();
        }

        out.println("</body>");
        out.println("</html>");
    }
}
