public class Accounttest {
    private static int customCount; // 客戶數量
    public static void main(String[] args) {
        Account[] customers = new Account[10]; // 儲存最多10個客戶的帳戶
        Account acc1 = new Account("0001", "John", 3000);
        addCustomer(customers, acc1);
        Account acc2 = new Account("1234", "Bob",5000);
        addCustomer(customers, acc2);
        Account acc3 = new Account("5678", "Alice", 10000);
        addCustomer(customers, acc3);
        printCustomerAccounts(customers);
    }

    //顯示客戶帳戶資訊


    public static void addCustomer(Account[] customers, Account newAccount) {
        if (customCount < customers.length) {
            customers[customCount] = newAccount;
            customCount++;
            System.out.println("成功新增客戶: " + newAccount.getAccountNumber());
        } else {
            System.out.println("無法新增客戶，客戶數量已達上限");
        }
    }

    public static void printCustomerAccounts(Account[] customers) {
        System.out.println("客戶帳戶資訊:");
        for (int i = 0; i < customCount; i++) {
            Account acc = customers[i];
            System.out.println("帳戶號碼: " + acc.getAccountNumber() + ", 擁有者: " + acc.getOwnerName() + ", 餘額: " + acc.getBalance());
        }
    }

    public static void printCustomerInfo(Account[] customers) {
        System.out.println("客戶資訊:");
        for (int i = 0; i < customCount; i++) {
            Account acc = customers[i];
            System.out.println("帳戶號碼: " + acc.getAccountNumber() + ", 擁有者: " + acc.getOwnerName() + ", 餘額: " + acc.getBalance());
        }
    }
}
