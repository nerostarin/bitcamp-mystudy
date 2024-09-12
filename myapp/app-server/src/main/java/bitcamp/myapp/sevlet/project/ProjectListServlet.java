package bitcamp.myapp.sevlet.project;

import bitcamp.myapp.service.ProjectService;
import bitcamp.myapp.vo.Project;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/project/list")
public class ProjectListServlet extends HttpServlet {

    private ProjectService projectService;

    @Override
    public void init() throws ServletException {
        this.projectService = (ProjectService) getServletContext().getAttribute("projectService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        try {
            List<Project> list = projectService.list();
            req.setAttribute("list", list);
            req.setAttribute("viewName", "/project/list.jsp");

        } catch (Exception e) {
            req.setAttribute("exception", e);
            req.getRequestDispatcher("/error.jsp").include(req, res);
        }
    }
}
