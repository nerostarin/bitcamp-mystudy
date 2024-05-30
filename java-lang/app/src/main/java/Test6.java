// 실습
// - 애플리케이션 아규먼트를 다음과 같이 결과가 나오도록 모두 출력하라.
//      $ java -classpath ... Test5 hongkildong aaa bbb ccc ddd
//      'aaa'
//      'bbb'
//      'ccc'
//      'ddd'

class Test6 {
    public static void main(String[] args) {
        for (int a = 0; a < args.length; a++) {
            System.out.println("'" + args[a] + "'");
        }
    }
}
