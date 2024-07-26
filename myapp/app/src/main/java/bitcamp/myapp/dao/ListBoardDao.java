package bitcamp.myapp.dao;

import bitcamp.myapp.vo.Board;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ListBoardDao implements BoardDao {
    private static final String DEFAULT_DATANAME = "boards";
    private List<Board> boardList = new ArrayList<>();
    private int seqNo;
    private String path;
    private String dataName;

    public ListBoardDao(String path) {
        this(path, DEFAULT_DATANAME);
    }

    public ListBoardDao(String path, String dataName) {
        this.path = path;
        this.dataName = dataName;
        try (XSSFWorkbook workbook = new XSSFWorkbook(path)) {
            XSSFSheet sheet = workbook.getSheet(dataName);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Board board = new Board();
                Row row = sheet.getRow(i);
                board.setNo(Integer.parseInt(row.getCell(0).getStringCellValue()));
                board.setTitle(row.getCell(1).getStringCellValue());
                board.setContent(row.getCell(2).getStringCellValue());
                Date date = formatter.parse(row.getCell(3).getStringCellValue());
                board.setCreatedDate(date);
                board.setViewCount(Integer.parseInt(row.getCell(4).getStringCellValue()));

                boardList.add(board);
            }
            seqNo = boardList.getLast().getNo();
        } catch (Exception e) {
            System.out.println("게시판 데이터 로딩중 오류 발생");
            e.printStackTrace();
        }
    }

    public void save() throws Exception {
        try (FileInputStream in = new FileInputStream(path);
             XSSFWorkbook workbook = new XSSFWorkbook(in)) {

            int sheetIndex = workbook.getSheetIndex(dataName);
            if (sheetIndex != -1) {
                workbook.removeSheetAt(sheetIndex);
            }

            XSSFSheet sheet = workbook.createSheet(dataName);

            String[] cellHeaders = {"no", "title", "content", "Date", "viewCount"};
            Row headerRow = sheet.createRow(0);

            for (int i = 0; i < cellHeaders.length; i++) {
                headerRow.createCell(i).setCellValue(cellHeaders[i]);
            }

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            int rowNo = 1;
            for (Board board : boardList) {
                Row dataRow = sheet.createRow(rowNo++);
                dataRow.createCell(0).setCellValue(String.valueOf(board.getNo()));
                dataRow.createCell(1).setCellValue(board.getTitle());
                dataRow.createCell(2).setCellValue(board.getContent());
                dataRow.createCell(3).setCellValue(formatter.format(board.getCreatedDate()));
                dataRow.createCell(4).setCellValue(Integer.toString(board.getViewCount()));
            }
            //엑셀파일로 데이터를 출력하기 전에
            //workbook을 위해 연결한 입력 스트림을 먼저 종료한다
            in.close();

            try (FileOutputStream out = new FileOutputStream(path)) {
                workbook.write(out);
            }
        }
    }


    @Override
    public boolean insert(Board board) throws Exception {
        board.setNo(++seqNo);
        boardList.add(board);
        return true;
    }

    @Override
    public List<Board> list() throws Exception {
        return boardList;
    }

    @Override
    public Board findBy(int no) throws Exception {
        for (Board board : boardList) {
            if (board.getNo() == no) {
                return board;
            }
        }
        return null;
    }

    @Override
    public boolean update(Board board) throws Exception {
        int index = boardList.indexOf(board);
        if (index == -1) {
            return false;
        }
        boardList.set(index, board);
        return true;
    }

    @Override
    public boolean delete(int no) throws Exception {
        int index = boardList.indexOf(new Board(no));
        if (index == -1) {
            return false;
        }
        boardList.remove(index);
        return true;
    }
}
