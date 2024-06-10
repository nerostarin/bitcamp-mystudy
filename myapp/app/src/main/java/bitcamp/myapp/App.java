package bitcamp.myapp;

import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        String line = "------------------------------";
        String m_title = "[팀 프로젝트 관리 시스템]";
        String bold = "\033[1m";
        String red = "\033[31m";
        String fine = "\033[0m";
        Scanner insert = new Scanner(System.in);
        String[] menus = new String[] {"회원", "팀", "프로젝트", "게시판", "도움말", "종료"};

        System.out.println(bold + line + fine);
        System.out.println(bold + m_title + fine);

        for (int i = 0; i< menus.length; i++) {
            if (menus[i] == ("종료")) {
                System.out.printf("%s%d. %s%s\n" ,(bold + red) , (i+1), menus[i], fine);
            }else {
                System.out.printf("%d. %s\n", (i+1), menus[i]);
            }
        }

        System.out.println(bold + line + fine);

        while (true) {
            try {
                System.out.print("> ");
                int num = insert.nextInt();

                if (num >= 1 && num <= menus.length) {
                    if (menus[num - 1] == "종료") {
                        System.out.println("종료합니다.");
                        break;
                    }
                    System.out.println(menus[num - 1]);
                } else {
                    System.out.println("유효한 메뉴 번호가 아닙니다");
                }
            }catch (Exception e)
            {
                System.out.println("숫자로 메뉴번호를 입력하세요");
                insert.nextLine();
                /*insert.nextLine();은 입력 버퍼에 남아 있는 잘못된 입력을 제거하는 역할을 합니다.
                예외 발생 후 nextLine()을 호출하면, 현재 입력 라인을 읽고 버립니다.
                이렇게 하면 다음 입력을 받을 때 버퍼가 비어 있어 정상적인 입력을 받을 수 있습니다.*/
            }
        }
        insert.close();
    }
}