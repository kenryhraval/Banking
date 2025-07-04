package main;

import java.io.*;
import java.util.HashMap;
import java.util.Random;

public class AccountManager {
    private final HashMap<Integer, BankAccount> accounts = new HashMap<>();
    private final Random random = new Random();
    private final File file = new File("accounts.bin");

    AccountManager() {
        if (!file.exists()) return;
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            accounts.putAll((HashMap<Integer, BankAccount>) in.readObject());
        } catch (Exception e) {
            System.out.println("Could not load saved accounts (" + e.getMessage() + "). Starting with empty account list.");
        }
    }

    public int generateUniqueId() {
        int id;
        do {
            id = 100000 + random.nextInt(900000); // 6 digits
        } while (accounts.containsKey(id));
        return id;
    }

    public int getSize() {
        return accounts.size();
    }

    public void saveAccounts() throws Exception {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
            out.writeObject(accounts);
        }
    }

    public void exportReportCsv(String filename) throws Exception {
        File outputFile = new File(filename);
        try (PrintWriter out = new PrintWriter(new FileWriter(outputFile))) {
            out.println("AccountID,Balance");
            accounts.forEach((id, account) -> {
                out.println(id + "," + account.getBalance());
            });
        }
    }

    public int addAccount(BankAccount account) {
        int id = generateUniqueId();
        accounts.put(id, account);
        return id;
    }


    public BankAccount getAccount(int id) {
        return accounts.get(id);
    }

    public void printBalances() {
        if (accounts.isEmpty()) {
            System.out.println("No accounts to show.");
            return;
        }
        accounts.forEach((id, account) -> {
            System.out.print("Account #" + id + ": ");
            account.printBalance();
        });
    }


}
