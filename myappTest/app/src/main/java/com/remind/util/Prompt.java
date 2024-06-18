package com.remind.util;

public class Prompt {
    static java.util.Scanner keyboardScanner = new java.util.Scanner(System.in);

    public static String input(String format, Object... args)
    {
        System.out.printf(format + " " + args);
        return keyboardScanner.nextLine();
    }

   public static int inputInit(String format, Object... args)
    {
        return Integer.parseInt(input(format, args));
    }

    public static void close()
    {
        keyboardScanner.close();
    }
}
