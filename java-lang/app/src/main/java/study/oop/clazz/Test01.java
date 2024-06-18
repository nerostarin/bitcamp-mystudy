package study.oop.clazz;

import study.oop.clazz.util.Calculator;

public class Test01 {
  //1.메서드 분류 0
  //2.static 필드 사용 0
  //3.non static 필드 사용 0
  //4.인스턴스 메서드 사용 0
  //5.private, getter사용 0
  //6.인스턴스 메서드 사용: clear 0

  public static void main(String[] args) {
    Calculator c1 = new Calculator();

    c1.plus(2);
    c1.plus(3);
    c1.minus(1);
    c1.multiple(7);
    c1.divide(3);

    System.out.printf("result = %d\n", c1.getResult());

    c1.clean();

    c1.plus(3);
    c1.plus(4);
    c1.minus(2);
    c1.multiple(8);
    c1.divide(4);

    System.out.printf("result = %d\n", c1.getResult());
  }

}

/*
    Calculator c1 = new Calculator();
    Calculator.plus(c1,2);
    Calculator.plus(c1,3);
    Calculator.minus(c1,1);
    Calculator.multiple(c1,7);
    Calculator.divide(c1,3);

    System.out.printf("result = %d\n", c1.result);
 * */
