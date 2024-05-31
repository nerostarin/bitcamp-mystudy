package study.lang.literal;

public class Test02 {
    public static void main(String[] args) {
        System.out.println(100);
        System.out.println("2진수" + Integer.toBinaryString(100));
        System.out.println("8진수" + Integer.toOctalString(100));
        System.out.println("16진수" + Integer.toHexString(100));

        System.out.println("2진수" + 0b01100100);
        System.out.println("8진수" + 0144);
        System.out.println("16진수" + 0x64);
    }
}
