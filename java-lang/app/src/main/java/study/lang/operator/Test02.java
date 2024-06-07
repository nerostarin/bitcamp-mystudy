package study.lang.operator;

public class Test02 {

  //
  public static void main(String[] args) {
    byte b1 = 100;
    byte b2 = 20;

    byte b3 = (byte) (b1 + b2);
    System.out.println(b3);

    int r = b1 + b2;

    char c = 20;
    short s = 30;
    // short r2 = c + s; short도 작은 값에 안들어 간다

    int i1 = 21_0000_0000;
    int i2 = 21_0000_0000;
    int i3 = i1 + i2;

    System.out.println(i3);

    long r3 = i1 + i2;
    System.out.println(r3);// int와 int는 계산하면 int이기 때문에 오버플로우가 발생하여 -값이 들어간다


  }

}
