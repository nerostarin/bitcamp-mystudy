package bitcamp.myapp.command;

import bitcamp.myapp.util.Prompt;
import bitcamp.myapp.vo.Project;
import bitcamp.myapp.vo.User;

public class ProjectCommand {
    private static int projectNo = 0;
    ProjectList projectList = new ProjectList();
    UserList userList;

    public ProjectCommand(UserList userList) {
        this.userList = userList;
    }

    public void executeProjectCommand(String command) {
        System.out.printf("[%s]\n", command);
        switch (command) {
            case "등록":
                addProject();
                break;

            case "조회":
                viewProject();
                break;

            case "목록":
                listProject();
                break;

            case "변경":
                updateProject();
                break;

            case "삭제":
                deleteProject();
                break;
        }
    }

    private void addProject() {
        Project project = new Project();
        project.setTitle(Prompt.input("프로젝트명?"));
        project.setDescription(Prompt.input("설명?"));
        project.setStartDate(Prompt.input("시작일?"));
        project.setEndDate(Prompt.input("종료일?"));
        project.setNo(Project.getNextSeqNo());
        System.out.println("팀원:");
        addMembers(project);
        projectList.add(project);
        System.out.println("등록했습니다");

    }

    private void listProject() {
        System.out.println("번호 프로젝트명 생성자");
        for (Object obj : projectList.toArray()) {
            Project project = (Project) obj;
            System.out.printf("%d %s %s ~ %s\n", project.getNo(), project.getTitle(), project.getStartDate(), project.getEndDate());
        }
    }

    private void viewProject() {
        projectNo = Integer.parseInt(Prompt.input("프로젝트 번호?"));
        Project project = projectList.findByProject(projectNo);
        if (project == null) {
            System.out.println("없는 프로젝트입니다.");
            return;
        }
        System.out.printf("프로젝트: %s \n", project.getTitle());
        System.out.printf("설명: %s \n", project.getDescription());
        System.out.printf("기간: %s ~ %s\n", project.getStartDate(), project.getEndDate());
        System.out.println("팀원 :");
        for (int i = 0; i < project.getMembers().size(); i++) {
            User user = (User) project.getMembers().get(i);
            System.out.printf("- %s\n", user.getName());
        }
    }

    private void updateProject() {
        projectNo = Integer.parseInt(Prompt.input("프로젝트 번호?"));
        Project project = projectList.findByProject(projectNo);
        if (project == null) {
            System.out.println("없는 프로젝트입니다.");
            return;
        }
        project.setTitle(Prompt.input("프로젝트명 (%s)?", project.getTitle()));
        project.setDescription(Prompt.input("설명 (%s)?", project.getDescription()));
        project.setStartDate(Prompt.input("시작일? "));
        project.setEndDate(Prompt.input("종료일? "));

        System.out.println("팀원:");
        deleteMembers(project);
        addMembers(project);
        System.out.println("변경되었습니다");
    }

    private void deleteProject() {
        projectNo = Integer.parseInt(Prompt.input("프로젝트 번호?"));
        Project deletedProject = projectList.findByProject(projectNo);
        if (deletedProject != null) {
            projectList.remove(projectList.indexOf(projectNo));
            System.out.printf("%s 삭제 했습니다", deletedProject.getTitle());
        }
        System.out.println("삭제 했습니다");
    }

    private void addMembers(Project project) {
        while (true) {
            int userNo = Prompt.inputInt("추가할 팀원 번호?(종료: 0)");
            if (userNo == 0) {
                break;
            }
            User user = userList.findByNo(userNo);
            if (user == null) {
                System.out.println("없는 팀원 입니다.");
                continue;
            }
            if (project.getMembers().contains(user)) {
                System.out.printf("'%s'는 현재 팀원입니다.\n", user.getName());
                continue;
            }

            project.getMembers().add(user);
            System.out.printf("'%s'를 추가했습니다.\n", user.getName());
        }
    }

    private void deleteMembers(Project project) {
        for (int i = 0; i < project.getMembers().size(); i++) {
            User user = (User) project.getMembers().get(i);
            String str = Prompt.input("팀원 (%s) 삭제?", user.getName());
            //대소문자 제외하고 y면 삭제
            if (str.equalsIgnoreCase("y")) {
                project.getMembers().remove(i);
                System.out.printf("%s 팀원을 삭제합니다\n", user.getName());
            } else {
                System.out.printf("%s 팀원을 유지합니다\n", user.getName());
            }
        }
    }

}
