package auction;

import java.time.LocalDateTime;
import java.time.Duration;
import java.util.ArrayList;

/**
 * 一般競標 (StandardAuction)
 * 符合 SDD 規格：具備最小增額驗證、最後一刻延長、自動出價、以及關注者通知。
 */
public class StandardAuction implements Biddable, Expirable, Watchable {
    private String listingId;
    private Seller seller;
    private String title;
    private double currentPrice;
    private double minIncrement;
    private LocalDateTime endTime;

    private Bid highestBid;
    private ArrayList<Bid> bidHistory = new ArrayList<>();
    private ArrayList<User> watchers = new ArrayList<>(); // 儲存關注此商品的用戶
    private AutoBid activeAutoBid; // 當前有效的自動出價器

    public StandardAuction(String id, Seller seller, String title, double startPrice,
                           double minIncrement, LocalDateTime endTime) {
        this.listingId = id;
        this.seller = seller;
        this.title = title;
        this.currentPrice = startPrice;
        this.minIncrement = minIncrement;
        this.endTime = endTime;
    }

    // --- Biddable 介面實作：核心競標邏輯 ---

    @Override
    public BidResult placeBid(Buyer buyer, double amount) {
        // 1. 驗證：拍賣是否結束
        if (isExpired()) {
            throw new IllegalStateException("Auction has ended");
        }

        // 2. 驗證：出價者非賣家本人
        if (buyer.getUserId().equals(seller.getUserId())) {
            throw new IllegalArgumentException("Seller cannot bid on own listing");
        }

        // 3. 驗證：金額是否符合最小增額要求
        double minRequired = (highestBid == null) ? currentPrice : highestBid.getAmount() + minIncrement;
        if (amount < minRequired) {
            throw new IllegalArgumentException("Bid must be at least " + minRequired);
        }

        // 4. 更新狀態：紀錄出價
        Bid previousBid = highestBid;
        this.highestBid = new ManualBid(buyer, amount, LocalDateTime.now());
        bidHistory.add(highestBid);

        // 5. 通知：通知前最高出價者被超越，並通知所有關注者
        if (previousBid != null) {
            System.out.println("[私訊通知] " + previousBid.getBidder().username + "：您的出價已被超越！");
        }
        notifyWatchers("商品 [" + title + "] 有新出價：$" + amount);

        // 6. 自動出價競爭：若有設 AutoBid 且非本人出價，觸發自動反擊
        if (activeAutoBid != null && !buyer.getUserId().equals(activeAutoBid.getBidder().getUserId())) {
            double counterAmount = activeAutoBid.compete(amount, minIncrement);
            if (counterAmount != -1) {
                System.out.println("[系統訊息] 自動出價器代表 " + activeAutoBid.getBidder().username + " 反擊出價 $" + counterAmount);
                return placeBid(activeAutoBid.getBidder(), counterAmount); // 遞迴競爭
            } else {
                notifyWatchers("自動出價者 " + activeAutoBid.getBidder().username + " 已達上限，退出競爭。");
                activeAutoBid = null;
            }
        }

        // 7. 延長機制：結束前 5 分鐘出價則延長 5 分鐘
        boolean wasExtended = false;
        if (getRemainingTime().toMinutes() < 5) {
            extendTime(Duration.ofMinutes(5));
            wasExtended = true;
            notifyWatchers("競標激烈！結束時間已延長 5 分鐘。");
        }

        return new BidResult(true, "出價成功", wasExtended);
    }

    // --- Watchable 介面實作：觀察者模式 ---

    @Override
    public void addWatcher(User user) {
        if (!watchers.contains(user)) {
            watchers.add(user);
            System.out.println("[系統] " + user.username + " 開始關注 " + this.title);
        }
    }

    @Override
    public void removeWatcher(User user) {
        watchers.remove(user);
    }

    @Override
    public void notifyWatchers(String eventMessage) {
        for (User user : watchers) {
            System.out.println("[推播給 " + user.username + "] " + eventMessage);
        }
    }

    // --- Expirable 與其他實體方法 ---

    @Override
    public boolean isExpired() {
        return LocalDateTime.now().isAfter(endTime);
    }

    @Override
    public Duration getRemainingTime() {
        return Duration.between(LocalDateTime.now(), endTime);
    }

    @Override
    public void extendTime(Duration extension) {
        this.endTime = this.endTime.plus(extension);
    }

    public void setAutoBid(AutoBid autoBid) {
        this.activeAutoBid = autoBid;
        // 初始設定後嘗試自動出價
        double minReq = (highestBid == null) ? currentPrice : highestBid.getAmount() + minIncrement;
        if (autoBid.getMaxLimit() >= minReq) {
            placeBid(autoBid.getBidder(), minReq);
        }
    }

    @Override public Bid getCurrentHighestBid() { return highestBid; }
    @Override public ArrayList<Bid> getBidHistory() { return new ArrayList<>(bidHistory); }
    @Override public LocalDateTime getEndTime() { return endTime; }
    public String getTitle() { return title; }
}