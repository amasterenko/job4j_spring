package ru.job4j.di;

import java.util.Scanner;

public class ConsoleInput {
    private final Scanner sc = new Scanner(System.in);

    public String getLine() {
        return sc.nextLine();
    }
}
