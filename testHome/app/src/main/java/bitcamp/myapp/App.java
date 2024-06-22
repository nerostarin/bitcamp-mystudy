package bitcamp.myapp;

import java.util.Scanner;

public class App {
    static Scanner keyboardScanner = new Scanner(System.in);
    static String[]  mainMenus = {"회원","팀","프로젝트","게시판","도움말","종료"};
    static String[][] subMenus = {
            {"등록a", "목록", "조회", "변경", "삭제"},
            {"등록b", "목록", "조회", "변경", "삭제"},
            {"등록c", "목록", "조회", "변경", "삭제"},
            {"등록d", "목록", "조회", "변경", "삭제"}};
    public static void main(String[] args) {

        printMenu(mainMenus);

        String command;

            while (true) {
                try {
                    command = Prompt("매인");

                if (command.equals("menu"))
                {
                    printMenu(mainMenus);
                }else
                {
                    int menuNo = Integer.parseInt(command);
                    String menuTitle = getMenuTitle(menuNo , mainMenus);
                    if (menuTitle == null)
                    {
                        System.out.println("없는 번호에요");
                    }else if (menuTitle.equals("종료"))
                    {
                        break;
                    }else
                    {
                        if (menuNo >= 1 && menuNo <= 4)
                        {
                            proceesMenu(menuTitle, subMenus[menuNo - 1]);
                        }else
                        {
                            System.out.println(menuTitle);
                        }
                    }
                }
            }catch (Exception e)
            {
                System.out.println("문자는 아니되오");
                keyboardScanner.next();
            }
            }

        System.out.println("종료하겠습니다");
        keyboardScanner.close();

    }

    static void proceesMenu(String subTitle, String[] subMenu) {
        printSubMenu(subTitle, subMenu);
        while (true) {
            String subCommand = Prompt("메인/" + subTitle);
            if (subCommand.equals("menu"))
            {
                printSubMenu(subTitle, subMenu);
            }else {
                int subNo = Integer.parseInt(subCommand);
                if(subNo == 9)
                {
                    break;
                }
                String subMenuTitle = getMenuTitle(subNo, subMenu);
                if (subMenuTitle == null)
                {
                    System.out.println("메뉴에 없는 번호에요");
                }
                else
                {
                    System.out.println(subMenuTitle);
                }
            }

        }
    }

    static void printSubMenu(String menuTitle, String[] menus)
    {
        System.out.printf("[%s]\n", menuTitle);
        for (int i = 0; i < menus.length; i++)
        {
            System.out.printf("%d. %s\n", (i + 1), menus[i]);
        }
        System.out.println("9. 이전");
    }

    static String getMenuTitle(int menuNo, String[] menus)
    {
        return isValidateMenu(menuNo, menus) ? menus[menuNo - 1] : null;
    }

    static boolean isValidateMenu(int menuNo, String[] menus){
    return menuNo >=1 && menuNo <= menus.length;
    }

    static String Prompt(String menu)
    {
        System.out.printf("%s> ", menu);
        return keyboardScanner.nextLine();
    }

    static void printMenu(String[] menu)
    {

        String boldAnsi = "\033[1m";
        String redAnsi = "\033[31m";
        String resetAnsi = "\033[0m";

        String appTitle = "[팀 프로젝트 관리 시스템]";
        String line = "----------------------------------";

        System.out.println(boldAnsi + line + resetAnsi);
        System.out.println(boldAnsi + appTitle + resetAnsi);

        for (int i =0; i < menu.length; i++)
        {
            if (menu[i].equals("종료"))
            {
                System.out.printf("%s%d. %s%s\n",(boldAnsi + redAnsi),i+1,  menu[i], resetAnsi);
            }else {
                System.out.printf("%d. %s\n",i+1,menu[i]);
            }
        }
        System.out.println(boldAnsi + line + resetAnsi);
    }
}
