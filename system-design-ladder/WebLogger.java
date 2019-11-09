import java.util.Iterator;
import java.util.LinkedList;

public class WebLogger {
    class Item {
        public int timestamp;
        public int count;
        public Item (int timestamp) {
            this.timestamp = timestamp;
            this.count = 1;
        }
    }

    private LinkedList<Item> counts = new LinkedList<Item>();

    public WebLogger() {}

    /*
     * @param timestamp: An integer
     * @return: nothing
     */
    public void hit(int timestamp) {
        if (this.counts.size() > 0 && this.counts.getLast().timestamp == timestamp)
            this.counts.getLast().count++;
        else
            this.counts.add(new Item(timestamp));
    }

    /*
     * @param timestamp: An integer
     * @return: An integer
     */
    public int get_hit_count_in_last_5_minutes(int timestamp) {
        Iterator<Item> iterator = this.counts.iterator();
        while (iterator.hasNext()) {
            Item item = iterator.next();
            if (item.timestamp <= timestamp - 300)
                iterator.remove();
            else
                break;
        }

        int result = 0;
        for (Item item : this.counts)
            result += item.count;
        return result;
    }
}