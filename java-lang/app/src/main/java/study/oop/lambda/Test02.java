package study.oop.lambda;

public class Test02 {
  interface Player
  {
    void play();
  }

  public static void main(String[] args) {
    //일반 클래스
    class MyPlayer implements Player
    {

      @Override
      public void play() {
        System.out.println("111111");
      }
    }

    test(new MyPlayer());

    //익명 클래스
    Player p1 = new Player() {
      @Override
      public void play() {
        System.out.println("222222");
      }
    };
    test(p1);

    //익명클래스 2
    test(new Player() {
      @Override
      public void play() {
        System.out.println("333333");
      }});

    //람다
    Player p2 =()->{System.out.println("444444");};
    test(p2);

    //람다 2
    Player p3 =()->System.out.println("444444");
    test(p3);

    //람다3
    test(()->{System.out.println("66666");});

  }

  static void test(Player player) {
    player.play();
  }
}
