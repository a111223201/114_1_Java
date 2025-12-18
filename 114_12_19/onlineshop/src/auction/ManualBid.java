// ManualBid.java (手動出價)
package auction;
import java.time.LocalDateTime;

public class ManualBid extends Bid {
    public ManualBid(Buyer bidder, double amount, LocalDateTime bidTime) {
        super(bidder, amount, bidTime);
    }

    @Override
    public String getBidType() {
        return "Manual";
    }
}