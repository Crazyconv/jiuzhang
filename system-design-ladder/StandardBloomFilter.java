import java.util.BitSet;

public class StandardBloomFilter {
    private int k;
    private BitSet bitSet;
    private int module;
    /*
     * @param k: An integer
     */
    public StandardBloomFilter(int k) {
        this.k = k;
        this.module = 100000+k;
        this.bitSet = new BitSet(this.module);
    }

    /*
     * @param word: A string
     * @return: nothing
     */
    public void add(String word) {
        for (int i = 0; i < k; i++) {
            int index = (word + Integer.toString(i)).hashCode() % this.module;
            if (index < 0)
                index += this.module;
            this.bitSet.set(index);
        }
    }

    /*
     * @param word: A string
     * @return: True if contains word
     */
    public boolean contains(String word) {
        for (int i = 0; i < k; i++) {
            int index = (word + Integer.toString(i)).hashCode() % this.module;
            if (index < 0)
                index += this.module;
            if (!this.bitSet.get(index))
                return false;
        }
        return true;
    }
}