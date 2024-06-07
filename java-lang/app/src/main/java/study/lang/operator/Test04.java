package study.lang.operator;

public class Test04 {

  // 연산자 우선순위
  // 0) ()
  // 1)* / %
  // 2)+ -
  // 우선순위가 같은경우 먼저 나온 연사자를 먼저 계산한다
  public static void main(String[] args) {

    System.out.println(3 + 5 * 2);
    System.out.println(5 * 2 + 3);
    System.out.println(5 * (2 + 3));

    // 암시적 형변환과 연산자 우선순위
    System.out.println(3.2f + 5 / 2l);

    // 명시적 형변환 + 연산자 우선순위
    System.out.println(3.2f + (float) 5 / 2l);

  }

}
