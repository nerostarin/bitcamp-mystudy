package study.patterns.ex02.before;

public class Printer3 extends Printer2{
  String header;
  String footer;

  public Printer3(String header, String footer) {
    super(header);
    this.footer = footer;
  }

  @Override
  void Print(String content)
  {
    super.Print(content);
    System.out.printf("==========%s==========",footer);
  }
}
