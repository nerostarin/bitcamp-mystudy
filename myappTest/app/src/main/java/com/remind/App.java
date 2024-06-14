package com.remind;

public class App {
    static String[] mainMenu = new String[] {"회원", "팀","프로젝트","게시판","도움말","종료"};

    static String[][] memberMenu = {
            {"등록","목록","조회","변경","삭제"},
            {"등록","목록","조회","변경","삭제"},
            {"등록","목록","조회","변경","삭제"},
            {"등록","목록","조회","변경","삭제"}
    };

    public static void main(String[] args) {

        printMenu();
        String command;

        while (true) {
            try {
                command = Prompt.input("메인> ");
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

        Prompt.close();
    }

    static void processMenu(String printTitle, String[] memberMenu)
    {
        System.out.println("[" + printTitle + "]");
        printMenu2(memberMenu);

        while (true) {
            String command = Prompt.input(String.format("메인/%s> ",printTitle));
            if (command.equals("9")) {
                break;
            } else if (command.equals("menu")) {
                System.out.println("[" + printTitle + "]");
                printMenu2(memberMenu);
            }else {
                try
                {
                    int menuNo = Integer.parseInt(command);
                    String printSubTitle = getMenuTitle(menuNo, memberMenu);

                    if (printSubTitle == null) {
                        System.out.println("유효한 메뉴 번호가 아닙니다.");
                    }
                    else {
                        switch (printTitle)
                        {
                            case "회원":
                                UserCommand.executeMemberCommand(printSubTitle);
                                break;
                            case "팀":
                                TeamCommand.executeTeamCommand(printSubTitle);
                                break;
                            case "프로젝트":
                                executeProjectCommand(printSubTitle);
                                break;
                            case "게시판":
                                executeBoardCommand(printSubTitle);
                                break;
                            default :
                                System.out.printf("%s 메뉴의 명령을 처리할 수 없습니다.\n", printSubTitle);
                        }
                    }
                }catch (NumberFormatException e) {
                    System.out.println("숫자로 메뉴 번호를 입력하세요.");
                }
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

    static void executeProjectCommand(String command) {
        System.out.printf("프로젝트 %s\n", command);
    }

    static void executeBoardCommand(String command) {
        System.out.printf("게시판 %s\n", command);
    }
}
