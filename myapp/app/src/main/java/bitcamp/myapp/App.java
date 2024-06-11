package bitcamp.myapp;

import java.util.Scanner;

public class App {
    static Scanner insert = new Scanner(System.in);

    static String[] menus = new String[] {"회원", "팀", "프로젝트", "게시판", "도움말", "종료"};

    public static void main(String[] args) {
        printMenu();
        String command;
        while (true) {
            try {
                command = prompt();

                if (command.equals("menu"))
                {
                    printMenu(); //메소드의 호출
                }else
                {
                    int num = Integer.parseInt(command);
                    String menuTitle = getMenuTitle(num); //설명하는 변수
                    if (menuTitle == null) {
                        System.out.println("유효한 메뉴 번호가 아닙니다");
                    } else if (menuTitle.equals("종료")) {
                        System.out.println("종료합니다.");
                        break;
                    }else {
                        System.out.println(menuTitle);
                    }
                }
            }catch (NumberFormatException e){
                System.out.println("해당되는 문자열이 없습니다");
            }
        }
        insert.close();
    }

    static String getMenuTitle(int menuNo)
    {
        return isValidateMenu(menuNo) ? menus[menuNo-1] : null;
    /*  if(isValidateMenu(menuNo){
            return menus[menuNo-1];
        }
        return null;*/
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

    static void printMenu(){
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