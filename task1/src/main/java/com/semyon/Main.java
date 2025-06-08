package com.semyon;

import java.util.Scanner;

public class Main {

    private static final int MAX_TABLE_SIZE = 32;
    private static final int MIN_TABLE_SIZE = 1;
    private static final int INPUT_MAX_LENGTH = String.valueOf(MAX_TABLE_SIZE).length();

    public static void main(String[] args) {

        try (Scanner scanner = new Scanner(System.in)) {
            int tableSize = 1;
            boolean isValidInput = false;

            while (!isValidInput) {
                System.out.println("Enter the size of the multiplication table: an integer from "
                        + MIN_TABLE_SIZE + " to " + MAX_TABLE_SIZE + ".");
                String input = scanner.nextLine().trim();

                if (input.matches("\\d{0," + INPUT_MAX_LENGTH + "}")) {
                    tableSize = Integer.parseInt(input);
                    if (tableSize < MIN_TABLE_SIZE || tableSize > MAX_TABLE_SIZE) {
                        System.out.println("Invalid size: " + input + ". Must be from "
                                + MIN_TABLE_SIZE + " to " + MAX_TABLE_SIZE + ".");
                        continue;
                    }
                    isValidInput = true;
                } else {
                    System.out.println("Invalid input: " + input + ". Enter an INTEGER from "
                            + MIN_TABLE_SIZE + " to " + MAX_TABLE_SIZE + ".");
                }
            }
            MultiplicationTable table = new MultiplicationTable(tableSize);
            System.out.println(table.generateTable());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}