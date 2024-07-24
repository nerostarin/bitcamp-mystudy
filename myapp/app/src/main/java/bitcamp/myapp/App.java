package bitcamp.myapp;

import bitcamp.menu.MenuGroup;
import bitcamp.menu.MenuItem;
import bitcamp.myapp.command.HelpCommand;
import bitcamp.myapp.command.HistoryCommand;
import bitcamp.myapp.command.board.*;
import bitcamp.myapp.command.project.*;
import bitcamp.myapp.command.user.*;
import bitcamp.myapp.vo.Board;
import bitcamp.myapp.vo.Project;
import bitcamp.myapp.vo.User;
import bitcamp.util.Prompt;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class App {

    MenuGroup mainMenu = new MenuGroup("메인");

    List<User> userList = new ArrayList<>();
    List<Project> projectList = new LinkedList<>();
    List<Board> boardList = new LinkedList<>();

    public App() {
        loadData();
        MenuGroup userMenu = new MenuGroup("회원");
        userMenu.add(new MenuItem("등록", new UserAddCommand(userList)));
        userMenu.add(new MenuItem("목록", new UserListCommand(userList)));
        userMenu.add(new MenuItem("조회", new UserViewCommand(userList)));
        userMenu.add(new MenuItem("변경", new UserUpdateCommand(userList)));
        userMenu.add(new MenuItem("삭제", new UserDeleteCommand(userList)));
        mainMenu.add(userMenu);

        MenuGroup projectMenu = new MenuGroup("프로젝트");
        ProjectMemberHandler memberHandler = new ProjectMemberHandler(userList);
        projectMenu.add(new MenuItem("등록", new ProjectAddCommand(projectList, memberHandler)));
        projectMenu.add(new MenuItem("목록", new ProjectListCommand(projectList)));
        projectMenu.add(new MenuItem("조회", new ProjectViewCommand(projectList, userList)));
        projectMenu.add(new MenuItem("변경", new ProjectUpdateCommand(projectList, memberHandler)));
        projectMenu.add(new MenuItem("삭제", new ProjectDeleteCommand(projectList)));
        mainMenu.add(projectMenu);

        MenuGroup boardMenu = new MenuGroup("게시판");
        boardMenu.add(new MenuItem("등록", new BoardAddCommand(boardList)));
        boardMenu.add(new MenuItem("목록", new BoardListCommand(boardList)));
        boardMenu.add(new MenuItem("조회", new BoardViewCommand(boardList)));
        boardMenu.add(new MenuItem("변경", new BoardUpdateCommand(boardList)));
        boardMenu.add(new MenuItem("삭제", new BoardDeleteCommand(boardList)));
        mainMenu.add(boardMenu);

        mainMenu.add(new MenuItem("도움말", new HelpCommand()));
        mainMenu.add(new MenuItem("명령내역", new HistoryCommand()));
        mainMenu.setExitMenuTitle("종료");
    }


    public static void main(String[] args) {
        new App().execute();
    }

    void execute() {
        try {
            mainMenu.execute();
        } catch (Exception ex) {
            System.out.println("오류가 발생하였습니다.");
        } finally {
            saveData();
        }
        System.out.println("종료합니다.");

        Prompt.close();
    }

    private void loadData() {
        try (XSSFWorkbook workbook = new XSSFWorkbook("data.xlsx")) {
            loadUser(workbook);
            loadBoards(workbook);
            loadProjects(workbook);
        } catch (Exception e) {
            System.out.println("데이터로딩중 문제 발생");
            e.printStackTrace();
        }
        System.out.println("데이터를 로딩했습니다");
    }

    private void loadUser(XSSFWorkbook workbook) {
        XSSFSheet sheet = workbook.getSheet("users");
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            User user = new User();
            Row row = sheet.getRow(i);
            user.setNo(Integer.parseInt(row.getCell(0).getStringCellValue()));
            user.setName(row.getCell(1).getStringCellValue());
            user.setEmail(row.getCell(2).getStringCellValue());
            user.setPassword(row.getCell(3).getStringCellValue());
            user.setTel(row.getCell(4).getStringCellValue());

            userList.add(user);
        }
    }

    private void loadBoards(XSSFWorkbook workbook) throws Exception {
        XSSFSheet sheet = workbook.getSheet("boards");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Board board = new Board();
            Row row = sheet.getRow(i);
            board.setNo(Integer.parseInt(row.getCell(0).getStringCellValue()));
            board.setTitle(row.getCell(1).getStringCellValue());
            board.setContent(row.getCell(2).getStringCellValue());
            Date date = formatter.parse(row.getCell(3).getStringCellValue());
            board.setCreatedDate(date);
            board.setViewCount((int) row.getCell(4).getNumericCellValue());

            boardList.add(board);
        }
    }

    private void loadProjects(XSSFWorkbook workbook) {
        XSSFSheet sheet = workbook.getSheet("projects");

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Project project = new Project();
            Row row = sheet.getRow(i);
            project.setNo(Integer.parseInt(row.getCell(0).getStringCellValue()));
            project.setTitle(row.getCell(1).getStringCellValue());
            project.setDescription(row.getCell(2).getStringCellValue());
            project.setStartDate(row.getCell(3).getStringCellValue());
            project.setEndDate(row.getCell(4).getStringCellValue());
            String[] num = row.getCell(5).getStringCellValue().split(",");

            for (int j = 0; j < num.length; j++) {
                int userNum = Integer.parseInt(num[j]);
                for (User user : userList) {
                    if (user.getNo() == userNum) {
                        project.getMembers().add(user);
                        break;
                    }
                }
            }
            projectList.add(project);
        }
    }


    private void saveData() {
        try {
            XSSFWorkbook workbook = new XSSFWorkbook();

            saveUsers(workbook);
            saveBoards(workbook);
            saveProjects(workbook);

            try (FileOutputStream out = new FileOutputStream("data.xlsx")) {
                workbook.write(out);
            }
            System.out.println("데이터를 저장했습니다");
        } catch (Exception e) {
            System.out.println("데이터 저장 중 오류 발생");
            e.printStackTrace();
        }
    }

    private void saveUsers(XSSFWorkbook workbook) {
        XSSFSheet sheet = workbook.createSheet("users");
        String[] cellHeaders = {"no", "name", "email", "password", "tel"};
        Row headerRow = sheet.createRow(0);

        for (int i = 0; i < cellHeaders.length; i++) {
            headerRow.createCell(i).setCellValue(cellHeaders[i]);
        }

        for (int i = 0; i < userList.size(); i++) {
            User user = userList.get(i);
            Row dataRow = sheet.createRow(i + 1);
            dataRow.createCell(0).setCellValue(String.valueOf(user.getNo()));
            dataRow.createCell(1).setCellValue(user.getName());
            dataRow.createCell(2).setCellValue(user.getEmail());
            dataRow.createCell(3).setCellValue(user.getPassword());
            dataRow.createCell(4).setCellValue(user.getTel());

        }
    }

    private void saveBoards(XSSFWorkbook workbook) {
        XSSFSheet sheet = workbook.createSheet("boards");
        String[] cellHeaders = {"no", "title", "content", "Date", "viewCount"};
        Row headerRow = sheet.createRow(0);

        for (int i = 0; i < cellHeaders.length; i++) {
            headerRow.createCell(i).setCellValue(cellHeaders[i]);
        }

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        for (int i = 0; i < boardList.size(); i++) {
            Board board = boardList.get(i);
            Row dataRow = sheet.createRow(i + 1);
            dataRow.createCell(0).setCellValue(String.valueOf(board.getNo()));
            dataRow.createCell(1).setCellValue(board.getTitle());
            dataRow.createCell(2).setCellValue(board.getContent());
            dataRow.createCell(3).setCellValue(formatter.format(board.getCreatedDate()));
            dataRow.createCell(4).setCellValue(board.getViewCount());

        }
    }

    private void saveProjects(XSSFWorkbook workbook) {
        XSSFSheet sheet = workbook.createSheet("projects");
        String[] cellHeaders = {"no", "title", "description", "start_date", "end_date", "members"};
        Row headerRow = sheet.createRow(0);

        for (int i = 0; i < cellHeaders.length; i++) {
            headerRow.createCell(i).setCellValue(cellHeaders[i]);
        }

        for (int i = 0; i < projectList.size(); i++) {
            Project project = projectList.get(i);
            Row dataRow = sheet.createRow(i + 1);
            dataRow.createCell(0).setCellValue(String.valueOf(project.getNo()));
            dataRow.createCell(1).setCellValue(project.getTitle());
            dataRow.createCell(2).setCellValue(project.getDescription());
            dataRow.createCell(3).setCellValue(project.getStartDate());
            dataRow.createCell(4).setCellValue(project.getEndDate());

            StringBuilder strBuilder = new StringBuilder();
            for (User member : project.getMembers()) {
                if (strBuilder.length() > 0) {
                    strBuilder.append(",");
                }
                strBuilder.append(member.getNo());
            }
            dataRow.createCell(5).setCellValue(strBuilder.toString());
        }
    }
}
