// Watchable.java
package auction;

public interface Watchable {
    void addWatcher(User user);
    void removeWatcher(User user);
    void notifyWatchers(String eventMessage);
}

