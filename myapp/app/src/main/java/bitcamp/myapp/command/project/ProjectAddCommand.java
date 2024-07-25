package bitcamp.myapp.command.project;

import bitcamp.myapp.command.Command;
import bitcamp.myapp.dao.ProjectDao;
import bitcamp.myapp.vo.Project;
import bitcamp.util.Prompt;

public class ProjectAddCommand implements Command {

    private ProjectDao projectDao;
    private ProjectMemberHandler memberHandler;


    public ProjectAddCommand(ProjectDao projectDao, ProjectMemberHandler memberHandler) {
        this.projectDao = projectDao;
        this.memberHandler = memberHandler;
    }

    @Override
    public void execute(String menuName) {
        Project project = new Project();
        project.setTitle(Prompt.input("프로젝트명?"));
        project.setDescription(Prompt.input("설명?"));
        project.setStartDate(Prompt.input("시작일?"));
        project.setEndDate(Prompt.input("종료일?"));

        System.out.println("팀원:");
        memberHandler.addMembers(project);

        project.setNo(Project.getNextSeqNo());

        try {
            projectDao.insert(project);
        } catch (Exception e) {
            System.out.println("회원 등록중 오류 발생");
        }

        System.out.println("등록했습니다.");
    }
}
