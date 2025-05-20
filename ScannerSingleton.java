package com.company;

import java.util.Scanner;

/*
   To provide one instance of scanner everywhere
   To help in garbage management
   Design pattern used - "Singleton class"
 */


public class ScannerSingleton {
    private static final Scanner sc = new Scanner(System.in);

    private ScannerSingleton(){}


    public static Scanner getScanner() {
        return sc;
    }

}


