package study.patterns.ex02.after2;

public class Test03 {
  public static void main(String[] args) {
    ContentPrinter printer0 = new ContentPrinter();
    HeaderPrinter printer1 = new HeaderPrinter(printer0,"인사말");
    FooterPrinter printer2 = new FooterPrinter(printer1,"꼬리말");

    printer2.print("안녕하세용");
  }
}
