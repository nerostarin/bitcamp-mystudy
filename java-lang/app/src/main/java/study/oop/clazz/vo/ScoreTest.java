package study.oop.clazz.vo;

public class ScoreTest {
  private String name;
  private int kor;
  private int eng;
  private int math;
  private int sum;
  private float aver;

  public void compute()
  {
    this.sum = this.kor + this.eng + this.math;
    this.aver = (float) this.sum / 3;
  }

  public ScoreTest(String name, int kor, int eng, int math)
  {
    this.name = name;
    this.kor = kor;
    this.eng = eng;
    this.math = math;
    this.compute(); //가져온 변수로 계산하게끔
  }

  public int getSum() {
    return this.sum = sum;
  }

  public float getAver() {
    return this.aver = aver;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    compute();
    this.name = name;
  }

  public int getKor() {
    return kor;
  }

  public void setKor(int kor) {
    compute();
    this.kor = kor;
  }

  public int getEng() {
    return eng;
  }

  public void setEng(int eng) {
    compute();
    this.eng = eng;
  }

  public int getMath() {
    return math;
  }

  public void setMath(int math) {
    compute();
    this.math = math;
  }

  public void setSum(int sum) {
    compute();
    this.sum = sum;
  }

  public void setAver(float aver) {
    compute();
    this.aver = aver;
  }


}
