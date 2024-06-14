package com.remind;

public class Prompt {
    static java.util.Scanner keyboardScanner = new java.util.Scanner(System.in);

    static String input(String menu)
    {
        System.out.printf("%s",menu);
        return keyboardScanner.nextLine();
    }

    static void close()
    {
        keyboardScanner.close();
    }
}
