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

@WebServlet("/board/file/delete")
public class BoardFileDeleteServlet extends HttpServlet {

    private BoardService boardService;
    private String uploadDir;

    @Override
    public void init() throws ServletException {
        this.boardService = (BoardService) this.getServletContext().getAttribute("boardService");
        this.uploadDir = this.getServletContext().getRealPath("/upload/board");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        try {

            User loginUser = (User) req.getSession().getAttribute("loginUser");

            if (loginUser == null) {
                throw new Exception("로그인 하지 않았습니다");
            }

            int fileNo = Integer.parseInt(req.getParameter("fileNo"));
            AttachedFile attachedFile = boardService.getAttachedFile(fileNo);

            if (attachedFile == null) {
                throw new Exception("없는 첨부 파일입니다");
            }

            Board board = boardService.get(attachedFile.getBoardNo());

            if (board == null) {
                throw new Exception("없는 게시글입니다");
            } else if (loginUser == null || loginUser.getNo() > 10 && board.getWriter().getNo() != loginUser.getNo()) {
                throw new Exception("권한이 없는 게시글입니다");
            }

            for (AttachedFile attachedFile1 : board.getAttachedFiles()) {
                File file = new File(uploadDir + "/" + attachedFile1.getFilename());
                if (file.exists()) {
                    file.delete();
                }
            }

            boardService.deleteAttachedFile(fileNo);
            req.setAttribute("viewName", "redirect:../view?no=" + req.getParameter("boardNo"));

        } catch (Exception e) {
            req.setAttribute("exception", e);
            req.getRequestDispatcher("/error.jsp").forward(req, res);
        }

    }
}