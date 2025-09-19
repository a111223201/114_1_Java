public class Account {
    // 帳戶號碼，唯一識別每個帳戶
    private String accountNumber;
    // 帳戶餘額，儲存目前帳戶的金額
    private double balance;

    /**
     * 建構子：初始化帳戶號碼與初始餘額
     * @param accountNumber 帳戶號碼
     * @param initialBalance 初始餘額
     */
    public Account(String accountNumber, double initialBalance) {
        this.accountNumber = accountNumber; // 設定帳戶號碼
        this.balance = initialBalance;      // 設定初始餘額
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
     * 存款方法：將指定金額存入帳戶
     * @param amount 存款金額，必須大於0
     * @throws IllegalArgumentException 如果存款金額小於等於0
     */
    public void deposit(double amount) {
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
        if (amount > 0 && amount <= balance) {
            balance -= amount; // 減少帳戶餘額
        } else {
            throw new IllegalArgumentException("提款金額不合法");
        }
    }
}
