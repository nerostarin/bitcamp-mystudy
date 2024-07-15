package bitcamp.myapp.command.project;

import bitcamp.myapp.command.Command;
import bitcamp.myapp.vo.Project;
import bitcamp.myapp.vo.User;
import bitcamp.util.Prompt;

import java.util.List;

public class ProjectViewCommand implements Command {
    private List<Project> projectList;
    private List<User> userList;

    public ProjectViewCommand(List projectList, List userList) {
        this.projectList = projectList;
        this.userList = userList;
    }

    @Override
    public void execute(String menuName) {
        int projectNo = Prompt.inputInt("프로젝트 번호?");
        int index = projectList.indexOf(new Project(projectNo));
        if (index == -1) {
            System.out.println("없는 프로젝트입니다.");
            return;
        }

        Project project = projectList.get(index);

        System.out.printf("프로젝트명: %s\n", project.getTitle());
        System.out.printf("설명: %s\n", project.getDescription());
        System.out.printf("기간: %s ~ %s\n", project.getStartDate(), project.getEndDate());
        System.out.println("팀원:");
        for (User user : userList) {
            System.out.printf("- %s\n", user.getName());
        }
    }
}