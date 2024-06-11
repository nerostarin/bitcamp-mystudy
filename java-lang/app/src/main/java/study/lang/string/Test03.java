package study.lang.string;

import java.util.Scanner;

public class Test03 {
  public static void main(String[] args) {
    Scanner keboard = new Scanner(System.in);

    System.out.print("입력1: ");
    String s1 = keboard.nextLine(); //사용자가 입력한 문자열을 저장한 string 인스턴스를 새로만들고 그 주소를 리턴한다

    System.out.print("입력2: ");
    String s2 = keboard.nextLine();

    System.out.println(s1 == s2); //래퍼런스에 저장된 주소를 비교 즉 래퍼런스가 가리키는 인스턴스가 같은지 비교한다
    System.out.println(s1.equals(s2)); // s1 인스턴스에 저장된 문자열 s2인스턴스에 저장된 문자열을 비교한다

    keboard.close();

  }
}