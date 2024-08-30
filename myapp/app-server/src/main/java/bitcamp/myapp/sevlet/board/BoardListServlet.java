package bitcamp.myapp.sevlet.board;

import bitcamp.myapp.dao.BoardDao;
import bitcamp.myapp.vo.Board;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.util.List;

@WebServlet("/board/list")
public class BoardListServlet extends GenericServlet {

    private BoardDao boardDao;

    @Override
    public void init() throws ServletException {

        boardDao = (BoardDao) this.getServletContext().getAttribute("boardDao");
    }

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {


        try {
            List<Board> list = boardDao.list();

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
