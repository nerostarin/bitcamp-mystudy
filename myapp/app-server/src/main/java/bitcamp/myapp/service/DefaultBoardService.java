package bitcamp.myapp.service;

import bitcamp.myapp.dao.BoardDao;
import bitcamp.myapp.vo.AttachedFile;
import bitcamp.myapp.vo.Board;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;


public class DefaultBoardService implements BoardService {
    private BoardDao boardDao;
    private SqlSessionFactory sqlSessionFactory;

    public DefaultBoardService(BoardDao boardDao, SqlSessionFactory sqlSessionFactory) {
        this.boardDao = boardDao;
        this.sqlSessionFactory = sqlSessionFactory;
    }

    @Override
    public void add(Board board) throws Exception {
        try {
            boardDao.insert(board);
            if (board.getAttachedFiles().size() > 0) {
                boardDao.insertFiles(board);
            }
            sqlSessionFactory.openSession(false).commit();
        } catch (Exception e) {
            sqlSessionFactory.openSession(false).rollback();
            throw e;
        }
    }

    @Override
    public List<Board> list() throws Exception {
        return boardDao.list();
    }

    @Override
    public Board get(int boardNo) throws Exception {
        try {
            Board board = boardDao.findBy(boardNo);
            sqlSessionFactory.openSession(false).commit();
            return board;
        } catch (Exception e) {
            sqlSessionFactory.openSession(false).rollback();
            throw e;
        }
    }

    @Override
    public boolean update(Board board) throws Exception {
        try {
            if (!boardDao.update(board)) {
                return false;
            }
            if (board.getAttachedFiles().size() > 0) {
                boardDao.insertFiles(board);
            }
            sqlSessionFactory.openSession(false).commit();
            return true;
        } catch (Exception e) {
            sqlSessionFactory.openSession(false).rollback();
            throw e;
        }
    }

    @Override
    public boolean delete(int boardNo) throws Exception {
        try {
            if (!boardDao.delete(boardNo)) {
                return false;
            }
            sqlSessionFactory.openSession(false).commit();
            return true;
        } catch (Exception e) {
            sqlSessionFactory.openSession(false).rollback();
            throw e;
        }
    }

    @Override
    public void increaseViewCount(int boardNo) throws Exception {
        Board board = boardDao.findBy(boardNo);
        if (board != null) {
            board.setViewCount(board.getViewCount() + 1);
            boardDao.updateViewCount(board.getNo(), board.getViewCount());
            sqlSessionFactory.openSession(false).commit();
        }
    }

    @Override
    public AttachedFile getAttachedFile(int fileNo) throws Exception {
        return boardDao.getFile(fileNo);
    }

    @Override
    public boolean deleteAttachedFile(int fileNo) throws Exception {
        try {
            if (!boardDao.deleteFile(fileNo)) {
                return false;
            }
            sqlSessionFactory.openSession(false).commit();
            return true;
        } catch (Exception e) {
            sqlSessionFactory.openSession(false).rollback();
            throw e;
        }
    }
}
