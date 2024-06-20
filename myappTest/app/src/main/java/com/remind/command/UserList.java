package com.remind.command;

import com.remind.vo.User;

public class UserList {
    static int MAX_SIZE = 100;
    public static User[] users = new User[MAX_SIZE];
    static  int userLength = 0;

    public static void add(User user)
    {
        users[userLength++] = user;
    }

    public static User delete(int userNo)
    {
        User deleteUser = UserList.findByNo(userNo);
        if (deleteUser == null) {
            System.out.println("없는 회원 입니다");
            return null;
        }
        int index = indexOf(deleteUser);
        for (int i = index + 1; i < userLength; i++) {
            users[i - 1] = users[i];
        }
        users[--userLength] = null;
        return deleteUser;
    }

    public static User[] toArray()
    {
        User[] arr = new User[userLength];
        for (int i = 0; i < userLength; i++)
        {
            arr[i] = users[i];
        }
        return arr;
    }

    public static User findByNo(int userNo) {
       for (int i = 0 ; i < userLength; i++)
       {
           User user = users[i];
           if (user.getNo() == userNo)
           {
               return user;
           }
       }
       return null;
   }

    public static int indexOf(User user)
   {
       for (int i = 0; i< userLength; i++)
       {
           if (user == users[i])
           {
               return i;
           }
       }
   return -1;
   }
}
