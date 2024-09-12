package bitcamp.myapp.sevlet.auth;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/auth/logout")
public class LogoutServlet extends HttpServlet {


    @Override
    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        req.getSession().invalidate();
        req.setAttribute("viewName", "redirect:/");
    }
}
