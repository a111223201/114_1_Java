// 定義一個帳戶類別，包含帳戶號碼與餘額，並提供存款與提款功能

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

    public Account(){
        this("0000",0);
    }

    public Account(String accountNumber){
        this(accountNumber,0);
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
     * 多貨幣存款方法：將指定金額以特定貨幣存入帳戶
     * @param amount 存款金額，必須大於0
     * @param currency 貨幣種類（如 "USD", "EUR", "JPY"）
     */
    public void deposit(double amount, String currency) {
        double exchangeRate = 1.0; // 預設匯率為1.0（即無匯率差異）
        switch (currency.toUpperCase()) {
            case "USD":
                exchangeRate = 30.0; // 假設1 USD = 30 TWD
                break;
            case "EUR":
                exchangeRate = 35.0; // 假設1 EUR = 35 TWD
                break;
            case "JPY":
                exchangeRate = 0.25; // 假設1 JPY = 0.25 TWD
                break;
            // 可以根據需要添加更多貨幣和匯率
            default:
                System.out.println("未知的貨幣，使用預設匯率1.0");
                return;
        }
        double amountInTWD = amount * exchangeRate; // 將存款金額轉換為台幣
        this.deposit(amountInTWD); // 使用原本的存款方法進行存
    }


    /**
     * 多重存款方法：將多個金額總和存入帳戶
     * @param amounts 多個存款金額，必須大於0
     */
    public void deposit(double ... amounts){
        double totalAmount = 0;
        for(double amount : amounts){
            if ( amount > 0)
            {
                totalAmount += amount;
            }
            else {
                throw new IllegalArgumentException("餘額必須為正數");
            }
        }
        this.deposit(totalAmount);
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

