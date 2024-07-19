package study.patterns.ex02.before;

public class Printer4 extends Printer{
  String footer;

  public Printer4(String footer) {
    this.footer = footer;
  }

  @Override
  void Print(String content)
  {
    super.Print(content);
    System.out.printf("==========%s==========",footer);
  }
}
