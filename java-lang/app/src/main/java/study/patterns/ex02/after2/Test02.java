package study.patterns.ex02.after2;

public class Test02 {
  public static void main(String[] args) {
    ContentPrinter printer0 = new ContentPrinter();
    HeaderPrinter printer1 = new HeaderPrinter(printer0,"공지사항");
    printer1.print("안녕하세용");
  }
}
