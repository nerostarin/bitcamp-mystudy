package study.oop.clazz;

public class Calculator {

  private int result = 0;


  void plus( int a) {
    this.result += a ;
  }

  void minus(int a) {
    this.result -= a;
  }

  void multiple(int a) {
    this.result *= a;
  }

  void divide(int a) {
    this.result /= a;
  }

  int getResult()
  {
    return this.result = result;
  }

  void clean()
  {
    this.result = 0;
  }
  /*
   인스턴스 메서드
     int result = 0;

    void plus(Calculator instance, int a) {
    return instance.result += a;
  }

  void minus(Calculator instance,int a) {
    return instance.result-= a;
  }

  void multiple(Calculator instance,int a) {
    return instance.result *= a;
  }

  void divide(Calculator instance,int a) {
    return instance.result /= a;
  }
   * */

}
