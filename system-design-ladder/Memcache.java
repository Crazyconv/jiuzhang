import java.util.HashMap;

public class Memcache {
    class Item {
        public int value;
        public int validUntil;
        public Item(int value, int validUntil) {
            this.value = value;
            this.validUntil = validUntil;
        }
    }

    private HashMap<Integer, Item> map = new HashMap<>();

    public Memcache() {}

    /*
     * @param curtTime: An integer
     * @param key: An integer
     * @return: An integer
     */
    public int get(int curtTime, int key) {
        if (!this.map.containsKey(key))
            return Integer.MAX_VALUE;
        if (this.map.get(key).validUntil < curtTime) {
            this.map.remove(key);
            return Integer.MAX_VALUE;
        }
        return this.map.get(key).value;
    }

    /*
     * @param curtTime: An integer
     * @param key: An integer
     * @param value: An integer
     * @param ttl: An integer
     * @return: nothing
     */
    public void set(int curtTime, int key, int value, int ttl) {
        this.map.put(key, new Item(value, ttl != 0 ? curtTime + ttl - 1 : Integer.MAX_VALUE));
    }

    /*
     * @param curtTime: An integer
     * @param key: An integer
     * @return: nothing
     */
    public void delete(int curtTime, int key) {
        this.map.remove(key);
    }

    /*
     * @param curtTime: An integer
     * @param key: An integer
     * @param delta: An integer
     * @return: An integer
     */
    public int incr(int curtTime, int key, int delta) {
        if (!this.map.containsKey(key))
            return Integer.MAX_VALUE;
        if (this.map.get(key).validUntil < curtTime) {
            this.map.remove(key);
            return Integer.MAX_VALUE;
        }
        this.map.get(key).value += delta;
        return this.map.get(key).value;
    }

    /*
     * @param curtTime: An integer
     * @param key: An integer
     * @param delta: An integer
     * @return: An integer
     */
    public int decr(int curtTime, int key, int delta) {
        return this.incr(curtTime, key, -delta);
    }
}