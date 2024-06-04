package study.lang.variable;

public class Test01 {
  public static void main(String[] args) {
    byte a = -128;
    byte b = 127;

    short c = -32768;
    short d = 32767;

    int e = -214748364;
    int f = 2147483647;

    long g = -9223372036854775808L;
    long h = 9223372036854775807L;

    float i = 1.4E-45f;
    float j = 3.4028235E38f; // 7자리넘어가면 반올림

    double k = 4.9E-324;
    double l = 1.7976931348623157E308;// 15자리넘어가면 반올림

    char m = 0;
    char n = 65535;

    boolean o = true;
    boolean p = false;


  }
}
