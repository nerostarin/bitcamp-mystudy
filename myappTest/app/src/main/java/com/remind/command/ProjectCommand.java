package com.remind.command;

import com.remind.util.Prompt;
import com.remind.vo.Project;
import com.remind.vo.User;

public class ProjectCommand {
    private static final int MAX_SIZE = 100;
    private  static Project[] projects = new Project[MAX_SIZE];
    private static  int projectLength = 0;
    private static int projectNo = 0;
   public static void executeProjectCommand(String command) {
       System.out.printf("[%s]\n", command);

       switch (command)
       {
           case"등록":
               addUser();
               break;
           case"목록":
               listUser();
               break;
           case"조회":
               viewUser();
               break;
           case"변경":
               updateUser();
               break;
           case"삭제":
               deleteUser();
               break;
       }
    }

    private static void addUser() {
       Project project = new Project();
        project.setTitle(Prompt.input("포로젝트 명?"));
        project.setDescription(Prompt.input("포로젝트 명?"));
        project.setStartDate(Prompt.input("포로젝트 명?"));
        project.setDescription(Prompt.input("포로젝트 명?"));
        System.out.println("팀원:");
        addMembers(project);
        projects[projectLength++] = project;
        System.out.println("등록했습니다");
    }

    private static void listUser() {
        System.out.println("번호 프로젝트명 생성자");
       for(int i = 0; i < projectLength; i++)
       {
           Project project = projects[i];
           System.out.printf("%d %s %s ~ %s\n", i + 1, project.getTitle(), project.getStartDate(), project.getEndDate());
       }
    }

    private static void viewUser() {
       projectNo = Prompt.inputInit("프로젝트 번호?");
       if (projectNo  < 1 || projectNo > projectLength)
       {
           System.out.println("업는 프로젝트 입니다");
           return;
       }
       Project project = projects[projectNo - 1];
        System.out.printf("프로젝트: %s \n", project.getTitle());
        System.out.printf("설명: %s \n", project.getDescription());
        System.out.printf("기간: %s ~ %s\n", project.getStartDate(), project.getEndDate());
        System.out.println("팀원 :");
        for (int i = 0; i < project.countMembers(); i++)
        {
            User user = project.getMember(i);
            System.out.printf("- %s \n",user.getName());
        }
    }

    private static void updateUser() {
        projectNo = Prompt.inputInit("프로젝트 번호?");
        if (projectNo  < 1 || projectNo > projectLength)
        {
            System.out.println("업는 프로젝트 입니다");
            return;
        }
        Project project = projects[projectNo - 1];
        project.setTitle(Prompt.input("프로젝트명 (%s)?", project.getTitle()));
        project.setDescription(Prompt.input("설명 (%s)?", project.getDescription()));
        project.setStartDate(Prompt.input("시작일? "));
        project.setEndDate(Prompt.input("종료일? "));

        System.out.println("팀원:");
        deleteMember(project);
        addMembers(project);
        System.out.println("변경되었습니다");
    }

    private static void deleteUser() {
        projectNo = Prompt.inputInit("프로젝트 번호?");
        if (projectNo  < 1 || projectNo > projectLength)
        {
            System.out.println("업는 프로젝트 입니다");
            return;
        }
        for(int i = projectNo; i < projectLength; i++)
        {
            projects[i-1] = projects[i];
        }
        projects[projectLength--] = null;

    }

    private static void addMembers(Project project)
    {
        while (true)
        {
            int userNo = Prompt.inputInit("추가할 팀원 번호?(종료: 0)");
            if (userNo == 0)
            {
                break;
            }
            User user = UserCommand.findByNo(userNo);
            if(user == null)
            {
                System.out.println("없는 팀원 입니다.");
                continue;
            }
            if (project.containMember(user))
            {
                System.out.printf("%s는 이미 있는 팀원 입니다\n", user.getName());
                continue;
            }
            project.addMember(user);
            System.out.printf("%s를 추가했습니다\n", user.getName());
        }
    }

    private static void deleteMember(Project project)
    {
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
