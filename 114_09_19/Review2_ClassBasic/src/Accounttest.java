public class Accounttest {
    public static void main(String[] args) {

        Account account1 = new Account("A123", 1000.0); // 建立一個帳戶物件，初始餘額為1000
        Account account2 = new Account("8456",-2000); // 建立一個帳戶物件，初始餘額為-2000
        System.out.println("帳戶號碼: " + account1.getAccountNumber()); // 顯示帳戶號碼
        System.out.println("初始餘額: " + account1.getBalance()); //

        System.out.printf("帳戶號碼:%s%n初始餘額:%.2f%n", account1.getAccountNumber(), account1.getBalance());
        System.out.printf("帳戶號碼:%s%n初始餘額:%.2f%n", account2.getAccountNumber(), account2.getBalance());

        account1.deposit(500.0); // 存入500
        System.out.printf("帳戶號碼:%s%n存款後餘額:%.2f%n", account1.getAccountNumber(), account1.getBalance());

        account1.withdraw(1000.0); // 提款1000
        System.out.printf("帳戶號碼:%s%n提款後餘額:%.2f%n", account1.getAccountNumber(), account1.getBalance());

        // 測試不合法的存款
        try {
            account1.deposit(-1000.0); // 嘗試存入負數
            System.out.printf("帳戶號碼:%s%n存款後餘額:%.2f%n", account1.getAccountNumber(), account1.getBalance());
        } catch (IllegalArgumentException e) {
            System.out.printf("存款錯誤: %s%n", e.getMessage());
        }
        try {
            account1.withdraw(2000.0); // 嘗試提款超過餘額
            System.out.printf("帳戶號碼:%s%n提款後餘額:%.2f%n", account1.getAccountNumber(), account1.getBalance());
        } catch (IllegalArgumentException e) {
            System.out.printf("提款錯誤: %s%n", e.getMessage());
        }

    }
}
