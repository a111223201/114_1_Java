package auction;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class BuyNowListing implements Biddable, Expirable {
    private String id;
    private Seller seller;
    private String title;
    private double fixedPrice;
    private boolean isSold = false;
    private Bid winningBid;
    private LocalDateTime endTime;

    public BuyNowListing(String id, Seller seller, String title, double fixedPrice, LocalDateTime endTime) {
        this.id = id;
        this.seller = seller;
        this.title = title;
        this.fixedPrice = fixedPrice;
        this.endTime = endTime;
    }

    @Override
    public BidResult placeBid(Buyer buyer, double amount) {
        if (isSold || isExpired()) throw new IllegalStateException("Listing is no longer available");

        // 規格：直購必須支付正確金額
        if (amount < fixedPrice) {
            throw new IllegalArgumentException("Price must be " + fixedPrice);
        }

        this.winningBid = new ManualBid(buyer, fixedPrice, LocalDateTime.now());
        this.isSold = true;
        return new BidResult(true, "Successfully purchased at Buy-it-now price!", false);
    }

    // 實作介面必要方法
    @Override public boolean isExpired() { return isSold || LocalDateTime.now().isAfter(endTime); }
    @Override public LocalDateTime getEndTime() { return endTime; }
    @Override public void extendTime(java.time.Duration e) {} // 直購不需要延長
    @Override public java.time.Duration getRemainingTime() { return java.time.Duration.between(LocalDateTime.now(), endTime); }
    @Override public Bid getCurrentHighestBid() { return winningBid; }
    @Override public ArrayList<Bid> getBidHistory() {
        ArrayList<Bid> h = new ArrayList<>();
        if (winningBid != null) h.add(winningBid);
        return h;
    }
}