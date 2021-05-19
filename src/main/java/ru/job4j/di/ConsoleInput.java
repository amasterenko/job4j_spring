package ru.job4j.di;

import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ConsoleInput {
    private final Scanner sc = new Scanner(System.in);

    public String getLine() {
        return sc.nextLine();
    }
}
