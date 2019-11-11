import java.util.Iterator;

class OutputCollector<K, V> {
    public void collect(K key, V value) {}
        // Adds a key/value pair to the output buffer
}

public class NGram {

    public static class Map {
        public void map(String _, int n, String str, OutputCollector<String, Integer> output) {
            for (int i = 0; i + n <= str.length(); i++) 
                output.collect(str.substring(i, i+n), 1);
        }
    }

    public static class Reduce {
        public void reduce(String key, Iterator<Integer> values, OutputCollector<String, Integer> output) {
            int count = 0;
            while (values.hasNext()) {
                count++;
                values.next();
            }
            output.collect(key, count);
        }
    }
}