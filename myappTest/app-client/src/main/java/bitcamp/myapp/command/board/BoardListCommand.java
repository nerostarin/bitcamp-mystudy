package bitcamp.myapp.command.board;

import bitcamp.command.Command;
import bitcamp.myapp.dao.BoardDao;
import bitcamp.myapp.vo.Board;

public class BoardListCommand implements Command {
    private BoardDao boardDao;

    public BoardListCommand(BoardDao boardDao) {
        this.boardDao = boardDao;
    }

    @Override
    public void execute(String menuName) {
        System.out.println("번호 제목 작성일 조회수");
        try {
            for (Board board : boardDao.list()) {
                System.out.printf("%d %s %s %tY-%4$tm-%4$td %d\n", board.getNo(), board.getTitle(), board.getWriter().getName(), board.getCreatedDate(), board.getViewCount());
            }
        } catch (Exception e) {
            System.out.println("게시물 목록 출력중 오류 발생");
            e.printStackTrace();
        }
    }
}
