package br.com.lmos.finzerecommerce.util;

public class CpfValidator {

    public static boolean isValid(String cpf) {
        if (cpf == null || cpf.length() != 11 || !cpf.matches("\\d{11}")) {
            return false;
        }


        if (cpf.matches("(\\d)\\1{10}")) {
            return false;
        }

        try {
            int[] numbers = cpf.chars().map(c -> c - '0').toArray();


            int sum = 0;
            for (int i = 0; i < 9; i++) {
                sum += numbers[i] * (10 - i);
            }
            int firstDigit = 11 - (sum % 11);
            firstDigit = (firstDigit >= 10) ? 0 : firstDigit;

            if (firstDigit != numbers[9]) {
                return false;
            }

            sum = 0;
            for (int i = 0; i < 10; i++) {
                sum += numbers[i] * (11 - i);
            }
            int secondDigit = 11 - (sum % 11);
            secondDigit = (secondDigit >= 10) ? 0 : secondDigit;

            return secondDigit == numbers[10];

        } catch (Exception e) {
            return false;
        }
    }
}
