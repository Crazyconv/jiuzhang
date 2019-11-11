public class CountingBloomFilter {
    private int k;
    private int[] counts;
    private int module;

    /*
     * @param k: An integer
     */public CountingBloomFilter(int k) {
        this.k = k;
        this.module = 1000000+k;
        this.counts = new int[this.module];
    }

    /*
     * @param word: A string
     * @return: nothing
     */
    public void add(String word) {
        for (int i = 0; i < k; i++) {
            int index = this.getIndex(word, i);
            this.counts[index]++;
        }
    }

    /*
     * @param word: A string
     * @return: nothing
     */
    public void remove(String word) {
        for (int i = 0; i < k; i++) {
            int index = this.getIndex(word, i);
            this.counts[index]--;
        }
    }

    /*
     * @param word: A string
     * @return: True if contains word
     */
    public boolean contains(String word) {
        for (int i = 0; i < k; i++) {
            int index = this.getIndex(word, i);
            if (this.counts[index] <= 0)
                return false;
        }
        return true;
    }

    private int getIndex(String word, int i) {
        int index = (word + Integer.toString(i)).hashCode() % this.module;
        if (index < 0)
            index += this.module;
        return index;
    }
}