package bitcamp.myapp.sevlet.board;

import bitcamp.myapp.dao.BoardDao;
import bitcamp.myapp.vo.Board;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/board/list")
public class BoardListServlet implements Servlet {

    private BoardDao boardDao;
    private ServletConfig config;

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.config = config;
        ServletContext ctx = config.getServletContext();
        boardDao = (BoardDao) ctx.getAttribute("boardDao");
    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html; charset=UTF-8");

        PrintWriter out = res.getWriter();

        req.getRequestDispatcher("/header").include(req, res);

        try {
            out.println("<h1>[게시물 목록]</h1>");
            out.println("<p><a href ='/board/form'>새 게시물</a></p>");
            out.println("<table border = '1'>");
            out.println("<thead>");
            out.println("<tr><th>번호</th><th>제목</th><th>작성자</th><th>작성일</th><th>조회수</th></tr>");
            out.println("</thead>");
            out.println("<tbody>");
            ;
            for (Board board : boardDao.list()) {
                out.printf("<tr><td>%d</td><td><a href='/board/view?no=%1$d'>%s</a></td><td>%s</td><td>%tY-%4$tm-%4$td</td><td>%d</td></tr>\n",
                        board.getNo(),
                        board.getTitle(),
                        board.getWriter().getName(),
                        board.getCreatedDate(),
                        board.getViewCount());
            }
            out.println("</tbody>");
            out.println("</table>");
        } catch (Exception e) {
            out.println("목록 조회 중 오류 발생!");
            e.printStackTrace();
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
