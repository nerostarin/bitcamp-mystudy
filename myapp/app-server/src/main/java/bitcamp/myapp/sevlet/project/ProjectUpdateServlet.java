package bitcamp.myapp.sevlet.project;

import bitcamp.myapp.dao.ProjectDao;
import bitcamp.myapp.vo.Project;
import bitcamp.myapp.vo.User;
import org.apache.ibatis.session.SqlSessionFactory;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;

@WebServlet("/project/update")
public class ProjectUpdateServlet extends GenericServlet {

    private ProjectDao projectDao;
    private SqlSessionFactory sqlSessionFactory;

    @Override
    public void init() throws ServletException {
        projectDao = (ProjectDao) this.getServletContext().getAttribute("projectDao");
        sqlSessionFactory = (SqlSessionFactory) this.getServletContext().getAttribute("sqlSessionFactory");
    }

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {

        try {

            Project project = new Project();
            project.setNo(Integer.parseInt(req.getParameter("no")));
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

            if (!projectDao.update(project)) {
                throw new Exception("없는 프로젝트 입니다");
            }

            projectDao.deleteMembers(project.getNo());
            if (project.getMembers() != null && project.getMembers().size() > 0) {
                projectDao.insertMembers(project.getNo(), project.getMembers());
            }

            projectDao.update(project);
            sqlSessionFactory.openSession(false).commit();
            ((HttpServletResponse) res).sendRedirect("/project/list");

        } catch (Exception e) {
            sqlSessionFactory.openSession(false).rollback();
            req.setAttribute("exception", e);
            req.getRequestDispatcher("/error.jsp").forward(req, res);
        }

    }

}