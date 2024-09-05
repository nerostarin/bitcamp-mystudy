package bitcamp.myapp.sevlet.project;

import bitcamp.myapp.dao.ProjectDao;
import bitcamp.myapp.vo.Project;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.util.List;

@WebServlet("/project/list")
public class ProjectListServlet extends GenericServlet {

    private ProjectDao projectDao;

    @Override
    public void init() throws ServletException {

        projectDao = (ProjectDao) this.getServletContext().getAttribute("projectDao");
    }

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {


        try {
            List<Project> list = projectDao.list();

            // 콘텐트 출력은 JSP에 맡긴다.
            req.setAttribute("list", list); // JSP를 실행하기 전에 JSP가 사용할 객체를 ServletRequest 보관소에 보관한다.

            // 콘텐트 타입은 include() 호출 전에 실행해야 한다.
            res.setContentType("text/html;charset=UTF-8");
            req.getRequestDispatcher("/project/list.jsp").include(req, res);

        } catch (Exception e) {
            req.setAttribute("exception", e);
            req.getRequestDispatcher("/error.jsp").include(req, res);
        }
    }
}