package bitcamp.myapp.dao.mysql;

import bitcamp.myapp.dao.BoardDao;
import bitcamp.myapp.vo.Board;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BoardDaoImpl implements BoardDao {

    private Connection con;

    public BoardDaoImpl(Connection con) {
        this.con = con;
    }

    @Override
    public boolean insert(Board board) throws Exception {
        try (Statement stmt = con.createStatement()) {
            stmt.executeUpdate(String.format(
                    "insert into myapp_boards(title, content, created_date, view_count)"
                            + "values('%s','%s','%tF %<tT','%d')",
                    board.getTitle(), board.getContent(), board.getCreatedDate(), board.getViewCount()));
            return true;
        }
    }

    @Override
    public List<Board> list() throws Exception {
        try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery("select * from myapp_boards order by board_id asc")) {

            ArrayList<Board> list = new ArrayList<>();

            while (rs.next()) {
                Board board = new Board();
                board.setNo(rs.getInt("board_id"));
                board.setTitle(rs.getString("title"));
                board.setCreatedDate(rs.getDate("created_date"));
                board.setViewCount(rs.getInt("view_count"));
                list.add(board);
            }
            return list;
        }
    }

    @Override
    public Board findBy(int no) throws Exception {
        try (// SQL을 서버에 전달할 객체 준비
             Statement stmt = con.createStatement();

             // select 문 실행을 요청한다.
             ResultSet rs = stmt.executeQuery("select * from myapp_boards where board_id=" + no)) {

            if (rs.next()) { // select 실행 결과에서 1 개의 레코드를 가져온다.
                Board board = new Board();
                board.setNo(rs.getInt("board_id"));
                board.setTitle(rs.getString("title"));
                board.setContent(rs.getString("content"));
                board.setCreatedDate(rs.getDate("created_date"));
                board.setViewCount(rs.getInt("view_count"));
                stmt.executeUpdate(String.format(
                        "update myapp_boards set" + " view_count ='%d'" + " where board_id=%d", board.getViewCount() + 1, no));
                return board;
            }

            return null;
        }
    }

    @Override
    public boolean update(Board board) throws Exception {
        try (// SQL을 서버에 전달할 객체 준비
             Statement stmt = con.createStatement()) {

            // update 문 전달
            int count = stmt.executeUpdate(String.format(
                    "update myapp_boards set"
                            + " title='%s',"
                            + " content='%s',"
                            + " created_date = '%tF %<tT',"
                            + " view_count='%d'" + " where board_id=%d",
                    board.getTitle(),
                    board.getContent(),
                    board.getCreatedDate(),
                    board.getViewCount(),
                    board.getNo()));

            return count > 0;
        }
    }

    @Override
    public boolean delete(int no) throws Exception {
        try (// SQL을 서버에 전달할 객체 준비
             Statement stmt = con.createStatement()) {

            // delete 문 전달
            int count = stmt.executeUpdate(String.format("delete from myapp_boards where board_id=%d", no));

            return count > 0;
        }
    }
}
