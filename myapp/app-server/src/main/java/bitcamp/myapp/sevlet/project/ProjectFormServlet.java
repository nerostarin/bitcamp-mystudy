package bitcamp.myapp.sevlet.project;

import bitcamp.myapp.dao.UserDao;
import bitcamp.myapp.vo.User;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.util.List;

@WebServlet("/project/form")
public class ProjectFormServlet extends GenericServlet {

    private UserDao userDao;

    @Override
    public void init() throws ServletException {
        userDao = (UserDao) this.getServletContext().getAttribute("userDao");
    }

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        try {
            List<User> list = userDao.list();

            req.setAttribute("list", list);
            res.setContentType("text/html;charset=UTF-8");
            req.getRequestDispatcher("/project/form.jsp").include(req, res);
        } catch (Exception e) {

        }

    }
}
