import java.util.*;

class OutputCollector<K, V> {
    public void collect(K key, V value) {}
        // Adds a key/value pair to the output buffer
}

class Document {
    public int count;
    public String content;
}

class Pair {
    private String content;
    private int count;
    
    Pair(String key, int value) {
        this.content = key;
        this.count = value;
    }
    public String getContent(){
            return this.content;
        }
        public int getCount(){
        return this.count;
    }
}

public class GoogleSuggestion {

    public static class Map {
        public void map(Document value, OutputCollector<String, Pair> output) {
            Pair p = new Pair(value.content, value.count);
            for (int i = 1; i <= value.content.length(); i++)
                output.collect(value.content.substring(0, i), p);
        }
    }

    public static class Reduce {
        public void setup() {
            // initialize your data structure here
        }   
		public void reduce(String key, Iterator<Pair> values, OutputCollector<String, Pair> output) {
            PriorityQueue<Pair> pq = new PriorityQueue<>(new Comparator<Pair>() {
                public int compare(Pair p1, Pair p2) {
                    int result = Integer.compare(p1.getCount(), p2.getCount());
                    if (result != 0)
                        return result;
                    return p2.getContent().compareTo(p1.getContent());
                }
            });

    		while (values.hasNext()) {
                pq.add(values.next());
                if (pq.size() > 10) 
                    pq.poll();
            }

            ArrayList<Pair> list = new ArrayList<>(pq.size());
            while (pq.size() > 0)
                list.add(pq.poll());
            Collections.reverse(list);
            for (Pair p : list)
                output.collect(key, p);
        }
    }
}