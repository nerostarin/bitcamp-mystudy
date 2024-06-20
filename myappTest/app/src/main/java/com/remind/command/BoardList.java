package com.remind.command;

import com.remind.vo.Board;

public class BoardList {
    static int MAX_SIZE = 100;
    public static Board[] boards = new Board[MAX_SIZE];
    static  int boardLength = 0;

    public static void add(Board board)
    {
        boards[boardLength++] = board;
    }

    public static Board[] toArray()
    {
        Board[] arr = new Board[boardLength];
        for (int i = 0; i < boardLength; i++)
        {
            arr[i] = boards[i];
        }
        return arr;
    }

    public static Board delete(int boardNo)
    {
        Board deleteBoard = BoardList.findByNo(boardNo);
        if (deleteBoard == null) {
            return null;
        }
        int index = indexOf(deleteBoard);
        for (int i = index +1; i < boardLength; i++) {
            boards[i - 1] = boards[i];
        }
        boards[--boardLength] = null;
        return deleteBoard;
    }

    public static Board findByNo(int boardNo) {
        for (int i = 0 ; i < boardLength; i++)
        {
            Board board = boards[i];
            if (board.getNo() == boardNo)
            {
                return board;
            }
        }
        return null;
    }

    public static int indexOf(Board board)
    {
        for (int i = 0; i< boardLength; i++)
        {
            if (board == boards[i])
            {
                return i;
            }
        }
        return -1;
    }
}
