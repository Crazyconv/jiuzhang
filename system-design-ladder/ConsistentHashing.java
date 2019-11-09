import java.util.*;

public class ConsistentHashing {
    /*
     * @param n: a positive integer
     * @return: n x 3 matrix
     */

    class Item {
        public int id;
        public int start;
        public int end;
        public int length;
        public Item(int id, int start, int end) {
            this.id = id;
            this.start = start;
            this.end = end;
            this.length = end - start + 1;
        }
    }
    public List<List<Integer>> consistentHashing(int n) {
        if (n == 0)
            return new ArrayList<>();

        PriorityQueue<Item> pq = new PriorityQueue<Item>(new Comparator<Item>() {
            public int compare(Item i1, Item i2) {
                int result = Integer.compare(i2.length, i1.length);
                if (result != 0)
                    return result;
                else
                    return Integer.compare(i1.id, i2.id);
            }
        });
        pq.add(new Item(1, 0, 359));

        int id = 2;
        while (n > 1) {
            Item item = pq.poll();
            pq.add(new Item(item.id, item.start, (item.start + item.end)/2));
            pq.add(new Item(id, (item.start + item.end)/2 + 1, item.end));
            n--;
            id++;
        }

        Item[] items = pq.toArray(new Item[pq.size()]);
        Arrays.sort(items, (a, b) -> Integer.compare(a.start, b.start));
        List<List<Integer>> result = new ArrayList();
        for (int i = 0; i < items.length; i++) 
            result.add(Arrays.asList(items[i].start, items[i].end, items[i].id));
        return result;
    }
}