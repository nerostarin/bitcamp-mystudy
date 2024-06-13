package com.remind;

import java.lang.reflect.Array;
import java.util.Arrays;

public class User {
    static String[][] userData = {
            {"홍길동", "hong@test.com", "1111", "010-1111-2222"},
            {"임꺽정", "leem@test.com", "2222", "010-2222-3333"},
            {"유관순", "yoo@test.com", "3333", "010-3333-4444"},
            {"안중근", "ahn@test.com", "4444", "010-4444-5555"},
    };
    static String name;
    static String email;
    static String password;
    static String tell;

    static void insertData() {
        if (name != null && email != null && password != null && tell != null) {
            // 새 사용자 데이터 배열 생성
            String[] newUser = {name, email, password, tell};

            // 기존 userData 배열을 확장하여 새 사용자 추가
            String[][] newUserData = new String[userData.length + 1][];
            newUserData[0] = newUser;
            System.arraycopy(userData, 0, newUserData, 1, userData.length);
            userData = newUserData;

            // 사용자 데이터 추가 후 필드 초기화
            name = null;
            email = null;
            password = null;
            tell = null;
        }
    }
}
