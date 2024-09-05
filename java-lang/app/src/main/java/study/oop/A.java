package study.oop;

public class A {
  public void m1() {
    this.m2();
  }

  public void m2() {
    System.out.println("출력했습니다 m2");
  }
}
