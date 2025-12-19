package auction;

/**
 * 自動出價類別
 * 規格：當被超越時自動加價至最小增額，直到達到上限
 */
public class AutoBid {
    private Buyer bidder;
    private double maxLimit;

    public AutoBid(Buyer bidder, double maxLimit) {
        this.bidder = bidder;
        this.maxLimit = maxLimit;
    }

    /**
     * 與新出價競爭的邏輯
     * @param incomingAmount 外部新的出價金額
     * @param minIncrement 最小增額
     * @return 產生的新出價金額，若無法超越則回傳 -1
     */
    public double compete(double incomingAmount, double minIncrement) {
        // 若新出價 >= 我的上限，則無法再跟
        if (incomingAmount >= maxLimit) {
            return -1;
        }

        // 自動加價 = 新出價 + 最小增額
        double autoAmount = incomingAmount + minIncrement;

        // 若加價後超過上限，則出價上限金額
        if (autoAmount > maxLimit) {
            autoAmount = maxLimit;
        }

        return autoAmount;
    }

    public Buyer getBidder() { return bidder; }
    public double getMaxLimit() { return maxLimit; }
}