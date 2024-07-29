package bitcamp.myapp.command.project;

import bitcamp.command.Command;
import bitcamp.myapp.dao.ProjectDao;
import bitcamp.myapp.vo.Project;

public class ProjectListCommand implements Command {
    private ProjectDao projectDao;


    public ProjectListCommand(ProjectDao projectDao) {
        this.projectDao = projectDao;
    }

    @Override
    public void execute(String menuName) {
        System.out.println("번호 프로젝트 기간");
        try {
            for (Project project : projectDao.list()) {
                System.out.printf("%d %s %s ~ %s\n", project.getNo(), project.getTitle(), project.getStartDate(), project.getEndDate());
            }
        } catch (Exception e) {
            System.out.println("프로젝트 목록에서 오류 발생");
        }
    }
}
