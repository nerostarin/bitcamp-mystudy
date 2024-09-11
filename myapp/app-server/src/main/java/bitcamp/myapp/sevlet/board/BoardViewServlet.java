package bitcamp.myapp.sevlet.board;

import bitcamp.myapp.service.BoardService;
import bitcamp.myapp.vo.Board;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/board/view")
public class BoardViewServlet extends HttpServlet {

    private BoardService boardService;

    @Override
    public void init() throws ServletException {
        boardService = (BoardService) this.getServletContext().getAttribute("boardService");
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        try {
            int boardNo = Integer.parseInt(req.getParameter("no"));
            Board board = boardService.get(boardNo);
            boardService.increaseViewCount(boardNo);
            req.setAttribute("board", board);

            res.setContentType("text/html;charset=UTF-8");
            req.getRequestDispatcher("/board/view.jsp").include(req, res);

        } catch (Exception e) {
            req.setAttribute("exception", e);
            req.getRequestDispatcher("/error.jsp").forward(req, res);
        }
    }
}
