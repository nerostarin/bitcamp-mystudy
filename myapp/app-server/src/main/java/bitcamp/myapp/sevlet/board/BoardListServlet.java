package bitcamp.myapp.sevlet.board;

import bitcamp.myapp.service.BoardService;
import bitcamp.myapp.vo.Board;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/board/list")
public class BoardListServlet extends HttpServlet {

    private BoardService boardService;

    @Override
    public void init() throws ServletException {

        boardService = (BoardService) this.getServletContext().getAttribute("boardService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        
        try {
            List<Board> list = boardService.list();

            // 콘텐트 출력은 JSP에 맡긴다.
            req.setAttribute("list", list); // JSP를 실행하기 전에 JSP가 사용할 객체를 ServletRequest 보관소에 보관한다.

            // 콘텐트 타입은 include() 호출 전에 실행해야 한다.
            res.setContentType("text/html;charset=UTF-8");
            req.getRequestDispatcher("/board/list.jsp").include(req, res);

        } catch (Exception e) {
            req.setAttribute("exception", e);
            req.getRequestDispatcher("/error.jsp").include(req, res);
        }
    }
}
