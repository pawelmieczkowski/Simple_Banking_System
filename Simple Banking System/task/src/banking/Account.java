package banking;

import java.util.Random;

public class Account {
    final private String majorIndustryIdentifier;
    final private String bankIdentificationNumber;
    final private String accountIdentifier;
    final private String checkSum;

    private final String pin;

    private double balance;

    public Account() {
        Random generator = new Random();
        majorIndustryIdentifier = "4";
        bankIdentificationNumber = majorIndustryIdentifier + "00000";

        int temp;
        StringBuilder tempString = new StringBuilder();
        for (int i = 0; i < 9; i++) {
            temp = generator.nextInt(9);
            tempString.append(temp);
        }
        accountIdentifier = tempString.toString();

        checkSum = checkSumGenerator();

        tempString.setLength(0);
        for (int i = 0; i < 4; i++) {
            temp = generator.nextInt(9);
            tempString.append(temp);
        }
        pin = tempString.toString();
        balance = 0;
    }

    public String checkSumGenerator() {
        int[] number = new int[15];
        String cardNumber = bankIdentificationNumber + accountIdentifier;
        for (int i = 0; i < number.length; i++) {
            number[i] = Character.getNumericValue(cardNumber.charAt(i));
        }

        int sum = 0;
        for (int i = 0; i < number.length; i++) {
            if ((i + 1) % 2 != 0) {
                number[i] *= 2;
                if (number[i] > 9) {
                    number[i] -= 9;
                }
            }
            sum += number[i];
        }
        int result;
        if (sum % 10 == 0) {
            result = 0;
        } else {
            result = 10 - (sum % 10);
        }
        return Integer.toString(result);
    }

    public static boolean checkLuhnAlgorithm(String cardNumber) {
        int[] number = new int[16];
        for (int i = 0; i < number.length; i++) {
            number[i] = Character.getNumericValue(cardNumber.charAt(i));
        }
        int sum = 0;
        for (int i = 0; i < number.length; i++) {
            if ((i + 1) % 2 != 0) {
                number[i] *= 2;
                if (number[i] > 9) {
                    number[i] -= 9;
                }
            }
            sum += number[i];
        }
        return sum % 10 == 0;
    }

    public String getCardNumber() {
        return bankIdentificationNumber +
                accountIdentifier + checkSum;
    }

    public String getPIN() {
        return pin;
    }

    public double getBalance() {
        return balance;
    }
}
