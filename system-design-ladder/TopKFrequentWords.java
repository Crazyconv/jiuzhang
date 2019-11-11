import java.util.*;

class OutputCollector<K, V> {
    public void collect(K key, V value) {}
        // Adds a key/value pair to the output buffer
}

class Document {
    public int id;
    public String content;
}

class Item {
    public String word;
    public int count;
    public Item(String word, int count) {
        this.word = word;
        this.count = count;
    }
}

public class TopKFrequentWords {

    public static class Map {
        public void map(String _, Document value,
                        OutputCollector<String, Integer> output) {
            for (String word : value.content.split("[\\s]+")) 
                output.collect(word, 1);
        }
    }

    public static class Reduce {
        private int k; 
        private PriorityQueue<Item> pq = new PriorityQueue<>(new Comparator<Item>() {
            public int compare(Item i1, Item i2) {
                int result = Integer.compare(i1.count, i2.count);
                if (result != 0)
                    return result;
                return i2.word.compareTo(i1.word);
            }
        });

        public void setup(int k) {
            this.k = k;
        }   

        public void reduce(String key, Iterator<Integer> values) {
            int count = 0;
            while (values.hasNext()) {
                count++;
                values.next();
            }
            this.pq.add(new Item(key, count));
            if (this.pq.size() > this.k)
                this.pq.poll();
        }

        public void cleanup(OutputCollector<String, Integer> output) {
            ArrayList<Item> list = new ArrayList<Item>();
            while (pq.size() > 0)
                list.add(pq.poll());
            Collections.reverse(list);
            for (Item item : list)
                output.collect(item.word, item.count);
        }
    }
}