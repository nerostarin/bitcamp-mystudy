package bitcamp.myapp.listener;

import bitcamp.bitbatis.SqlSession;
import bitcamp.context.ApplicationContext;
import bitcamp.listener.ApplicationListener;
import bitcamp.menu.MenuGroup;
import bitcamp.menu.MenuItem;
import bitcamp.myapp.command.HelpCommand;
import bitcamp.myapp.command.HistoryCommand;
import bitcamp.myapp.command.board.*;
import bitcamp.myapp.command.project.*;
import bitcamp.myapp.command.user.*;
import bitcamp.myapp.dao.BoardDao;
import bitcamp.myapp.dao.ProjectDao;
import bitcamp.myapp.dao.UserDao;
import bitcamp.myapp.dao.mysql.BoardDaoImpl;
import bitcamp.myapp.dao.mysql.ProjectDaoImpl;
import bitcamp.myapp.dao.mysql.UserDaoImpl;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;


public class InitApplicationListener implements ApplicationListener {

    private Connection con;
    private UserDao userDao;
    private BoardDao boardDao;
    private ProjectDao projectDao;

    @Override
    public boolean onStart(ApplicationContext ctx) throws Exception {

        Properties props = new Properties();
        props.load(new FileReader("app.properties"));
        String url = props.getProperty("jdbc.url");
        String userName = props.getProperty("jdbc.username");
        String password = props.getProperty("jdbc.password");

        //JDBC 커넥션 객체 준비
        con = DriverManager.getConnection(url, userName, password);

        SqlSession sqlSession = new SqlSession(con);

        userDao = new UserDaoImpl(sqlSession);
        boardDao = new BoardDaoImpl(con);
        projectDao = new ProjectDaoImpl(con);

        ctx.setAttribute("userDao", userDao);
        ctx.setAttribute("projectDao", projectDao);
        ctx.setAttribute("boardDao", boardDao);

        MenuGroup mainMenu = ctx.getMainMenu();

        MenuGroup userMenu = new MenuGroup("회원");
        userMenu.add(new MenuItem("등록", new UserAddCommand(userDao)));
        userMenu.add(new MenuItem("목록", new UserListCommand(userDao)));
        userMenu.add(new MenuItem("조회", new UserViewCommand(userDao)));
        userMenu.add(new MenuItem("변경", new UserUpdateCommand(userDao)));
        userMenu.add(new MenuItem("삭제", new UserDeleteCommand(userDao)));
        mainMenu.add(userMenu);

        MenuGroup projectMenu = new MenuGroup("프로젝트");
        ProjectMemberHandler memberHandler = new ProjectMemberHandler(userDao);
        projectMenu.add(new MenuItem("등록", new ProjectAddCommand(projectDao, memberHandler, con)));
        projectMenu.add(new MenuItem("목록", new ProjectListCommand(projectDao)));
        projectMenu.add(new MenuItem("조회", new ProjectViewCommand(projectDao)));
        projectMenu.add(new MenuItem("변경", new ProjectUpdateCommand(projectDao, memberHandler, con)));
        projectMenu.add(new MenuItem("삭제", new ProjectDeleteCommand(projectDao, con)));
        mainMenu.add(projectMenu);

        MenuGroup boardMenu = new MenuGroup("게시판");
        boardMenu.add(new MenuItem("등록", new BoardAddCommand(boardDao, ctx)));
        boardMenu.add(new MenuItem("목록", new BoardListCommand(boardDao)));
        boardMenu.add(new MenuItem("조회", new BoardViewCommand(boardDao)));
        boardMenu.add(new MenuItem("변경", new BoardUpdateCommand(boardDao, ctx)));
        boardMenu.add(new MenuItem("삭제", new BoardDeleteCommand(boardDao, ctx)));
        mainMenu.add(boardMenu);

        mainMenu.add(new MenuItem("도움말", new HelpCommand()));
        mainMenu.add(new MenuItem("명령내역", new HistoryCommand()));
        mainMenu.setExitMenuTitle("종료");

        return true;
    }
}
