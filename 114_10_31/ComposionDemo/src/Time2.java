// 檔案路徑: c:\Users\m306\Desktop\114_1_Java\114_10_31\ComposionDemo\src\Time2.java
public class Time2 {
    private int hour; // 小時 0 - 23
    private int minute; // 分 0 - 59
    private int second; // 秒 0 - 59

    // Time2 無參數建構子:
    // 將每個成員變數初始化為 0
    public Time2() {
        this(0, 0, 0); // 呼叫三參數建構子
    }

    // Time2 建構子: 指定小時，分鐘與秒預設為 0
    public Time2(int hour) {
        this(hour, 0, 0); // 呼叫三參數建構子
    }

    // Time2 建構子: 指定小時與分鐘，秒預設為 0
    public Time2(int hour, int minute) {
        this(hour, minute, 0); // 呼叫三參數建構子
    }

    // Time2 建構子: 指定小時、分鐘與秒
    public Time2(int hour, int minute, int second) {
        if (hour < 0 || hour >= 24) {
            throw new IllegalArgumentException("小時必須在 0-23 之間");
        }

        if (minute < 0 || minute >= 60) {
            throw new IllegalArgumentException("分鐘必須在 0-59 之間");
        }

        if (second < 0 || second >= 60) {
            throw new IllegalArgumentException("秒數必須在 0-59 之間");
        }

        this.hour = hour;
        this.minute = minute;
        this.second = second;
    }

    // Time2 建構子: 傳入另一個 Time2 物件
    public Time2(Time2 time) {
        // 呼叫三參數建構子
        this(time.hour, time.minute, time.second);
    }

    // 設定方法
    // 使用 24 小時制設定時間；驗證資料合法性
    public void setTime(int hour, int minute, int second) {
        if (hour < 0 || hour >= 24) {
            throw new IllegalArgumentException("小時必須在 0-23 之間");
        }

        if (minute < 0 || minute >= 60) {
            throw new IllegalArgumentException("分鐘必須在 0-59 之間");
        }

        if (second < 0 || second >= 60) {
            throw new IllegalArgumentException("秒數必須在 0-59 之間");
        }

        this.hour = hour;
        this.minute = minute;
        this.second = second;
    }

    // 驗證並設定小時
    public void setHour(int hour) {
        if (hour < 0 || hour >= 24) {
            throw new IllegalArgumentException("小時必須在 0-23 之間");
        }

        this.hour = hour;
    }

    // 驗證並設定分鐘
    public void setMinute(int minute) {
        if (minute < 0 || minute >= 60) {
            throw new IllegalArgumentException("分鐘必須在 0-59 之間");
        }

        this.minute = minute;
    }

    // 驗證並設定秒數
    public void setSecond(int second) {
        if (second < 0 || second >= 60) {
            throw new IllegalArgumentException("秒數必須在 0-59 之間");
        }

        this.second = second;
    }

    // 取得方法
    // 取得小時
    public int getHour() {return hour;}

    // 取得分鐘
    public int getMinute() {return minute;}

    // 取得秒數
    public int getSecond() {return second;}

    // 以國際時間格式 (HH:MM:SS) 回傳字串
    public String toUniversalString() {
        return String.format(
                "%02d:%02d:%02d", getHour(), getMinute(), getSecond());
    }

    // 以 12 小時制格式 (H:MM:SS 上午 或 下午) 回傳字串
    public String toString() {
        return String.format("%d:%02d:%02d %s",
                ((getHour() == 0 || getHour() == 12) ? 12 : getHour() % 12),
                getMinute(), getSecond(), (getHour() < 12 ? "上午" : "下午"));
    }
}