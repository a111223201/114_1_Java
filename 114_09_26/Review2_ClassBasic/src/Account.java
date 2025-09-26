import java.util.Scanner;

public class Account {
    // 帳戶號碼，唯一識別每個帳戶
    private String accountNumber;
    // 帳戶餘額，儲存目前帳戶的金額
    private double balance;
    private static final Scanner scanner = new Scanner(System.in);

    /**
     * 建構子：初始化帳戶號碼與初始餘額
     * @param accountNumber 帳戶號碼
     * @param initialBalance 初始餘額
     */
    public Account(String accountNumber, double initialBalance) {
        this.setAccountNumber(accountNumber);
        try { this.setBalance(initialBalance); // 設定初始餘額
        } catch (IllegalArgumentException e) {
            System.out.println("初始餘額錯誤: " + e.getMessage()+", 將餘額設為0");
            this.balance = 0; // 如果初始餘額不合法，設為0
        }
    }

    /**
     * 取得帳戶號碼
     * @return 帳戶號碼
     */
    public String getAccountNumber() {
        return accountNumber;
    }

    /**
     * 取得帳戶餘額
     * @return 帳戶餘額
     */
    public double getBalance() {
        return balance;
    }

    /**
     * 設定帳戶餘額
     * @param balance 新的帳戶餘額，必須大於0
     * @throws IllegalArgumentException 如果餘額小於等於0
     */
    public void setBalance(double balance) {
        int attempts = 0;
        while (balance <= 0 && attempts < 3) {
            System.out.println("餘額必須為正數，請重新輸入：");
            try {
                balance = Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                balance = -1;
            }
            attempts++;
        }
        if (balance > 0) {
            this.balance = balance;
        } else {
            throw new IllegalArgumentException("餘額必須為正數");
        }
    }

    /**
     * 設定帳戶號碼
     * @param accountNumber 新的帳戶號碼
     */
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    /**
     * 存款方法：將指定金額存入帳戶
     * @param amount 存款金額，必須大於0
     * @throws IllegalArgumentException 如果存款金額小於等於0
     */
    public void deposit(double amount) {
        int attempts = 0;
        while (amount <= 0 && attempts < 3) {
            System.out.println("存款金額必須為正數，請重新輸入：");
            try {
                amount = Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                amount = -1;
            }
            attempts++;
        }
        if (amount > 0) {
            balance += amount; // 增加帳戶餘額
        } else {
            throw new IllegalArgumentException("存款金額必須為正數");
        }
    }

    /**
     * 提款方法：從帳戶扣除指定金額
     * @param amount 提款金額，必須大於0且小於等於餘額
     * @throws IllegalArgumentException 如果提款金額不合法
     */
    public void withdraw(double amount) {
        int attempts = 0;
        while ((amount <= 0 || amount > balance) && attempts < 3) {
            System.out.println("提款金額不合法，請重新輸入(必須大於0且小於等於餘額)：");
            try {
                amount = Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                amount = -1;
            }
            attempts++;
        }
        if (amount > 0 && amount <= balance) {
            balance -= amount; // 減少帳戶餘額
        } else {
            throw new IllegalArgumentException("提款金額不合法");
        }
    }
}
