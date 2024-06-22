package com.remind.command;

import com.remind.vo.User;

public class UserList extends ArrayList {

    public User findByNo(int userNo) {
       for (int i = 0 ; i < size(); i++)
       {
           User user =  (User) get(i);
           System.out.println("여기");
           if (user.getNo() == userNo)
           {
               System.out.println("여기2");

               return user;
           }
       }
       return null;
   }
}
