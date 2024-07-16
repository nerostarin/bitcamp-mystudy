package study.oop.lambda;

public class Test01 {

  interface Player{
    void play();
  }

  public static void main(String[] args) {

    //일반클래스
    class Myplayer implements Player
    {
      @Override
      public void play()
      {
        System.out.println("일반클래스");
      }
    }

    Player p1 = new Myplayer();
    p1.play();


    //익명클래스
    Player p2 = new Player() {
      @Override
      public void play()
      {
        System.out.println("익명클래스");
      }
    };
    p2.play();

    //익명클래스2
    new Player()
    {
      @Override
      public void play()
      {
        System.out.println("익명클래스2");
      }
    }.play();

    //람다
    Player p3 = () -> {System.out.println("람다");};
    p3.play();

    //람다2
    Player p4 = () -> System.out.println("람다2");
    p4.play();


  }
}
