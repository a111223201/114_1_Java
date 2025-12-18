// AuctionEvent.java (簡單化處理)
package auction;

public class AuctionEvent {
    private String type;
    private String message;

    public AuctionEvent(String type, String message) {
        this.type = type;
        this.message = message;
    }
    public String getMessage() { return message; }
}