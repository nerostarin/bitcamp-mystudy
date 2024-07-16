package study.oop.lambda;

public class Test04 {

  interface InterestCalculator{
    double compute(int money);
  }

  //일반 클래스로 + 로컬클래스의 특징 이용하지 않고
  static InterestCalculator create1(double rate)
  {
    class My implements InterestCalculator{
      double rate;
      public My(double rate)
      {
        this.rate = rate;
      }
      @Override
      public double compute(int money) {
        return money + (money * rate);
      }
    }
    return new My(rate);
  }

  //일반 클래스 + 로컬클래스의 특징 이용하여
  static InterestCalculator create2(double rate)
  {
    class My implements InterestCalculator{
      @Override
      public double compute(int money) {
        return money + (money * rate);
      }
    }
    return new My();
  }

  //익명 클래스
  static InterestCalculator create3(double rate)
  {
    InterestCalculator c = new InterestCalculator() {
      @Override
      public double compute(int money) {
        return money + (money * rate);
      }
    };
    return c;
  }

  //익명 클래스에 직접대입하기
  static InterestCalculator create4(double rate)
  {
    return new InterestCalculator() {
      @Override
      public double compute(int money) {
        return money + (money * rate);
      }
    };
  }

  //람다
  static InterestCalculator create5(double rate)
  {
    InterestCalculator c = (money) ->{return money + (money * rate);};
    return c;
  }

  //람다직접 대입
  static InterestCalculator create6(double rate)
  {
    return money -> money + (money * rate);
  }


  public static void main(String[] args) {
    InterestCalculator c1 = create1(3.5);
    System.out.println(c1.compute(1000_0000));

    System.out.println(create2(0.035).compute(1000_0000));

    System.out.println(create3(0.035).compute(1000_0000));

    System.out.println(create4(0.035).compute(1000_0000));

    System.out.println(create5(0.035).compute(1000_0000));

    System.out.println(create6(0.035).compute(1000_0000));

  }

}
