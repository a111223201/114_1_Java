import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class AccountTest {
    // 使用 List 儲存客戶資料（改用動態陣列）
    public static void main(String[] args) {
        List<Account> customers = new ArrayList<>(); // 儲存客戶帳戶的 List
        Account acc1 = new Account("A001", "Alice", 5000);
        addCustomer(customers, acc1);
        Account acc2 = new Account("A002", "Bob", 3000);
        addCustomer(customers, acc2);
        Account acc3 = new Account("A003", "Charlie", -100);
        addCustomer(customers,  acc3);

        operation(customers);
        // 顯示所有客戶帳戶資訊
        //System.out.println("\n所有位客戶帳戶資訊:");
        //printCustomerAccounts(customers);
    }

    public static void operation(List<Account> customers) {
        Scanner scanner = new java.util.Scanner(System.in);
        Account selectedAccount;
        while (true) {
            menu();
            System.out.print("請選擇功能(1-5): ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // 清除換行符號

            switch (choice) {
                case 1:
                    System.out.print("輸入帳戶號碼: ");
                    String accNum = scanner.nextLine();
                    System.out.print("輸入持有人名稱: ");
                    String ownerName = scanner.nextLine();
                    System.out.print("輸入初始餘額: ");
                    double initialBalance = scanner.nextDouble();
                    scanner.nextLine(); // 清除換行符號
                    Account newAccount = new Account(accNum, ownerName, initialBalance);
                    addCustomer(customers, newAccount);
                    break;
                case 2:
                    System.out.print("輸入要查詢的帳戶號碼: ");
                    String searchAccNum = scanner.nextLine();
                    selectedAccount = customerInAction(customers,searchAccNum);
                    printCustomerInfo(selectedAccount);
                    break;
                case 3:
                    System.out.println("\n所有位客戶帳戶資訊:");
                    printCustomerAccounts(customers);
                    break;
                case 4:
                    System.out.print("輸入要刪除的帳戶號碼: ");
                    String delAccNum = scanner.nextLine();
                    deleteCustomer(customers, delAccNum);
                    break;
                case 5:
                    System.out.println("離開系統，謝謝使用!");
                    scanner.close();
                    return;
                default:
                    System.out.println("無效的選擇，請重新輸入。");
            }
        }
    }

    public static Account customerInAction(List<Account> customers, String accountNumber) {
        for (Account c : customers) {
            if (c.getAccountNumber().equals(accountNumber)) {
                return c;
            }
        }
        System.out.println("找不到指定的帳戶號碼: " + accountNumber);
        return null;
    }

    public static void addCustomer(List<Account> customers, Account newAccount) {
        customers.add(newAccount);
        System.out.println("新增客戶成功: " + newAccount.getAccountNumber());
    }

    public static void printCustomerAccounts(List<Account> customers) {
        for (Account c : customers) {
            printCustomerInfo(c);
        }
    }

    // 刪除帳戶：輸入帳號，若存在則從 List 中移除
    public static void deleteCustomer(List<Account> customers, String accountNumber) {
        boolean removed = customers.removeIf(c -> c.getAccountNumber().equals(accountNumber));
        if (removed) {
            System.out.println("刪除成功: " + accountNumber);
        } else {
            System.out.println("找不到指定的帳戶號碼: " + accountNumber);
        }
    }

    public static void printCustomerInfo(Account account) {
        if (account == null) {
            System.out.println("無法列印客戶資訊，帳戶不存在。");
            return;
        }
        System.out.println("帳戶號碼: " + account.getAccountNumber() +
                ", 持有人: " + account.getOwnerName() +
                ", 餘額: " + account.getBalance());
    }

    //功能選單(1) 新增客戶 (2) 列印指定客戶資訊 (3)顯示所有客戶帳戶資訊 (4) 離開系統
    // 功能選單(1) 新增客戶 (2) 列印指定客戶資訊 (3) 顯示所有客戶帳戶資訊 (4) 離開系統
    public static void menu() {
        System.out.println("功能選單:");
        System.out.println("1. 新增客戶");
        System.out.println("2. 列印指定客戶資訊");
        System.out.println("3. 顯示所有客戶帳戶資訊");
        System.out.println("4. 刪除帳戶");
        System.out.println("5. 離開系統");
    }
}
