package auction;

import java.time.LocalDateTime;

/**
 * 線上拍賣系統整合展示
 * 涵蓋：一般競標、荷蘭式拍賣、管理員操作
 */
public class AuctionDemo {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("===== 2025 線上拍賣系統展示 (SDD 規範實作) =====");

        // 1. 初始化使用者
        Seller seller = new Seller("S001", "Apple官方旗艦店");
        Buyer alice = new Buyer("B001", "Alice");
        Buyer bob = new Buyer("B002", "Bob");
        Administrator admin = new Administrator("A001", "系統管理員", "EMP-99");

        // ---------------------------------------------------------
        // 2. 一般競標展示 (StandardAuction)
        // 設定 2 分鐘後結束，測試「最後一刻出價延長」機制
        // ---------------------------------------------------------
        System.out.println("\n--- [場景 1: 一般競標 - iPhone 16] ---");
        StandardAuction standardAuction = new StandardAuction(
                "ITEM-001", seller, "iPhone 16 Pro", 30000.0, 500.0,
                LocalDateTime.now().plusMinutes(2)
        );

        System.out.println("初始結束時間: " + standardAuction.getEndTime());

        // Alice 出價 31000
        BidResult r1 = standardAuction.placeBid(alice, 31000.0);
        System.out.println("Alice 出價 31000: " + r1);
        System.out.println("出價後結束時間: " + standardAuction.getEndTime()); // 應延後 5 分鐘

        // Bob 嘗試出價 31200 (低於最低增額 31000 + 500)
        try {
            standardAuction.placeBid(bob, 31200.0);
        } catch (IllegalArgumentException e) {
            System.err.println("Bob 出價失敗 (預期內): " + e.getMessage());
        }

        // Bob 正確加價
        BidResult r2 = standardAuction.placeBid(bob, 32000.0);
        System.out.println("Bob 出價 32000: " + r2);

        // ---------------------------------------------------------
        // 3. 荷蘭式拍賣展示 (DutchAuction)
        // 價格隨時間遞減，第一個出價者即成交
        // ---------------------------------------------------------
        System.out.println("\n--- [場景 2: 荷蘭式拍賣 - 限量咖啡機] ---");
        // 設定 5 秒鐘內從 10000 降到 5000
        DutchAuction dutchAuction = new DutchAuction(
                "DUTCH-001", seller, "限定版濃縮咖啡機", 10000.0, 5000.0,
                LocalDateTime.now().plusSeconds(5)
        );

        System.out.println("拍賣開始價格: " + dutchAuction.getCurrentPrice());

        System.out.println("等待價格下降中...");
        Thread.sleep(2500); // 等待 2.5 秒 (預計降到約 7500)

        double currentPrice = dutchAuction.getCurrentPrice();
        System.out.println("當前遞減價格: " + currentPrice);

        BidResult r3 = dutchAuction.placeBid(alice, currentPrice);
        System.out.println("Alice 決定以此價格購入: " + r3);

        // ---------------------------------------------------------
        // 4. 管理員與爭議處理展示 (Administrator & Dispute)
        // ---------------------------------------------------------
        System.out.println("\n--- [場景 3: 爭議處理] ---");
        // 模擬一個針對場景 1 的爭議
        Dispute dispute = new Dispute("買家付款後賣家未發貨");
        System.out.println("收到新爭議: " + "買家付款後賣家未發貨");

        admin.resolveDispute(dispute, "核實後強制退款予買家，並暫停賣家權限。");
        admin.cancelAuction(standardAuction, "檢舉異常活動");

        System.out.println("\n===== 展示結束 =====");

        System.out.println("\n--- [場景 4: 自動出價競爭] ---");
        StandardAuction tesla = new StandardAuction(
                "ITEM-99", seller, "Model 3", 1000000.0, 10000.0, LocalDateTime.now().plusMinutes(10)
        );

// Alice 設定自動出價上限 105 萬
        System.out.println("Alice 設定自動出價，上限 1,050,000");
        tesla.setAutoBid(new AutoBid(alice, 1050000.0));

// Bob 手動出價 102 萬
        System.out.println("\nBob 手動出價 1,020,000...");
        tesla.placeBid(bob, 1020000.0);
        System.out.println("目前最高價: " + tesla.getCurrentHighestBid().getAmount() +
                " (由 " + tesla.getCurrentHighestBid().getBidder().username + " 持有)");

// Bob 再次挑戰，手動出價 106 萬 (超過 Alice 上限)
        System.out.println("\nBob 手動出價 1,060,000...");
        tesla.placeBid(bob, 1060000.0);
        System.out.println("目前最高價: " + tesla.getCurrentHighestBid().getAmount() +
                " (由 " + tesla.getCurrentHighestBid().getBidder().username + " 持有)");

// 在 AuctionDemo.java 中測試
        System.out.println("\n--- [場景 5: 關注者通知測試] ---");
        StandardAuction camera = new StandardAuction(
                "CAM-01", seller, "Nikon Z9", 150000.0, 1000.0, LocalDateTime.now().plusMinutes(10)
        );

// Alice 和 Bob 都關注這台相機
        camera.addWatcher(alice);
        camera.addWatcher(bob);

// 當第三方（假設是另一個買家）出價時，Alice 和 Bob 都會收到通知
        Buyer charlie = new Buyer("B003", "Charlie");
        camera.placeBid(charlie, 160000.0);

        System.out.println("\n--- [場景 6: 評價系統測試] ---");
// Alice 給予賣家評價
        seller.addRating(alice, 5, "商品包裝優良，出貨迅速！");
        seller.addRating(bob, 4, "還可以，但運送稍微慢了一點。");

        System.out.println("賣家總評價數: " + seller.getRatingCount());
        System.out.printf("賣家平均得分: %.1f 星\n", seller.getAverageRating());

        System.out.println("\n--- [場景 7: 底價與直購測試] ---");

// 底價拍賣測試
        ReserveAuction reserve = new ReserveAuction("RES-01", seller, "古董錶", 1000, 100, 5000, LocalDateTime.now().plusMinutes(1));
        reserve.placeBid(alice, 1200);
        System.out.println("底價拍賣 - 目前出價 1200，底價 5000。是否達標: " + reserve.isSuccessfullySold());

// 直購測試
        BuyNowListing buyNow = new BuyNowListing("BN-01", seller, "全新耳機", 3000, LocalDateTime.now().plusDays(1));
        BidResult buyResult = buyNow.placeBid(bob, 3000);
        System.out.println("直購結果: " + buyResult);

// --- [場景 8: ManualBid 實作驗證] ---
        System.out.println("\n--- [場景 8: ManualBid 實作驗證] ---");

        StandardAuction watch = new StandardAuction(
                "WATCH-001", seller, "Rolex Submariner", 200000.0, 5000.0, LocalDateTime.now().plusMinutes(10)
        );

// 執行手動出價
        System.out.println("Alice 進行手動出價...");
        watch.placeBid(alice, 210000.0);

// 從歷史紀錄中驗證物件類型
        Bid lastBid = watch.getBidHistory().get(0);
        System.out.println("出價紀錄類型: " + lastBid.getBidType()); // 預計顯示 "Manual"
        System.out.println("出價金額: " + lastBid.getAmount());

        if (lastBid instanceof ManualBid) {
            System.out.println("驗證成功：此紀錄確實是 ManualBid 的實體。");
        }

        System.out.println("\n--- [場景 9: 完整成交流程測試] ---");

        AuctionHouse platform = new AuctionHouse();
        platform.listAuction(standardAuction);

// 假設 standardAuction 結束且 Bob 獲勝
        Bid winningBid = standardAuction.getCurrentHighestBid();
        if (winningBid != null) {
            // 1. 產生紀錄
            Transaction tx = new Transaction("TXN-101", standardAuction, winningBid);

            // 2. 處理付款
            Payment pay = new Payment("Credit Card", winningBid.getAmount());
            pay.confirm();
            tx.setPayment(pay);
            System.out.println("付款已確認: " + winningBid.getAmount());

            // 3. 處理運送
            ShippingInfo ship = new ShippingInfo("台北市忠孝東路一段1號");
            ship.updateStatus("Shipped", "SF123456789");
            tx.setShipping(ship);
            System.out.println("物流已發貨，單號: SF123456789");

            platform.recordTransaction(tx);
        }
    }
}