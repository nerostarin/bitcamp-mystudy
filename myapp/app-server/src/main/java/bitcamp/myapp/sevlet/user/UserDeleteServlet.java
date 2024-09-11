package bitcamp.myapp.sevlet.user;

import bitcamp.myapp.service.UserService;
import org.apache.ibatis.session.SqlSessionFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/user/delete")
public class UserDeleteServlet extends HttpServlet {

    private UserService userService;
    private SqlSessionFactory sqlSessionFactory;

    @Override
    public void init() throws ServletException {
        this.userService = (UserService) this.getServletContext().getAttribute("userService");
        this.sqlSessionFactory = (SqlSessionFactory) this.getServletContext().getAttribute("sqlSessionFactory");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        try {
            int userNo = Integer.parseInt(req.getParameter("no"));

            if (userService.delete(userNo)) {
                sqlSessionFactory.openSession(false).commit();
                res.sendRedirect("/user/list");
            } else {
                throw new Exception("없는 회원입니다");
            }

        } catch (Exception e) {
            sqlSessionFactory.openSession(false).rollback();
            req.setAttribute("exception", e);
            req.getRequestDispatcher("/error.jsp").forward(req, res);
        }
    }
}
