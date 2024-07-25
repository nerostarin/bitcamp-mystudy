package bitcamp.myapp;

import bitcamp.menu.MenuGroup;
import bitcamp.menu.MenuItem;
import bitcamp.myapp.command.HelpCommand;
import bitcamp.myapp.command.HistoryCommand;
import bitcamp.myapp.command.board.*;
import bitcamp.myapp.command.project.*;
import bitcamp.myapp.command.user.*;
import bitcamp.myapp.dao.*;
import bitcamp.util.Prompt;

public class App {

    MenuGroup mainMenu = new MenuGroup("메인");

    UserDao userDao;
    BoardDao boardDao;
    ProjectDao projectDao;

    public App() {
        //loadData();
        userDao = new MapUserDao("data.xlsx");
        boardDao = new MapBoardDao("data.xlsx");
        projectDao = new MapProjectDao("data.xlsx", userDao);


        MenuGroup userMenu = new MenuGroup("회원");
        userMenu.add(new MenuItem("등록", new UserAddCommand(userDao)));
        userMenu.add(new MenuItem("목록", new UserListCommand(userDao)));
        userMenu.add(new MenuItem("조회", new UserViewCommand(userDao)));
        userMenu.add(new MenuItem("변경", new UserUpdateCommand(userDao)));
        userMenu.add(new MenuItem("삭제", new UserDeleteCommand(userDao)));
        mainMenu.add(userMenu);

        MenuGroup projectMenu = new MenuGroup("프로젝트");
        ProjectMemberHandler memberHandler = new ProjectMemberHandler(userDao);
        projectMenu.add(new MenuItem("등록", new ProjectAddCommand(projectDao, memberHandler)));
        projectMenu.add(new MenuItem("목록", new ProjectListCommand(projectDao)));
        projectMenu.add(new MenuItem("조회", new ProjectViewCommand(projectDao)));
        projectMenu.add(new MenuItem("변경", new ProjectUpdateCommand(projectDao, memberHandler)));
        projectMenu.add(new MenuItem("삭제", new ProjectDeleteCommand(projectDao)));
        mainMenu.add(projectMenu);

        MenuGroup boardMenu = new MenuGroup("게시판");
        boardMenu.add(new MenuItem("등록", new BoardAddCommand(boardDao)));
        boardMenu.add(new MenuItem("목록", new BoardListCommand(boardDao)));
        boardMenu.add(new MenuItem("조회", new BoardViewCommand(boardDao)));
        boardMenu.add(new MenuItem("변경", new BoardUpdateCommand(boardDao)));
        boardMenu.add(new MenuItem("삭제", new BoardDeleteCommand(boardDao)));
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
            try {
                ((MapUserDao) userDao).save();
                ((MapBoardDao) boardDao).save();
                ((MapProjectDao) projectDao).save();
            } catch (Exception e) {
                System.out.println("데이터 저장 중 오류 발생");
                e.printStackTrace();
                System.out.println();
            }
        }
        System.out.println("종료합니다.");

        Prompt.close();
    }
}
