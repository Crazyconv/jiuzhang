import java.util.HashMap;
import java.util.LinkedHashSet;

public class LFUCache {
    private int minFreq = 0;
    private HashMap<Integer, Integer> freqMap = new HashMap<>();
    private HashMap<Integer, LinkedHashSet<Integer>> freqListMap = new HashMap<>();
    private HashMap<Integer, Integer> valueMap = new HashMap<>();
    private int capacity = 0;

    /*
    * @param capacity: An integer
    */public LFUCache(int capacity) {
        this.capacity = capacity;
    }

    /*
     * @param key: An integer
     * @param value: An integer
     * @return: nothing
     */
    public void set(int key, int value) {
        if (this.valueMap.containsKey(key)) {
            this.increFreq(key);
            this.valueMap.put(key, value);
            return;
        }

        if (this.valueMap.size() == this.capacity) {
            int keyToRemove = this.freqListMap.get(this.minFreq).iterator().next();
            this.valueMap.remove(keyToRemove);
            this.freqMap.remove(keyToRemove);
            this.freqListMap.get(this.minFreq).remove(keyToRemove);
            if (this.freqListMap.get(this.minFreq).isEmpty())
                this.freqListMap.remove(this.minFreq);
        }

        this.valueMap.put(key, value);
        this.freqMap.put(key, 1);
        this.freqListMap.putIfAbsent(1, new LinkedHashSet<>());
        this.freqListMap.get(1).add(key);
        this.minFreq = 1;
    }

    /*
     * @param key: An integer
     * @return: An integer
     */
    public int get(int key) {
        if (!this.valueMap.containsKey(key))
            return -1;
        
        this.increFreq(key);

        return this.valueMap.get(key);
    }

    private void increFreq(int key) {
        int freq = this.freqMap.get(key);
        this.freqMap.put(key, freq+1);
        this.freqListMap.putIfAbsent(freq+1, new LinkedHashSet<>());
        this.freqListMap.get(freq+1).add(key);
        this.freqListMap.get(freq).remove(key);
        if (this.freqListMap.get(freq).isEmpty()) {
            this.freqListMap.remove(freq);
            if (this.minFreq == freq)
                this.minFreq ++;
        }
    }
}