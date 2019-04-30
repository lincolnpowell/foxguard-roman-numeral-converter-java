package com.foxguardsolutions.romannumeralconverter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class RomanNumeralConverter {
    private static HashMap<Character, Integer> romanNumerals = new HashMap<>();

    static {
        romanNumerals.put('I', 1);
        romanNumerals.put('V', 5);
        romanNumerals.put('X', 10);
        romanNumerals.put('L', 50);
        romanNumerals.put('C', 100);
        romanNumerals.put('D', 500);
        romanNumerals.put('M', 1000);
    }

    private int getDecimalValue(char romanNumeral) {
        if (romanNumerals.containsKey(romanNumeral)) {
            return romanNumerals.get(romanNumeral);
        } else if (Character.isSpaceChar(romanNumeral)) {
            throw new RuntimeException("Roman numeral string contains space");
        } else {
            throw new RuntimeException("Roman numeral string contains invalid character " + romanNumeral);
        }
    }

    public int convertRomanNumeralsToDecimal(String romanNumerals) {
        int result = 0;
        for (int i = 0; i < romanNumerals.length(); i++) {
            int currentValue = getDecimalValue(romanNumerals.charAt(i)),
                nextValue = (i + 1 == romanNumerals.length()) ? 0 : getDecimalValue(romanNumerals.charAt(i + 1));

            if (currentValue >= nextValue) {
                result += currentValue;
            } else {
                result -= currentValue;
            }
        }
        return result;
    }

    public String readFile(String path) {
        try (Scanner scanner = new Scanner(new File(path))) {
            return scanner.nextLine();
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File not found using path " + path);
        } catch (NoSuchElementException e) {
            throw new RuntimeException("File is empty");
        }
    }

    public void run(String path) {
        System.out.println(convertRomanNumeralsToDecimal(readFile(path)));
    }

    public static void main(String[] args) {
        String path;
        if (args.length != 1) {
            try (Scanner scanner = new Scanner(System.in)) {
                System.out.print("Enter file path: ");
                path = scanner.nextLine();
            }
        } else {
            path = args[0];
        }

        new RomanNumeralConverter().run(path);
    }
}