package study.patterns.ex02.before;

public class Printer6 extends Printer5{
  String header;

  public Printer6(String sign, String header) {
    super(sign);
    this.header = header;
  }

  @Override
  void Print(String content)
  {
    System.out.printf("[%s]--------------------------\n",header);
    super.Print(content);

  }
}
