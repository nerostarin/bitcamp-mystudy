// Byte Stream - 텍스트 출력 하기
package study.io.ex02;

import java.io.FileOutputStream;

public class Exam0514 {

  public static void main(String[] args) throws Exception {

    String str = new String("AB가각");

    // String 객체의 데이터를 출력하려면
    // 문자열을 담은 byte[] 배열을 리턴 받아야 한다.

    // => UTF-8로 인코딩 하기
    System.out.printf("file.encoding=%s\n", System.getProperty("file.encoding"));
    byte[] bytes = str.getBytes("UTF-8"); // UCS2 ==> UTF-8

    for (byte b : bytes) {
      System.out.printf("%x ", b);
    }
    System.out.println();

    // 바이트 배열 전체를 그대로 출력한다.
    FileOutputStream out = new FileOutputStream("temp/utf8.txt");
    out.write(bytes);
    out.close();
    System.out.println("데이터 출력 완료!");
  }

}

