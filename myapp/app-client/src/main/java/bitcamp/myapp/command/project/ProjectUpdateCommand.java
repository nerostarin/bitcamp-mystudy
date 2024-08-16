package bitcamp.myapp.command.project;

import bitcamp.command.Command;
import bitcamp.myapp.dao.ProjectDao;
import bitcamp.myapp.vo.Project;
import bitcamp.util.Prompt;

import java.sql.Connection;

public class ProjectUpdateCommand implements Command {
    private ProjectDao projectDao;
    private ProjectMemberHandler memberHandler;
    private Connection con;


    public ProjectUpdateCommand(ProjectDao projectDao, ProjectMemberHandler memberHandler, Connection con) {
        this.projectDao = projectDao;
        this.memberHandler = memberHandler;
        this.con = con;
    }

    @Override
    public void execute(String menuName) {
        int projectNo = Prompt.inputInt("프로젝트 번호?");
        try {
            Project project = projectDao.findBy(projectNo);
            if (project == null) {
                System.out.println("없는 프로젝트입니다.");
                return;
            }
            project.setTitle(Prompt.input("프로젝트명(%s)?", project.getTitle()));
            project.setDescription(Prompt.input("설명(%s)?", project.getDescription()));
            project.setStartDate(Prompt.inputDate("시작일(%s)? (예: 2024-01-24)", project.getStartDate()));
            project.setEndDate(Prompt.inputDate("종료일(%s)? (예: 2024-01-24)", project.getEndDate()));

            System.out.println("팀원:");
            memberHandler.deleteMembers(project);
            memberHandler.addMembers(project);
            con.setAutoCommit(false);
            projectDao.update(project);
            System.out.println("변경 했습니다.");
            con.commit();

        } catch (Exception e) {
            try {
                con.rollback();
            } catch (Exception e2) {
            }
            System.out.println("프로젝트 변경중 오류 발생");
            e.printStackTrace();
        } finally {
            try {
                con.setAutoCommit(true);
            } catch (Exception e3) {
            }
        }
    }
}
