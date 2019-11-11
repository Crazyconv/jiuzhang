import java.util.Iterator;

class OutputCollector<K, V> {
    public void collect(K key, V value) {}
        // Adds a key/value pair to the output buffer
}

public class WordCount {

    public static class Map {
        public void map(String key, String value, OutputCollector<String, Integer> output) {
            for (String word : value.split("[\\s]+"))
                output.collect(word, 1);
        }
    }

    public static class Reduce {
        public void reduce(String key, Iterator<Integer> values, OutputCollector<String, Integer> output) {
            int result = 0;
            while (values.hasNext()) {
                result++;
                values.next();
            }
            output.collect(key, result);
        }
    }
}
