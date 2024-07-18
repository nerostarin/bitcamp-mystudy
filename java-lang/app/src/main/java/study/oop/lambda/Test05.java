package study.oop.lambda;

public class Test05 {
  static class MyCalculator {
    public static int plus(int a, int b) {return a + b;}
    public static int minus(int a, int b) {return a - b;}
    public static int multiple(int a, int b) {return a * b;}
    public static int divide(int a, int b) {return a / b;}
  }

  interface Calculator
  {
    int compute(int x, int y);
  }

  public static void main(String[] args) {
    Calculator c2 = MyCalculator::minus;
    // => Calculator c2 = (x, y) -> MyCalculator.minus(x, y);

    Calculator c3 = MyCalculator::multiple;
    // => Calculator c3 = (x, y) -> MyCalculator.multiple(x, y);

    Calculator c4 = MyCalculator::divide;
    // => Calculator c4 = (x, y) -> MyCalculator.divide(x, y);
  }
}
