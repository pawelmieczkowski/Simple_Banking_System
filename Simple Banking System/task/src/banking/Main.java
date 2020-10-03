package banking;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.function.ToDoubleBiFunction;

public class Main {
    public static void main(String[] args) {
        CreditCardDB db = new CreditCardDB(args[1]);
        boolean menuFlag = true;

        while (menuFlag) {
            Scanner scanner = new Scanner(System.in);

            System.out.println("1. Create an account");
            System.out.println("2. Log into account");
            System.out.println("0. Exit");

            int input = scanner.nextInt();
            boolean accountMenuFlag = true;
            switch (input) {
                case 1: {
                    Account account = new Account();
                    System.out.println("Your card has been created");
                    System.out.println("Your card number:");
                    System.out.println(account.getCardNumber());
                    System.out.println("Your card PIN:");
                    System.out.println(account.getPIN());
                    //accounts.add(account);
                    db.addNewCreditCard(account.getCardNumber(), account.getPIN(), (int) account.getBalance());
                    break;
                }
                case 2: {
                    System.out.println("Enter your card number:");
                    String enteredCardNumber = scanner.next();
                    System.out.println("Enter your PIN:");
                    String enteredPIN = scanner.next();


                    if (db.checkCreditCard(enteredCardNumber, enteredPIN)) {
                        while (accountMenuFlag) {
                            System.out.println("You have successfully logged in!\n");
                            System.out.println("1. Balance");
                            System.out.println("2. Add income");
                            System.out.println("3. Do transfer");
                            System.out.println("4. Close account");
                            System.out.println("5. Log out");
                            System.out.println("0. Exit");
                            input = scanner.nextInt();
                            switch (input) {
                                case 1: {
                                    System.out.print("Balance: ");
                                    System.out.println(db.getBalance(enteredCardNumber));
                                    break;
                                }
                                case 2: {
                                    System.out.println("Enter income: ");
                                    int income = scanner.nextInt();
                                    db.addIncome(enteredCardNumber, income);
                                    break;
                                }
                                case 3: {
                                    transfer(db, enteredCardNumber);
                                    break;
                                }
                                case 4: {
                                    System.out.println("The account has been closed!");
                                    db.deleteAccount(enteredCardNumber);
                                    break;
                                }
                                case 5: {
                                    System.out.println("You have successfully logged out!");
                                    accountMenuFlag = false;
                                    break;
                                }
                                case 0: {
                                    accountMenuFlag = false;
                                    menuFlag = false;
                                    break;
                                }
                                default: {
                                    System.out.println("No such command");
                                    break;
                                }
                            }
                        }
                    }
                    break;
                }
                case 0: {
                    menuFlag = false;
                    break;
                }

            }

        }
    }

    public static void transfer(CreditCardDB db, String cardNumberOwner) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Transfer");
        System.out.println("Enter card number:");
        String cardNumberToTransferTo = scanner.next();
        if (cardNumberOwner.equals(cardNumberToTransferTo)) {
            System.out.println("You can't transfer money to the same account!");
        } else if (!Account.checkLuhnAlgorithm(cardNumberToTransferTo)) {
            System.out.println("Probably you made mistake in the card number. Please try again!");
        } else if (!db.checkCreditCard(cardNumberToTransferTo)) {
            System.out.println("Such a card does not exist.");
        } else {
            System.out.println("Enter how much money you want to transfer: ");
            int moneyAmount = scanner.nextInt();
            if (db.getBalance(cardNumberOwner) < moneyAmount) {
                System.out.println("Not enough money!");
            } else {
                db.transfer(cardNumberOwner, cardNumberToTransferTo, moneyAmount);
            }
        }

    }
}
