import java.util.*;

class OutputCollector<K, V> {
    public void collect(K key, V value) {}
        // Adds a key/value pair to the output buffer
}

public class Anagram {

    public static class Map {
        public void map(String key, String value, OutputCollector<String, String> output) {
            for (String word : value.split("[\\s]+")) {
                char[] chars = word.toCharArray();
                Arrays.sort(chars);
                output.collect(new String(chars), word);
            }
        }
    }

    public static class Reduce {
        public void reduce(String key, Iterator<String> values, OutputCollector<String, List<String>> output) {
            ArrayList<String> result = new ArrayList<String>();
            while (values.hasNext()) {
                result.add(values.next());
            }
            output.collect(key, result);
        }
    }
}
