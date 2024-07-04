package study.patterns.ex01.step6;

public abstract class Car {
  String maker;
  String model;
  int cc;
  @Override
  public String toString() {
    return "Car [maker=" + maker + ", model=" + model + ", cc=" + cc + "]";
  }

  //템플릿 메서드
  public void play()
  {
    start();
    run();
    stop();
  }
  //내부에서 호출하기위해서 프로텍티드를 쓴다
  protected abstract void start();
  protected abstract void run();
  protected abstract void stop();
}
