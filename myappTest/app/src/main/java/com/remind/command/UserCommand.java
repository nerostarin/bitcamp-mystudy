package com.remind.command;

import com.remind.vo.User;
import com.remind.util.Prompt;

public class UserCommand {

    static int MAX_SIZE = 100;
    public static User[] users = new User[MAX_SIZE];
    static  int userLength = 0;
    static int userNo = 0;

    public static void executeMemberCommand(String command) {
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

    static void addUser()
    {
        User user = new User();
        user.setName(Prompt.input("이름?"));
        user.setEmail(Prompt.input("이메일?"));
        user.setPassword(Prompt.input("암호?"));
        user.setTel(Prompt.input("연락처?"));
        users[userLength++] = user;
    }

    static void listUser()
    {
        System.out.println("번호 이름 이메일");
        for (int i = 0 ; i < userLength; i++)
        {
            User user =  users[i];
            System.out.printf("%d %s %s\n", i + 1, user.getName(), user.getEmail());
        }
    }

    static void viewUser()
    {
        userNo = Integer.parseInt(Prompt.input("회원번호? "));
        if(userNo < 1 || userNo > userLength)
        {
            System.out.println("없는 회원 입니다");
            return;
        }
        User user = users[userNo - 1];
        System.out.printf("이름: %s \n", user.getName());
        System.out.printf("이메일: %s \n", user.getEmail());
        System.out.printf("전화번호: %s \n", user.getTel());
    }

    static void updateUser()
    {
        userNo = Integer.parseInt(Prompt.input("회원번호? "));
        if(userNo < 1 || userNo > userLength)
        {
            System.out.println("없는 회원 입니다");
            return;
        }
        User user = users[userNo - 1];
        user.setName(Prompt.input("이름 (%s)?", user.getName()));
        user.setEmail(Prompt.input("이메일 (%s)?", user.getEmail()));
        user.setPassword(Prompt.input("암호?"));
        user.setTel(Prompt.input("연락처 (%s)?", user.getTel()));
    }

    static void deleteUser() {
        userNo = Integer.parseInt(Prompt.input("회원번호? "));
        if (userNo < 1 || userNo > userLength) {
            System.out.println("없는 회원 입니다");
            return;
        }
        for (int i = userNo; i < userLength; i++) {
            users[i - 1] = users[i];
        }
        userLength--;
        users[userLength] = null;
        System.out.println("삭제 헸습니다");
    }

    public static User findByNo(int userNo) {
        if (userNo < 1 || userNo > userLength) {
            return null;
        }
        return users[userNo - 1];
    }
}
