package bitcamp.myapp.sevlet.project;

import bitcamp.myapp.service.ProjectService;
import bitcamp.myapp.vo.Project;
import bitcamp.myapp.vo.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;

@WebServlet("/project/update")
public class ProjectUpdateServlet extends HttpServlet {

    private ProjectService projectService;

    @Override
    public void init() throws ServletException {
        projectService = (ProjectService) this.getServletContext().getAttribute("projectService");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

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

            if (!projectService.update(project)) {
                throw new Exception("없는 프로젝트 입니다");
            }

            projectService.deleteMember(project.getNo());
            if (project.getMembers() != null && project.getMembers().size() > 0) {
                projectService.insertMember(project.getNo(), project.getMembers());
            }

            projectService.update(project);
            res.sendRedirect("/project/list");

        } catch (Exception e) {
            req.setAttribute("exception", e);
            req.getRequestDispatcher("/error.jsp").forward(req, res);
        }

    }

}
