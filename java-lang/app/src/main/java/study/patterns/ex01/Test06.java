package study.patterns.ex01;

import study.patterns.ex01.step6.Car;
import study.patterns.ex01.step6.CarFactory;
import study.patterns.ex01.step6.K7Factory;
import study.patterns.ex01.step6.SonataFactory;
//
public class Test06 {
  public static void main(String[] args) {
    SonataFactory sonataFactory = SonataFactory.getInstance();
    K7Factory k7Factory = K7Factory.getInstance();
    playCar(sonataFactory);
    playCar(k7Factory);
  }

  static void playCar( CarFactory carFactory)
  {
    Car car = carFactory.createCar();
    car.play();
  }
}
