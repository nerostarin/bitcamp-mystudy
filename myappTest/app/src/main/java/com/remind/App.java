package com.remind;

import java.util.Arrays;
import java.util.Scanner;

public class App {
    static String[] mainMenu = new String[] {"회원", "팀","프로젝트","게시판","도움말","종료"};

    static String[][] memberMenu = {
            {"등록a","목록","조회","변경","삭제"},
            {"등록b","목록","조회","변경","삭제"},
            {"등록c","목록","조회","변경","삭제"},
            {"등록d","목록","조회","변경","삭제"}
    };

    static java.util.Scanner keyboardScanner = new java.util.Scanner(System.in);

    public static void main(String[] args) {

        printMenu();
        String command;

        while (true) {
            try {
                command = prompt("메인");
                if (command.equals("menu")) {
                    printMenu();
                } else {

                    int menuNo = Integer.parseInt(command);
                    String printTitle = getMenuTitle(menuNo, mainMenu);

                    if (printTitle == null) {
                        System.out.println("유효한 메뉴 번호가 아닙니다.");
                    } else if(printTitle.equals("종료")){
                        break;
                    } else
                    {
                        if (menuNo >= 1 && menuNo <= 4) {
                            processMenu(printTitle, memberMenu[menuNo-1]);
                        }else {
                            System.out.println(printTitle);
                        }
                    }
                }
            } catch (NumberFormatException ex) {
                System.out.println("숫자로 메뉴 번호를 입력하세요.");
            }
        }

        System.out.println("종료합니다.");

        keyboardScanner.close();
    }

    static void processMenu(String printTitle, String[] memberMenu)
    {
        System.out.println("[" + printTitle + "]");
        printMenu2(memberMenu);

        while (true) {
            String command = prompt("메인/" + printTitle);
            if (command.equals("9")) {
                break;
            } else if (command.equals("menu")) {
                printMenu2(memberMenu);
            }else {
                try
                {
                    int menuNo = Integer.parseInt(command);
                    String printTitleMenu = getMenuTitle(menuNo, memberMenu);

                    if (printTitleMenu == null) {
                        System.out.println("유효한 메뉴 번호가 아닙니다.");
                    }else if(printTitleMenu.equals("등록a"))
                    {
                        signIn();
                    }else if(printTitleMenu.equals("목록"))
                    {
                        printUser();
                    }
                    else {
                        System.out.println(printTitleMenu);
                    }
                }catch (NumberFormatException e) {
                    System.out.println("숫자로 메뉴 번호를 입력하세요.");
                }
            }
        }
    }
    static void printUser() {
        System.out.println("번호\t이름\t이메일");
        for (int i = 1; i < User.userData.length; i++) {
            if (User.userData[i].length > 0) {
                System.out.printf("%d\t%s\t%s\n", i, User.userData[i][0], User.userData[i][1]);
            }
        }
    }
    static String getMenuTitle(int menuNo, String[] menus)
    {
        return isValidateMenu(menuNo, menus) ? menus[menuNo-1] : null;
    }

    static boolean isValidateMenu(int menuNo, String[] menus)
    {
        return menuNo >= 1 && menuNo <= menus.length;
    }

    static String prompt(String menu)
    {
        System.out.print(menu+"> ");
        return keyboardScanner.nextLine();
    }

    static void printMenu()
    {
        String boldAnsi = "\033[1m";
        String redAnsi = "\033[31m";
        String resetAnsi = "\033[0m";

        String appTitle = "[팀 프로젝트 관리 시스템]";
        String line = "----------------------------------";

        System.out.println(boldAnsi + line + resetAnsi);
        System.out.println(boldAnsi + appTitle + resetAnsi);

        for (int i = 0; i < mainMenu.length; i++) {
            if (mainMenu[i].equals("종료")) {
                System.out.printf("%s%d. %s%s\n", (boldAnsi + redAnsi), (i + 1), mainMenu[i], resetAnsi);
            } else {
                System.out.printf("%d. %s\n", (i + 1), mainMenu[i]);
            }
        }

        System.out.println(boldAnsi + line + resetAnsi);
    }

    static void printMenu2(String[] menus)
    {
        for (int i = 0; i < menus.length; i++) {
            System.out.printf("%d. %s\n", (i + 1), menus[i]);
        }
        System.out.println("9. 이전");
    }

    static void signIn() {
        System.out.printf("이름? ");
        User.name = keyboardScanner.nextLine();
        System.out.printf("이메일? ");
        User.email = keyboardScanner.nextLine();
        System.out.printf("암호? ");
        User.password = keyboardScanner.nextLine();
        System.out.printf("연락처? ");
        User.tell = keyboardScanner.nextLine();

        User.insertData();
    }
}
