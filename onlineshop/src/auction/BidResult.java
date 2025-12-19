// BidResult.java
package auction;

public class BidResult {
    private boolean success;
    private String message;
    private boolean wasExtended;

    public BidResult(boolean success, String message, boolean wasExtended) {
        this.success = success;
        this.message = message;
        this.wasExtended = wasExtended;
    }
    @Override
    public String toString() {
        return String.format("Status: %s, Msg: %s, Extended: %b",
                success ? "SUCCESS" : "FAIL", message, wasExtended);
    }
}
