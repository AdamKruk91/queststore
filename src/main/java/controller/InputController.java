package controller;

import java.util.Scanner;

public class InputController {

    public String getStringInput(String text) {
        System.out.println(text);
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        return input;
    }
    public int getIntInput(String text) {
        System.out.println(text);
        Scanner scanner = new Scanner(System.in);
        int input = scanner.nextInt();
        return input;
    }
}
