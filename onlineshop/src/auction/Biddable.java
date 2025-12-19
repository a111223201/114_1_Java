// Biddable.java
package auction;
import java.util.ArrayList;

public interface Biddable {
    auction.BidResult placeBid(auction.Buyer buyer, double amount);
    auction.Bid getCurrentHighestBid();
    ArrayList<auction.Bid> getBidHistory();
}


