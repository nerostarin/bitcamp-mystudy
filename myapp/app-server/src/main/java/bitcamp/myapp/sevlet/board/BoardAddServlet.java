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

        try {
            Board board = new Board();
            board.setTitle(req.getParameter("title"));
            board.setContent(req.getParameter("content"));
            User loginUser = (User) ((HttpServletRequest) req).getSession().getAttribute("loginUser");
            board.setWriter(loginUser);

            boardDao.insert(board);
            sqlSessionFactory.openSession(false).commit();
            ((HttpServletResponse) res).sendRedirect("/board/list");

        } catch (Exception e) {
            sqlSessionFactory.openSession(false).rollback();
            req.setAttribute("exception", e);
            req.getRequestDispatcher("/error.jsp").forward(req, res);
        }

    }
}
