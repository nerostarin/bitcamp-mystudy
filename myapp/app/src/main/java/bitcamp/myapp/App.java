package bitcamp.myapp;

import java.util.InputMismatchException;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        String line = "------------------------------";
        String m_title = "[팀 프로젝트 관리 시스템]";
        String m_one = "1. 회원";
        String m_two = "2. 팀";
        String m_thr = "3. 프로젝트";
        String m_fou = "4. 게시판";
        String m_fiv = "5. 도움말";
        String m_six = "6. 종료";
        String bold = "\033[1m";
        String red = "\033[31m";
        String fine = "\033[0m";
        Scanner insert = new Scanner(System.in);

        System.out.println(bold + line + fine);
        System.out.println(bold + m_title + fine);
        System.out.println(m_one);
        System.out.println(m_two);
        System.out.println(m_thr);
        System.out.println(m_fou);
        System.out.println(m_fiv);
        System.out.println(bold + red + m_six + fine);
        System.out.println(bold + line + fine);

        while (true)
        {
            System.out.print("> ");
            int num = insert.nextInt();
                switch (num) {
                    case 1:
                        System.out.println("회원");
                        break;
                    case 2:
                        System.out.println("팀");
                        break;
                    case 3:
                        System.out.println("프로젝트");
                        break;
                    case 4:
                        System.out.println("게시판");
                        break;
                    case 5:
                        System.out.println("도움말");
                        break;
                    case 6:
                        System.out.println("종료하겠습니다");
                        insert.close();
                        return;
                    default:
                        System.out.println("1~6까지의 숫자를 입력해 주세요");
                }
        }
    }
}
