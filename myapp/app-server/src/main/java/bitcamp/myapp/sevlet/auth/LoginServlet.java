package bitcamp.myapp.sevlet.auth;

import bitcamp.myapp.dao.UserDao;
import bitcamp.myapp.vo.User;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/auth/login")
public class LoginServlet extends GenericServlet {

    private UserDao userDao;

    @Override
    public void init() throws ServletException {
        userDao = (UserDao) this.getServletContext().getAttribute("userDao");
    }

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {

        try {
            String email = req.getParameter("email");
            String password = req.getParameter("password");

            User user = userDao.findByEmailAndPassword(email, password);
            if (user == null) {
                ((HttpServletResponse) res).setHeader("Refresh", "1;url=/auth/form");
                res.setContentType("text/html;charset='UTF-8'");
                req.getRequestDispatcher("/auth/fail.jsp").include(req, res);
                return;
            }

            HttpServletRequest httpReq = (HttpServletRequest) req;

            //클라이언트 전용보관소를 알아낸다
            HttpSession session = httpReq.getSession();

            //클라이언트 전용 보관소에 로그인 사용자 정보를 보관한다
            session.setAttribute("loginUser", user);
            ((HttpServletResponse) res).sendRedirect("/");
        } catch (Exception e) {
            req.setAttribute("exception", e);
            req.getRequestDispatcher("/error.jsp").forward(req, res);
        }
    }
}