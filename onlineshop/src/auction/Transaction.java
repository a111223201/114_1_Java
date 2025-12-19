// Transaction.java - 拍賣結束後的成交紀錄
package auction;

import java.time.LocalDateTime;

public class Transaction {
    private String transactionId;
    private Biddable auction;
    private Buyer buyer;
    private double finalAmount;
    private LocalDateTime timestamp;
    private Payment payment;
    private ShippingInfo shipping;

    public Transaction(String id, Biddable auction, Bid winningBid) {
        this.transactionId = id;
        this.auction = auction;
        this.buyer = winningBid.getBidder();
        this.finalAmount = winningBid.getAmount();
        this.timestamp = LocalDateTime.now();
    }

    public void setPayment(Payment p) { this.payment = p; }
    public void setShipping(ShippingInfo s) { this.shipping = s; }
}