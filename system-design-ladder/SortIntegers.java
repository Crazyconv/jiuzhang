import java.util.*;

class OutputCollector<K, V> {
    public void collect(K key, V value) {}
}

class Item {
    public int value;
    public Iterator<Integer> iterator;
    public Item(int value, Iterator<Integer> iterator) {
        this.value = value;
        this.iterator = iterator;
    }
}

public class SortIntegers {
    public static class Map {
        public void map(int _, List<Integer> value, OutputCollector<String, List<Integer>> output) {
            Collections.sort(value);
            output.collect("ignore", value);
        }
    }
        
    public static class Reduce {
        public void reduce(String key, List<List<Integer>> values, OutputCollector<String, List<Integer>> output) {                   
            ArrayList<Integer> result = new ArrayList<>();
            PriorityQueue<Item> pq = new PriorityQueue<>((a, b) -> Integer.compare(a.value, b.value));
            for (List<Integer> list : values) {
                Iterator<Integer> iterator = list.iterator();
                if (iterator.hasNext()) 
                    pq.add(new Item(iterator.next(), iterator));
            }
            while (pq.size() > 0) {
                Item min = pq.poll();
                result.add(min.value);
                if (min.iterator.hasNext()) {
                    min.value = min.iterator.next();
                    pq.add(min);
                }
            }
            output.collect("ignore", result);
        }
    }
}