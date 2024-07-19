package study.patterns.ex02.after2;

public class Test04 {
  public static void main(String[] args) {
    ContentPrinter printer0 = new ContentPrinter();
    FooterPrinter printer1 = new FooterPrinter(printer0,"꼬리말");

    printer1.print("안녕하세용");
  }
}
