package com.remind;

import java.util.Scanner;

public class App {
   static String[] menus = new String[] {"회원", "팀", "프로젝트", "게시판", "도움말", "종료"};
   static Scanner insert = new Scanner(System.in);

    public static void main(String[] args) {

        printMenu();
        String command;
        while (true) {
            try {
                command = prompt();
                if(command.equals("menu"))
                {
                    printMenu();
                } else {
                    int num = Integer.parseInt(command);
                    String printTitle = printMenuTitle(num);
                    if (printTitle == null)
                    {
                        System.out.println("해당되는 숫자가 없습니다");
                    } else if (printTitle.equals("종료")) {
                        System.out.println("종료하겠습니다");
                        break;
                    }else {
                        System.out.println(printTitle);
                    }
                }

            }catch (Exception e)
            {
                System.out.println("해당되는 문자열이 없습니다");
            }
        }
        insert.close();
    }
    static String printMenuTitle(int num)
    {
        return isValidateMenu(num) ? menus[num-1] : null;
    }

    static boolean isValidateMenu(int num)
    {
        return num >= 1 && num <= menus.length;
    }

    static String prompt()
    {
        System.out.print("> ");
        return insert.nextLine();
    }

    static void printMenu()
    {
        String line = "------------------------------";
        String m_title = "[팀 프로젝트 관리 시스템]";
        String bold = "\033[1m";
        String red = "\033[31m";
        String fine = "\033[0m";

        System.out.println(bold + line + fine);
        System.out.println(bold + m_title + fine);

        for (int i = 0; i< menus.length; i++) {
            if (menus[i].equals("종료")) {
                System.out.printf("%s%d. %s%s\n" ,(bold + red) , (i+1), menus[i], fine);
            }else {
                System.out.printf("%d. %s\n", (i+1), menus[i]);
            }
        }

        System.out.println(bold + line + fine);
    }
}
