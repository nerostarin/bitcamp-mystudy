package bitcamp.myapp.listener;

import bitcamp.context.ApplicationContext;
import bitcamp.listener.ApplicationListener;
import bitcamp.myapp.dao.*;
import bitcamp.myapp.dao.skel.BoardDaoSkel;
import bitcamp.myapp.dao.skel.ProjectDaoSkel;
import bitcamp.myapp.dao.skel.UserDaoSkel;

public class InitApplicationListener implements ApplicationListener {

    UserDao userDao;
    BoardDao boardDao;
    ProjectDao projectDao;

    @Override
    public void onStart(ApplicationContext ctx) throws Exception {

        userDao = new ListUserDao("data.xlsx");
        boardDao = new ListBoardDao("data.xlsx");
        projectDao = new ListProjectDao("data.xlsx", userDao);

        UserDaoSkel userDaoSkel = new UserDaoSkel(userDao);
        BoardDaoSkel boardDaoSkel = new BoardDaoSkel(boardDao);
        ProjectDaoSkel projectDaoSkel = new ProjectDaoSkel(projectDao);


        ctx.setAttribute("userDaoSkel", userDaoSkel);
        ctx.setAttribute("boardDaoSkel", boardDaoSkel);
        ctx.setAttribute("projectDaoSkel", projectDaoSkel);
    }

    @Override
    public void onShutDown(ApplicationContext ctx) throws Exception {
        try {
//                ((MapUserDao) userDao).save();
//                ((MapBoardDao) boardDao).save();
//                ((MapProjectDao) projectDao).save();
            ((ListUserDao) userDao).save();
            ((ListBoardDao) boardDao).save();
            ((ListProjectDao) projectDao).save();
        } catch (Exception e) {
            System.out.println("데이터 저장 중 오류 발생");
            e.printStackTrace();
            System.out.println();
        }
    }
}
