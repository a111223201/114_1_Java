package auction;

import java.time.LocalDateTime;
import java.time.Duration;
import java.util.ArrayList;

/**
 * 荷蘭式拍賣：價格隨時間遞減
 */
public class DutchAuction implements Biddable, Expirable {
    private String listingId;
    private Seller seller;
    private String title;
    private double startPrice;
    private double reservePrice; // 底價
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private boolean isSold = false;
    private Bid winningBid;

    public DutchAuction(String id, Seller seller, String title, double startPrice,
                        double reservePrice, LocalDateTime endTime) {
        this.listingId = id;
        this.seller = seller;
        this.title = title;
        this.startPrice = startPrice;
        this.reservePrice = reservePrice;
        this.startTime = LocalDateTime.now();
        this.endTime = endTime;
    }

    /**
     * 計算當前應有的價格 (根據時間比例遞減)
     */
    public double getCurrentPrice() {
        if (isSold) return winningBid.getAmount();

        long totalDuration = Duration.between(startTime, endTime).toSeconds();
        long elapsed = Duration.between(startTime, LocalDateTime.now()).toSeconds();

        if (elapsed >= totalDuration) return reservePrice;

        // 線性遞減公式: 當前價格 = 起始價 - (起始價 - 底價) * (已過時間 / 總時間)
        double priceDrop = (startPrice - reservePrice) * ((double) elapsed / totalDuration);
        return startPrice - priceDrop;
    }

    @Override
    public BidResult placeBid(Buyer buyer, double amount) {
        if (isExpired()) throw new IllegalStateException("Auction has ended");
        if (isSold) throw new IllegalStateException("Item already sold");

        double currentPrice = getCurrentPrice();

        // 荷蘭式拍賣中，出價必須大於等於當前價格
        if (amount < currentPrice) {
            throw new IllegalArgumentException("Bid is lower than current price: " + currentPrice);
        }

        // 成功成交
        this.winningBid = new ManualBid(buyer, currentPrice, LocalDateTime.now());
        this.isSold = true;
        return new BidResult(true, "Congratulations! You won the Dutch Auction at " + currentPrice, false);
    }

    @Override
    public boolean isExpired() {
        return LocalDateTime.now().isAfter(endTime) || isSold;
    }

    @Override
    public Duration getRemainingTime() {
        return Duration.between(LocalDateTime.now(), endTime);
    }

    // 其他介面必要實作
    @Override public void extendTime(Duration extension) { this.endTime = this.endTime.plus(extension); }
    @Override public Bid getCurrentHighestBid() { return winningBid; }
    @Override public ArrayList<Bid> getBidHistory() {
        ArrayList<Bid> history = new ArrayList<>();
        if (winningBid != null) history.add(winningBid);
        return history;
    }
    @Override public LocalDateTime getEndTime() { return endTime; }
    public String getTitle() { return title; }
}