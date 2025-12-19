package auction;

/**
 * Rateable 介面
 * 規格要求：可評價、計算平均分、統計評價數
 */
public interface Rateable {
    void addRating(User rater, int score, String comment);
    double getAverageRating();
    int getRatingCount();
}