package study.lang.string;

public class Test01 {
  public static void main(String[] args) {
    String s;

    s = new String("aaa");

    String s2 = new String("aaa");
    System.out.println(s == s2); //래퍼런스에 들어 있는 값을 비교한다

  }
}