package com.remind.command;

import com.remind.util.Prompt;
import com.remind.vo.Board;
import com.remind.vo.Project;
import com.remind.vo.User;

import java.util.Date;

public class BoardCommand {

    private static int boardNo = 0;
    BoardList boardList = new BoardList();
    public void executeBoardCommand(String command) {
        System.out.printf("[%s]\n", command);

        switch (command)
        {
            case"등록":
                addBoard();
                break;
            case"목록":
                listBoard();
                break;
            case"조회":
                viewBoard();
                break;
            case"변경":
                updateBoard();
                break;
            case"삭제":
                deleteBoard();
                break;
        }
    }

     void addBoard()
    {
        Board board = new Board();
        board.setTitle(Prompt.input("제목?"));
        board.setContent(Prompt.input("내용?"));
        board.setCreateDate(new Date());
        board.setNo(Board.getNextSeqNo());
        boardList.add(board);
    }

     void listBoard()
    {
        System.out.println("번호 제목 작성일 조회수");
        for(Object object : boardList.toArray())
        {
            Board board = (Board) object;
            System.out.printf("%d %s %tY-%3$tm-%3$td %s\n", board.getNo(), board.getTitle(), board.getCreateDate(), board.getViewCount());
        }
    }

     void viewBoard()
    {
        boardNo = Integer.parseInt(Prompt.input("게시글 번호?"));
        Board board = boardList.findByNo(boardNo);
        if (board == null) {
            System.out.println("없는 회원 입니다");
            return;
        }
        board.setViewCount(board.getViewCount()+1);
        System.out.printf("제목: %s \n", board.getTitle());
        System.out.printf("내용: %s \n", board.getContent());
        System.out.printf("작성일: %tY-%1$tm-%1$td %1$tH : %1$tM : %1$ts\n", board.getCreateDate());
        System.out.printf("조회수: %d \n", board.getViewCount());
    }

     void updateBoard()
    {
        boardNo = Integer.parseInt(Prompt.input("게시글 번호?"));
        Board board = boardList.findByNo(boardNo);
        if (board == null) {
            System.out.println("없는 회원 입니다");
            return;
        }
        board.setTitle(Prompt.input("제목 (%s)?", board.getTitle()));
        board.setContent(Prompt.input("내용 (%s)?", board.getContent()));
    }

     void deleteBoard() {
        boardNo = Integer.parseInt(Prompt.input("게시글 번호?"));
        Board deletedBoard = boardList.findByNo(boardNo);
        if (deletedBoard != null) {
            boardList.remove(boardList.indexOf(deletedBoard));
            System.out.printf("%s 삭제 했습니다\n", deletedBoard.getTitle());
            return;
        }
        System.out.println("삭제 헸습니다");
    }

}
