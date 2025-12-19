// Bid.java (基礎類別)
package auction;
import java.time.LocalDateTime;

public abstract class Bid {
    protected Buyer bidder;
    protected double amount;
    protected LocalDateTime bidTime;

    public Bid(Buyer bidder, double amount, LocalDateTime bidTime) {
        this.bidder = bidder;
        this.amount = amount;
        this.bidTime = bidTime;
    }

    public double getAmount() { return amount; }
    public Buyer getBidder() { return bidder; }
    public abstract String getBidType(); // 用於區分出價類型
}