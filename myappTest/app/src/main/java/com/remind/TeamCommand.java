package com.remind;

public class TeamCommand {

    static int MAX_SIZE = 100;
    static Team[] teams = new Team[MAX_SIZE];
    static int userNo = 0;
    static int teamCount = 0;
    public static void executeTeamCommand(String command) {
        System.out.printf("[%s]\n", command);
        switch (command) {
            case "등록":
                addTeam();
                break;
            case "목록":
                listTeam();
                break;
            case "조회":
                viewTeam();
                break;
            case "변경":
                updateTeam();
                break;
            case "삭제":
                deleteTeam();
                break;
        }
    }
     static void addTeam() {
        Team team = new Team();
        team.teamMember = new String[MAX_SIZE];
        team.teamName = Prompt.input("팀명? ");
         int count = 0;
         while (true) {
             int userNo = Integer.parseInt(Prompt.input("추가할 팀원 번호? (종료: 0) "));
             if (userNo == 0) {
                 System.out.println("팀 등록을 완료했습니다.");
                 teams[teamCount++] = team; // 팀 등록 후 teams 배열에 추가
                 break;
             } else if (userNo < 1 || userNo > UserCommand.userLength) {
                 System.out.println("잘못된 회원 번호입니다. 다시 입력해 주세요.");
                 continue;
             }
             User user = UserCommand.users[userNo - 1];

             boolean alreadyAdded = false;
             for (int i = 0; i < count; i++) {
                 if (team.teamMember[i] != null && team.teamMember[i].equals(user.name)) {
                     System.out.println(user.name + "은 이미 추가되었습니다.");
                     alreadyAdded = true;
                     break;
                 }
             }

             if (alreadyAdded) {
                 continue;
             }

             System.out.printf("'%s'을 추가했습니다.\n", user.name);
             team.teamMember[count++] = user.name;
             team.memberCount = count;
             teams[teamCount] = team;
         }
    }

     static void listTeam() {
            System.out.println("번호 팀명");
            for (int i = 0; i < teamCount; i++) {
                Team team = teams[i];
                System.out.printf("%d %s\n", i + 1, team.teamName);
        }
    }

     static void viewTeam() {
        userNo = Integer.parseInt(Prompt.input("팀 번호? "));
        Team team = teams[userNo - 1];
        if (userNo < 1 || userNo > team.memberCount)
        {
            System.out.println("없는 번호입니다");
            return;
        }
         System.out.printf("팀명: %s\n",team.teamName);
         System.out.println("팀원");
         for (int i = 0; i < team.memberCount; i++ )
         {
             System.out.printf("-%s \n",team.teamMember[i]);
         }
     }

    static void updateTeam() {
        int teamNo = Integer.parseInt(Prompt.input("팀 번호? "));
        if (teamNo < 1 || teamNo > teamCount) {
            System.out.println("없는 번호입니다");
            return;
        }
        Team team = teams[teamNo - 1];
        team.teamName = Prompt.input(String.format("팀명 (%s): ", team.teamName));

        // 팀원 삭제 처리
        for (int i = 0; i < team.memberCount; i++) {
            String response = Prompt.input(String.format("팀원(%s) 삭제? (y/n): ", team.teamMember[i]));
            if (response.equalsIgnoreCase("y")) {
                System.out.printf("%s 팀원을 삭제합니다 \n", team.teamMember[i]);
                for (int j = i; j < team.memberCount - 1; j++) {
                    team.teamMember[j] = team.teamMember[j + 1];
                }
                team.teamMember[team.memberCount - 1] = null;
                team.memberCount--;
                i--; // 현재 인덱스를 다시 검사하도록 설정
            } else {
                System.out.printf("%s 팀원을 유지합니다 \n", team.teamMember[i]);
            }
        }

        // 팀원 추가 처리
        while (true) {
            int userNo = Integer.parseInt(Prompt.input("추가할 팀원 번호? (종료: 0) "));
            if (userNo == 0) {
                System.out.println("팀 등록을 완료했습니다.");
                break;
            } else if (userNo < 1 || userNo > UserCommand.userLength) {
                System.out.println("잘못된 회원 번호입니다. 다시 입력해 주세요.");
                continue;
            }
            User user = UserCommand.users[userNo - 1];

            boolean alreadyAdded = false;
            for (int i = 0; i < team.memberCount; i++) {
                if (team.teamMember[i] != null && team.teamMember[i].equals(user.name)) {
                    System.out.println(user.name + "은 이미 추가되었습니다.");
                    alreadyAdded = true;
                    break;
                }
            }

            if (alreadyAdded) {
                continue;
            }

            System.out.printf("'%s'을 추가했습니다.\n", user.name);
            team.teamMember[team.memberCount++] = user.name;
        }
    }

    static void deleteTeam() {
        int teamNo = Integer.parseInt(Prompt.input("팀 번호? "));
        if (teamNo < 1 || teamNo > teamCount) {
            System.out.println("없는 번호입니다");
            return;
        }

        for (int i = teamNo - 1; i < teamCount - 1; i++) {
            teams[i] = teams[i + 1];
        }
        teams[--teamCount] = null; // 팀 개수 감소 및 마지막 팀을 null로 설정
        System.out.println("삭제했습니다.");
    }
}