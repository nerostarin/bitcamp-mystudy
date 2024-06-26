package bitcamp.myapp.command;

import bitcamp.myapp.util.LinkedList;
import bitcamp.myapp.util.Prompt;
import bitcamp.myapp.vo.Board;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class BoardCommand {

    private static int boardNo = 0;
    LinkedList boardList = new LinkedList();

    private static String dateNow() {
        LocalDate currentDate = LocalDate.now();

        // 날짜를 포맷하기 위한 DateTimeFormatter 생성
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // 날짜를 문자열로 변환하여 출력
        return currentDate.format(formatter);
    }

    public void executeBoardCommand(String command) {
        System.out.printf("[%s]\n", command);
        switch (command) {
            case "등록":
                this.addBoard();
                break;

            case "목록":
                this.viewBoard();
                break;

            case "조회":
                this.listBoard();
                break;

            case "변경":
                this.updateBoard();
                break;

            case "삭제":
                this.deleteBoard();
                break;
        }
    }

    private void addBoard() {
        Board board = new Board();
        board.setName(Prompt.input("제목?"));
        board.setDetail(Prompt.input("내용?"));
        board.setDate(dateNow());
        board.setViews(0);
        board.setNo(Board.getNextSeqNo());
        boardList.add(board);
    }

    private void viewBoard() {
        System.out.println("번호 제목 작성일 조회수");
        for (Object obj : boardList.toArray()) {
            Board board = (Board) obj;
            System.out.printf("%d %s %s %s\n", board.getNo(), board.getName(), board.getDate(), board.getViews());
        }
    }

    private void listBoard() {
        boardNo = Prompt.inputInt("게시글 번호?");
        Board board = (Board) boardList.get(boardList.indexOf(new Board(boardNo)));
        if (board == null) {
            System.out.println("없는 게시글 입니다");
            return;
        }
        System.out.printf("제목: %s\n", board.getName());
        System.out.printf("내용: %s\n", board.getDetail());
        System.out.printf("작성일: %s\n", board.getDate());
        System.out.printf("제목: %s\n", board.getViews());
        board.setViews(board.getViews() + 1);

    }

    private void updateBoard() {
        boardNo = Prompt.inputInt("게시글 번호?");
        Board board = (Board) boardList.get(boardList.indexOf(new Board(boardNo)));
        if (board == null) {
            System.out.println("없는 게시글 입니다");
            return;
        }
        Prompt.input("제목(%s)?", board.getName());
        Prompt.input("제목(%s)?", board.getDetail());
        System.out.println("변경되었습니다");
    }

    private void deleteBoard() {
        boardNo = Prompt.inputInt("게시글 번호?");
        Board deletedBoard = (Board) boardList.get(boardList.indexOf(new Board(boardNo)));
        System.out.println(deletedBoard);
        if (deletedBoard != null) {
            boardList.remove(boardList.indexOf(deletedBoard));
            System.out.printf("%s 삭제 했습니다\n", deletedBoard.getName());
            return;
        }
        System.out.println("없는 회원입니다.");
    }

}
