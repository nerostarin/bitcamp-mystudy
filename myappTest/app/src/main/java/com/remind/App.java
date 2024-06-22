package com.remind;

import com.remind.command.BoardCommand;
import com.remind.command.ProjectCommand;
import com.remind.command.UserCommand;
import com.remind.util.Prompt;

public class App {
    static String[] mainMenu = new String[] {"회원", "프로젝트","게시판", "공지사항","도움말","종료"};

    static String[][] memberMenu = {
            {"등록","목록","조회","변경","삭제"},
            {"등록","목록","조회","변경","삭제"},
            {"등록","목록","조회","변경","삭제"},
            {"등록","목록","조회","변경","삭제"}
    };

     UserCommand userCommand = new UserCommand();
     ProjectCommand projectCommand = new ProjectCommand(userCommand.getUserList());
     BoardCommand boardCommand = new BoardCommand();
     BoardCommand noticeCommand = new BoardCommand();



    public static void main(String[] args) {
        new App().execute();
    }

    void execute()
    {
        printMenu();
        String command;

        while (true) {
            try {
                command = Prompt.input("메인> ", "");
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
                        processMenu(printTitle, memberMenu[menuNo-1]);
                    }
                }
            } catch (NumberFormatException ex) {
                System.out.println("숫자로 메뉴 번호를 입력하세요.");
            }
        }

        System.out.println("종료합니다.");

        Prompt.close();
    }

    void processMenu(String printTitle, String[] memberMenu)
    {
        System.out.println("[" + printTitle + "]");
        printMenu2(memberMenu);

        while (true) {
            String command = Prompt.input("메인/%s> ",printTitle);
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
                                userCommand.executeMemberCommand(printSubTitle);
                                break;
                            case "프로젝트":
                                projectCommand.executeProjectCommand(printSubTitle);
                                break;
                            case "게시판":
                                boardCommand.executeBoardCommand(printSubTitle);
                                break;
                            case "공지사항":
                                noticeCommand.executeBoardCommand(printSubTitle);
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
    String getMenuTitle(int menuNo, String[] menus)
    {
        return isValidateMenu(menuNo, menus) ? menus[menuNo-1] : null;
    }

    boolean isValidateMenu(int menuNo, String[] menus)
    {
        return menuNo >= 1 && menuNo <= menus.length;
    }

    void printMenu()
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

    void printMenu2(String[] menus)
    {
        for (int i = 0; i < menus.length; i++) {
            System.out.printf("%d. %s\n", (i + 1), menus[i]);
        }
        System.out.println("9. 이전");
    }
}
