import java.util.*;

class OutputCollector<K, V> {
    public void collect(K key, V value) {}
        // Adds a key/value pair to the output buffer
}

class Document {
    public int id;
    public String content;
}

public class InvertedIndex {

    public static class Map {
        public void map(String _, Document value, OutputCollector<String, Integer> output) {
            for (String word : value.content.split("[\\s]+"))
                output.collect(word, value.id);
        }
    }

    public static class Reduce {
        public void reduce(String key, Iterator<Integer> values, OutputCollector<String, List<Integer>> output) {
            HashSet<Integer> result = new HashSet<>();
            while (values.hasNext())
                result.add(values.next());
            output.collect(key, new ArrayList<Integer>(result));
        }
    }
}