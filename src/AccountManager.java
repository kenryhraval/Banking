import java.io.*;
import java.util.ArrayList;

public class AccountManager {
    private final ArrayList<BankAccount> accounts = new ArrayList<>();
    private final File file = new File("accounts.bin");

    AccountManager() {
        if (!file.exists()) return;
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            accounts.addAll((ArrayList<BankAccount>) in.readObject());
        } catch (Exception e) {
            System.out.println("Could not load saved accounts (" + e.getMessage() + "). Starting with empty account list.");
        }
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
            for (int i = 0; i < accounts.size(); i++) {
                out.println((i + 1) + "," + accounts.get(i).getBalance());
            }
        }
    }

    public void addAccount(BankAccount account) {
        accounts.add(account);
    }


    public BankAccount getAccount(int id) {
        if (id >= 0 && id < accounts.size()) {
            return accounts.get(id);
        } else {
            return null;
        }
    }
}
