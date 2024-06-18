package bitcamp.myapp.command;

import bitcamp.myapp.util.Prompt;
import bitcamp.myapp.vo.Board;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class BoardCommand {

    private static final int MAX_SIZE = 100;
    private static Board[] boards = new Board[MAX_SIZE];
    private static int boardLength = 0;
    private static int boardNo = 0;

    public static void executeBoardCommand(String command) {
        System.out.printf("[%s]\n", command);
        switch (command) {
            case "등록":
                addBoard();
                break;

            case "조회":
                viewBoard();
                break;

            case "목록":
                listBoard();
                break;

            case "변경":
                updateBoard();
                break;

            case "삭제":
                deleteBoard();
                break;
        }
    }

    private static void addBoard() {
        Board board = new Board();
        board.setName(Prompt.input("제목?"));
        board.setDetail(Prompt.input("내용?"));
        board.setDate(dateNow());
        board.setViews(0);
        boards[boardLength++] = board;
    }

    private static String dateNow() {
        LocalDate currentDate = LocalDate.now();

        // 날짜를 포맷하기 위한 DateTimeFormatter 생성
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // 날짜를 문자열로 변환하여 출력
        return currentDate.format(formatter);
    }

    private static void viewBoard() {
        System.out.println("번호 제목 작성일 조회수");
        for (int i = 0; i < boardLength; i++) {
            System.out.printf("%d %s %s %s\n", i + 1, boards[i].getName(), boards[i].getDate(), boards[i].getViews());
        }
    }

    private static void listBoard() {
        boardNo = Prompt.inputInt("게시글 번호?");
        if (boardNo < 1 || boardNo > boardLength) {
            System.out.println("없는 게시글 입니다");
            return;
        }
        Board board = boards[boardNo - 1];
        System.out.printf("제목: %s\n", board.getName());
        System.out.printf("내용: %s\n", board.getDetail());
        System.out.printf("작성일: %s\n", board.getDate());
        System.out.printf("제목: %s\n", board.getViews());
        board.setViews(board.getViews() + 1);

    }

    private static void updateBoard() {
        boardNo = Prompt.inputInt("게시글 번호?");
        if (boardNo < 1 || boardNo > boardLength) {
            System.out.println("없는 게시글 입니다");
            return;
        }
        Board board = boards[boardNo - 1];
        Prompt.input("제목(%s)?", board.getName());
        Prompt.input("제목(%s)?", board.getDetail());
        System.out.println("변경되었습니다");
    }

    private static void deleteBoard() {
        boardNo = Prompt.inputInt("게시글 번호?");
        if (boardNo < 1 || boardNo > boardLength) {
            System.out.println("없는 게시글 입니다");
            return;
        }
        for (int i = boardNo; i < boardLength; i++) {
            boards[i - 1] = boards[i];
        }
        boardLength--;
        boards[boardLength] = null;
        System.out.println("삭제 했습니다");
    }
}
