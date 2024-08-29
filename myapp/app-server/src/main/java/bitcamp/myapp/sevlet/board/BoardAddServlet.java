package bitcamp.myapp.sevlet.board;

import bitcamp.myapp.dao.BoardDao;
import bitcamp.myapp.vo.Board;
import bitcamp.myapp.vo.User;
import org.apache.ibatis.session.SqlSessionFactory;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/board/add")
public class BoardAddServlet extends GenericServlet {

    private BoardDao boardDao;
    private SqlSessionFactory sqlSessionFactory;

    @Override
    public void init() throws ServletException {
        boardDao = (BoardDao) this.getServletContext().getAttribute("boardDao");
        sqlSessionFactory = (SqlSessionFactory) this.getServletContext().getAttribute("sqlSessionFactory");
    }

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html;charset=UTF-8");

        PrintWriter out = res.getWriter();
        req.getRequestDispatcher("/header").include(req, res);

        try {
            Board board = new Board();
            board.setTitle(req.getParameter("title"));
            board.setContent(req.getParameter("content"));
            User loginUser = (User) ((HttpServletRequest) req).getSession().getAttribute("loginUser");
            board.setWriter(loginUser);

            boardDao.insert(board);
            sqlSessionFactory.openSession(false).commit();
            out.println("<p>등록 성공입니다</p>");

        } catch (Exception e) {
            sqlSessionFactory.openSession(false).rollback();
            out.println("<p>등록 중 오류 발생!</p>");
            e.printStackTrace();
        }
        out.println("</body>");
        out.println("</html>");
        ((HttpServletResponse) res).setHeader("Refresh", "1;url=/board/list");
    }
}
