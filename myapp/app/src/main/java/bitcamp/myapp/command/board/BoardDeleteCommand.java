package bitcamp.myapp.command.board;

import bitcamp.myapp.command.Command;
import bitcamp.myapp.dao.BoardDao;
import bitcamp.myapp.vo.Board;
import bitcamp.util.Prompt;

public class BoardDeleteCommand implements Command {
    private BoardDao boardDao;

    public BoardDeleteCommand(BoardDao boardDao) {
        this.boardDao = boardDao;
    }

    @Override
    public void execute(String menuName) {
        int boardNo = Prompt.inputInt("게시글 번호?");
        try {
            Board deletedBoard = boardDao.findBy(boardNo);
            if (deletedBoard == null) {
                System.out.println("없는 게시글입니다.");
                return;
            }

            boardDao.delete(boardNo);
            System.out.printf("%d번 게시글을 삭제 했습니다.\n", deletedBoard.getNo());

        } catch (Exception e) {
            System.out.println("게시물 삭제중 오류 발생");
        }
    }
}
