package auction;

import java.time.LocalDateTime;

public class ReserveAuction extends StandardAuction {
    private double reservePrice;

    public ReserveAuction(String id, Seller seller, String title, double startPrice,
                          double minIncrement, double reservePrice, LocalDateTime endTime) {
        super(id, seller, title, startPrice, minIncrement, endTime);
        this.reservePrice = reservePrice;
    }

    /**
     * 檢查拍賣結果
     * 規格：若最高出價 < 底價，則回傳流標狀態
     */
    public boolean isSuccessfullySold() {
        if (!isExpired()) return false;
        Bid highest = getCurrentHighestBid();
        return highest != null && highest.getAmount() >= reservePrice;
    }

    public double getReservePrice() { return reservePrice; }
}