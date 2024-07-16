package study.oop.lambda;

public class Test03 {
  interface Calculator
  {
    int plus(int a, int b);
  }

  static void test (Calculator c)
  {
    System.out.println(c.plus(100, 200));
  }

  public static void main(String[] args) {
    //일반클래스
    class MyCalc implements Calculator
    {
      @Override
      public int plus(int a, int b) {
        return a+b;
      }
    }
    Calculator c1 = new MyCalc();
    test(c1);

    //익명 클래스
    Calculator c2 = new Calculator() {

      @Override
      public int plus(int a, int b) {
        return a+b;
      }
    };
    test(c2);

    //익명클래스 직접 대입
    test(new Calculator() {
      @Override
      public int plus(int a, int b)
      {return a+b;}
    }
        );

    //람다
    Calculator c3 =(a,b) -> {return a+b;};
    test(c3);

    //람다 중괄호 생략
    Calculator c4 =(a,b) -> a+b;
    test(c4);

    //람다 직접 대입
    test((a,b)->a+b);

  }
}
