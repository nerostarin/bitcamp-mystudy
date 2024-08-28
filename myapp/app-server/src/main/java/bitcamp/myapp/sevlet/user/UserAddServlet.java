package bitcamp.myapp.sevlet.user;

import bitcamp.myapp.dao.UserDao;
import bitcamp.myapp.vo.User;
import org.apache.ibatis.session.SqlSessionFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/user/add")
public class UserAddServlet extends GenericServlet {

    private UserDao userDao;
    private SqlSessionFactory sqlSessionFactory;

    @Override
    public void init() throws ServletException {
        ServletContext ctx = this.getServletContext();
        this.userDao = (UserDao) ctx.getAttribute("userDao");
        this.sqlSessionFactory = (SqlSessionFactory) ctx.getAttribute("sqlSessionFactory");
    }

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html;charset=UTF-8");

        PrintWriter out = res.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("    <meta charset='UTF-8'>");
        out.println("    <meta content='1; url=/user/list'  http-equiv='refresh'>");
        out.println("    <title>Title</title>");
        out.println("<link href='/css/common.css' rel='stylesheet'>");
        out.println("</head>");
        out.println("<body>");
        out.println("<header>");
        out.println("<a href=' / '><img src='/images/home.png' style='vertical-align:middle;'></a>프로젝트 관리 시스템");
        out.println("</header>");
        try {
            User user = new User();
            user.setName(req.getParameter("name"));
            user.setEmail(req.getParameter("email"));
            user.setPassword(req.getParameter("password"));
            user.setTel(req.getParameter("tel"));

            userDao.insert(user);
            sqlSessionFactory.openSession(false).commit();
            out.println("<p>등록 성공입니다</p>");

        } catch (Exception e) {
            sqlSessionFactory.openSession(false).rollback();
            out.println("<p>등록 중 오류 발생!</p>");
        }

        out.println("</body>");
        out.println("</html>");
    }
}
