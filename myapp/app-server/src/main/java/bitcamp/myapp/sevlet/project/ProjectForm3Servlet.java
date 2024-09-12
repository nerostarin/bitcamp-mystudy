package bitcamp.myapp.sevlet.project;

import bitcamp.myapp.service.UserService;
import bitcamp.myapp.vo.Project;
import bitcamp.myapp.vo.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/project/form3")
public class ProjectForm3Servlet extends HttpServlet {

    private UserService userService;

    @Override
    public void init() throws ServletException {
        this.userService = (UserService) this.getServletContext().getAttribute("userService");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        //세션에 보관된 프로젝트 기본정보를 꺼낸디
        try {
            Project project = (Project) req.getSession().getAttribute("project");

            String[] memberNos = req.getParameterValues("member");//멤버라는 모든 파라미터의 값을 전부 가지고 온다
            if (memberNos != null) {
                ArrayList<User> members = new ArrayList<>();
                for (String memberNo : memberNos) {
                    User user = userService.get(Integer.parseInt(memberNo));
                    members.add(user);
                }
                project.setMembers(members);
            }
            req.setAttribute("viewName", "/project/form3.jsp");
        } catch (Exception e) {
            req.setAttribute("exception", e);
        }

    }

}
