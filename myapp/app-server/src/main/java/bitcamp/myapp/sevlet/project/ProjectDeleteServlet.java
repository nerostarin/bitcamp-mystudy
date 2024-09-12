package bitcamp.myapp.sevlet.project;

import bitcamp.myapp.service.ProjectService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/project/delete")
public class ProjectDeleteServlet extends HttpServlet {

    private ProjectService projectService;

    @Override
    public void init() throws ServletException {
        projectService = (ProjectService) this.getServletContext().getAttribute("projectService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        try {
            int projectNo = Integer.parseInt(req.getParameter("no"));

            projectService.deleteMember(projectNo);
            if (projectService.delete(projectNo)) {
                req.setAttribute("viewName", "redirect:list");
            } else {
                throw new Exception("없는 프로젝트입니다");
            }

        } catch (Exception e) {
            req.setAttribute("exception", e);
        }

    }

}
