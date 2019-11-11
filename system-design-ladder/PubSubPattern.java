import java.util.*;

class PushNotification {
    public static void notify(int user_id, String the_message) {}
};

public class PubSubPattern {
    HashMap<String, HashSet<Integer>> subscribers = new HashMap<>();

    public PubSubPattern(){ }
    
    /**
     * @param channel: the channel's name
     * @param user_id: the user who subscribes the channel
     * @return: nothing
     */
    public void subscribe(String channel, int user_id) {
        this.subscribers.putIfAbsent(channel, new HashSet<Integer>());
        this.subscribers.get(channel).add(user_id);
    }

    /**
     * @param channel: the channel's name
     * @param user_id: the user who unsubscribes the channel
     * @return: nothing
     */
    public void unsubscribe(String channel, int user_id) {
        if (this.subscribers.containsKey(channel))
            this.subscribers.get(channel).remove(user_id);
    }

    /**
     * @param channel: the channel's name
     * @param message: the message need to be delivered to the channel's subscribers
     * @return: nothing
     */
    public void publish(String channel, String message) {
        if (this.subscribers.containsKey(channel)) {
            for (int userId : this.subscribers.get(channel))
                PushNotification.notify(userId, message);
        }
    }
}