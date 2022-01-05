package com.example.bank.Users;

public class Bank {
    public static int bankBalance = 1000000;

    public Bank() {
    }

    public static int getBankBalance() {
        return bankBalance;
    }

    public static void setBankBalance(int bankBalance) {
        Bank.bankBalance = bankBalance;
    }
}
