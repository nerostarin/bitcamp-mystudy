package bitcamp.myapp.listener;

import bitcamp.myapp.controller.*;
import bitcamp.myapp.dao.BoardDao;
import bitcamp.myapp.dao.DaoFactory;
import bitcamp.myapp.dao.ProjectDao;
import bitcamp.myapp.dao.UserDao;
import bitcamp.myapp.mybatis.SqlSessionFactoryProxy;
import bitcamp.myapp.service.DefaultBoardService;
import bitcamp.myapp.service.DefaultProjectService;
import bitcamp.myapp.service.DefaultUserService;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@WebListener
public class ContextLoaderListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        //서블릿 컨테이너가 실행 될때 호출되는 메서드
        try {
            System.out.println("서비스 관련 객체 준비!");

            InputStream inputStream = Resources.getResourceAsStream("config/mybatis-config.xml");
            SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
            SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(inputStream);

            SqlSessionFactoryProxy sqlSessionFactoryProxy = new SqlSessionFactoryProxy(sqlSessionFactory);

            DaoFactory daoFactory = new DaoFactory(sqlSessionFactoryProxy);

            UserDao userDao = daoFactory.createObject(UserDao.class);
            BoardDao boardDao = daoFactory.createObject(BoardDao.class);
            ProjectDao projectDao = daoFactory.createObject(ProjectDao.class);

            DefaultUserService userService = new DefaultUserService(userDao, sqlSessionFactoryProxy);
            DefaultBoardService boardService = new DefaultBoardService(boardDao, sqlSessionFactoryProxy);
            DefaultProjectService projectService = new DefaultProjectService(projectDao, sqlSessionFactoryProxy);


            ServletContext ctx = sce.getServletContext();
            ctx.setAttribute("sqlSessionFactory", sqlSessionFactoryProxy);

            List<Object> controllers = new ArrayList<>();
            controllers.add(new UserController(userService));
            controllers.add(new AuthController(userService));
            controllers.add(new ProjectController(projectService, userService));
            controllers.add(new BoardController(boardService, ctx));
            controllers.add(new DownloadController(boardService, ctx));
            ctx.setAttribute("controllers", controllers);

        } catch (Exception e) {
            System.out.println("객체 준비중 오류가 발생하였습니다 contextInitialized");
            e.printStackTrace();
        }
    }
}
