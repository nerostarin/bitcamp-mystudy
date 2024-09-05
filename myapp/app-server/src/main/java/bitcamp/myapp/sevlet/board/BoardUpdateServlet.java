package bitcamp.myapp.sevlet.board;

import bitcamp.myapp.dao.BoardDao;
import bitcamp.myapp.vo.Board;
import bitcamp.myapp.vo.User;
import org.apache.ibatis.session.SqlSessionFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/board/update")
public class BoardUpdateServlet extends HttpServlet {

    private BoardDao boardDao;
    private SqlSessionFactory sqlSessionFactory;

    @Override
    public void init() throws ServletException {
        this.boardDao = (BoardDao) this.getServletContext().getAttribute("boardDao");
        this.sqlSessionFactory = (SqlSessionFactory) this.getServletContext().getAttribute("sqlSessionFactory");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        try {
            req.setCharacterEncoding("UTF-8");
            User loginUser = (User) req.getSession().getAttribute("loginUser");

            int boardNo = Integer.parseInt(req.getParameter("no"));
            Board board = boardDao.findBy(boardNo);

            if (board == null) {
                throw new Exception("없는 게시글 입니다");
            } else if (loginUser == null || loginUser.getNo() > 10 && board.getWriter().getNo() != loginUser.getNo()) {
                throw new Exception("작성자가 아니거나 로그인을 해주세요");
            }

            board.setTitle(req.getParameter("title"));
            board.setContent(req.getParameter("content"));
            boardDao.update(board);
            sqlSessionFactory.openSession(false).commit();
            res.sendRedirect("/board/list");

        } catch (Exception e) {
            sqlSessionFactory.openSession(false).rollback();
            req.setAttribute("exception", e);
            req.getRequestDispatcher("/error.jsp").forward(req, res);
        }
    }
}
