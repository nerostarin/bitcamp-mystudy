package bitcamp.myapp.sevlet.project;

import bitcamp.myapp.dao.ProjectDao;
import bitcamp.myapp.vo.Project;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/project/list")
public class ProjectListServlet implements Servlet {

    private ProjectDao projectDao;
    private ServletConfig config;

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.config = config;
        ServletContext ctx = config.getServletContext();
        projectDao = (ProjectDao) ctx.getAttribute("projectDao");
    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {

        res.setContentType("text/html; charset=UTF-8");

        PrintWriter out = res.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<title>Title</title>");
        out.println("<link href='/css/common.css' rel='stylesheet'>");
        out.println("</head>");
        out.println("<body>");
        out.println("<header>");
        out.println("<a href=' / '><img src='/images/home.png' style='vertical-align:middle;'></a>프로젝트 관리 시스템");
        out.println("</header>");
        try {
            out.println("<h1>[프로젝트 목록]</h1>");
            out.println("<p><a href ='/project/form'>새 프로젝트</a></p>");
            out.println("<table border = '1'>");
            out.println("<thead>");
            out.println("<tr><th>번호</th><th>제목</th><th>시작일 ~ 종료일</th></tr>");
            out.println("</thead>");
            out.println("<tbody>");
            for (Project project : projectDao.list()) {
                out.printf("<tr><td>%d</td><td><a href='/project/view?no=%1$d'>%s</a></td><td>%s ~ %s</td></tr>\n", project.getNo(), project.getTitle(), project.getStartDate(), project.getEndDate());
            }
            out.println("</tbody>");
            out.println("</table>");
        } catch (Exception e) {
            out.println("목록 조회 중 오류 발생!");
        }
        out.println("</body>");
        out.println("</html>");
    }

    @Override
    public String getServletInfo() {
        return "";
    }

    @Override
    public void destroy() {

    }
}
