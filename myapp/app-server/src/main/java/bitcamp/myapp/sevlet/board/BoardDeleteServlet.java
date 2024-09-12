package bitcamp.myapp.sevlet.board;

import bitcamp.myapp.service.BoardService;
import bitcamp.myapp.vo.AttachedFile;
import bitcamp.myapp.vo.Board;
import bitcamp.myapp.vo.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

@WebServlet("/board/delete")
public class BoardDeleteServlet extends HttpServlet {

    private String uploadDir;
    private BoardService boardService;


    @Override
    public void init() throws ServletException {
        this.boardService = (BoardService) this.getServletContext().getAttribute("boardService");
        this.uploadDir = this.getServletContext().getRealPath("/upload/board");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        try {

            User loginUser = (User) req.getSession().getAttribute("loginUser");

            int boardNo = Integer.parseInt(req.getParameter("no"));

            Board board = boardService.get(boardNo);
            if (board == null) {
                throw new Exception("없는 게시글입니다");
            } else if (loginUser == null || loginUser.getNo() > 10 && board.getWriter().getNo() != loginUser.getNo()) {
                throw new Exception("권한이 없는 게시글입니다");
            }

            for (AttachedFile attachedFile : board.getAttachedFiles()) {
                File file = new File(uploadDir + "/" + attachedFile.getFilename());
                if (file.exists()) {
                    file.delete();
                }
            }

            boardService.delete(boardNo);
            res.sendRedirect("/board/list");

        } catch (Exception e) {
            req.setAttribute("exception", e);
            req.getRequestDispatcher("/error.jsp").forward(req, res);
        }

    }
}