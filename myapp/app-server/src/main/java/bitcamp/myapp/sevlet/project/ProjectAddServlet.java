package bitcamp.myapp.sevlet.project;

import bitcamp.myapp.dao.ProjectDao;
import bitcamp.myapp.vo.Project;
import bitcamp.myapp.vo.User;
import org.apache.ibatis.session.SqlSessionFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;

@WebServlet("/project/add")
public class ProjectAddServlet extends HttpServlet {

    private ProjectDao projectDao;
    private SqlSessionFactory sqlSessionFactory;

    @Override
    public void init() throws ServletException {
        ServletContext ctx = this.getServletContext();
        this.projectDao = (ProjectDao) ctx.getAttribute("projectDao");
        this.sqlSessionFactory = (SqlSessionFactory) ctx.getAttribute("sqlSessionFactory");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        try {
            req.setCharacterEncoding("UTF-8");
            Project project = new Project();
            project.setTitle(req.getParameter("title"));
            project.setDescription(req.getParameter("description"));
            project.setStartDate(Date.valueOf(req.getParameter("startDate")));
            project.setEndDate(Date.valueOf(req.getParameter("endDate")));
            String[] memberNos = req.getParameterValues("member");//멤버라는 모든 파라미터의 값을 전부 가지고 온다

            if (memberNos != null) {
                ArrayList<User> members = new ArrayList<>();
                for (String memberNo : memberNos) {
                    members.add(new User(Integer.parseInt(memberNo)));
                }
                project.setMembers(members);
            }

            projectDao.insert(project);

            if (project.getMembers() != null && project.getMembers().size() > 0) {
                projectDao.insertMembers(project.getNo(), project.getMembers());
            }
            sqlSessionFactory.openSession(false).commit();
            res.sendRedirect("/project/list");


        } catch (Exception e) {
            sqlSessionFactory.openSession(false).rollback();
            req.setAttribute("exception", e);
            req.getRequestDispatcher("/error.jsp").forward(req, res);
        }

    }
}
