package com.remind;

import java.util.InputMismatchException;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        String line = "------------------------------";
        String m_title = "[팀 프로젝트 관리 시스템]";
        String[] menu = {"회원","팀","프로젝트","게시판","도움말","종료"};
        String bold = "\033[1m";
        String red = "\033[31m";
        String fine = "\033[0m";
        Scanner insert = new Scanner(System.in);

        System.out.println(bold + line + fine);
        System.out.println(bold + m_title + fine);
        for (int i = 0; i< menu.length; i++){
            if(menu[i] == "종료")
            {
                System.out.printf("%s%s%d. %s%s\n",bold,red,i+1,menu[i],fine);
            }else
            {
                System.out.printf("%d. %s\n",i+1,menu[i]);

            }
        }
        System.out.println(bold + line + fine);

        while (true)
        {
            System.out.print("> ");
            try
            {
                int num = insert.nextInt();
               if(num >= 1 && num <= menu.length)
               {
                   if (menu[num-1] == "종료")
                   {
                       System.out.println("종료하겠습니다");
                       break;
                   }
                   System.out.println(menu[num-1]);
               }else
               {
                   System.out.println("1에서부터 6까지의 숫자를 입력해 주세요");
               }
            }catch (InputMismatchException e)
            {
                    System.out.println("문자는 아니되오");
                insert.nextLine(); //남아있는 문자 값을 읽어들여서 exception이 일어 나지 않게 한다
            }
        }

//        Scanner insert1 = new Scanner(System.in);
//        Scanner insert2 = new Scanner(System.in);
//        System.out.println("애너 그램인지 뭔지를 만들어 보자");
//        String word1 = insert1.nextLine();
//        String word2 = insert2.nextLine();
//        int cnt = 0;
//
//        if (word1.length() == word2.length())
//        {
//            for (int i=0; i<word1.length(); i++)
//            {
//                for (int x=0; x<word2.length(); x++)
//                {
//                    if(word1.substring(i,i+1).equals(word2.substring(x,x+1)))
//                    {
//                        cnt += 1;
//                    }
//                }
//            }
//            if (word1.length() == cnt)
//            {
//                System.out.println("애너그램인 것이야");
//            }
//            else
//            {
//                System.out.println("애너그램이 아닌것이야");
//            }
//        }else
//        {
//            System.out.println("애초에 글자수가 안 맞잔아 애너그램이 아닌것이야");
//        }


    }
}
