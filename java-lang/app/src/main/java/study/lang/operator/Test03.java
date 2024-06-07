package study.lang.operator;

public class Test03 {

  // 같은 타입끼리만 연산할 수 있다
  // 타입이 다르면 같은 타입으로 변환한다 이걸 암시적 형변환이라한다
  // 개발자가 명시적으로 타입을 변환할수있다

  public static void main(String[] args) {
    byte b = 1;
    char c = 2;
    short s = 3;
    int i = 4;
    long l = 5;
    float f = 6.0f;
    double d = 6.0;

    int r = b + c + s;
    long r2 = i + l;
    // long r3 = f;
    float r4 = l; // 값이 잘릴수도 있다 에러가 아니기에 치명적이다



  }

}
