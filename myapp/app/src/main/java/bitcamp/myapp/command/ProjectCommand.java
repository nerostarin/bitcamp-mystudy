package bitcamp.myapp.command;

import bitcamp.myapp.util.Prompt;
import bitcamp.myapp.vo.Project;
import bitcamp.myapp.vo.User;

public class ProjectCommand {
    private static int projectNo = 0;

    public static void executeProjectCommand(String command) {
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

    private static void addProject() {
        Project project = new Project();
        project.setTitle(Prompt.input("프로젝트명?"));
        project.setDescription(Prompt.input("설명?"));
        project.setStartDate(Prompt.input("시작일?"));
        project.setEndDate(Prompt.input("종료일?"));
        project.setNo(Project.getNextSeqNo());
        System.out.println("팀원:");
        addMembers(project);
        ProjectList.add(project);
        System.out.println("등록했습니다");

    }

    private static void listProject() {
        System.out.println("번호 프로젝트명 생성자");
        for (Project project : ProjectList.toArray()) {
            System.out.printf("%d %s %s ~ %s\n", project.getNo(), project.getTitle(), project.getStartDate(), project.getEndDate());
        }
    }

    private static void viewProject() {
        projectNo = Integer.parseInt(Prompt.input("프로젝트 번호?"));
        Project project = ProjectList.findByProject(projectNo);
        if (project == null) {
            System.out.println("없는 프로젝트입니다.");
            return;
        }
        System.out.printf("프로젝트: %s \n", project.getTitle());
        System.out.printf("설명: %s \n", project.getDescription());
        System.out.printf("기간: %s ~ %s\n", project.getStartDate(), project.getEndDate());
        System.out.println("팀원 :");
        for (int i = 0; i < project.countMembers(); i++) {
            User user = project.getMember(i);
            System.out.printf("- %s\n", user.getName());
        }
    }

    private static void updateProject() {
        projectNo = Integer.parseInt(Prompt.input("프로젝트 번호?"));
        Project project = ProjectList.findByProject(projectNo);
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

    private static void deleteProject() {
        projectNo = Integer.parseInt(Prompt.input("프로젝트 번호?"));
        Project deletedProject = ProjectList.delete(projectNo);
        if (deletedProject == null) {
            System.out.printf("%s 삭제 했습니다", deletedProject.getTitle());
        }
        System.out.println("삭제 했습니다");
    }

    private static void addMembers(Project project) {
        while (true) {
            int userNo = Prompt.inputInt("추가할 팀원 번호?(종료: 0)");
            if (userNo == 0) {
                break;
            }
            User user = UserList.findByNo(userNo);
            if (user == null) {
                System.out.println("없는 팀원 입니다.");
                continue;
            }
            if (project.containsMember(user)) {
                System.out.printf("'%s'는 현재 팀원입니다.\n", user.getName());
                continue;
            }

            project.addMember(user);
            System.out.printf("'%s'를 추가했습니다.\n", user.getName());
        }
    }

    private static void deleteMembers(Project project) {
        for (int i = project.countMembers() - 1; i >= 0; i--) {
            User user = project.getMember(i);
            String str = Prompt.input("팀원 (%s) 삭제?", user.getName());
            //대소문자 제외하고 y면 삭제
            if (str.equalsIgnoreCase("y")) {
                project.deleteMember(i);
                System.out.printf("%s 팀원을 삭제합니다\n", user.getName());
            } else {
                System.out.printf("%s 팀원을 유지합니다\n", user.getName());
            }
        }
    }

}
