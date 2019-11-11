import java.util.PriorityQueue;

public class MergeKSortedArrays {
    class Item {
        public int row;
        public int col;
        public Item(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }
     /**
     * @param arrays: k sorted integer arrays
     * @return: a sorted array
     */
    public int[] mergekSortedArrays(int[][] arrays) {
        PriorityQueue<Item> pq = new PriorityQueue<>((a, b) -> Integer.compare(arrays[a.row][a.col], arrays[b.row][b.col]));
        int length = 0;
        for (int i = 0; i < arrays.length; i++) {
            length += arrays[i].length;
            if (arrays[i].length > 0) {
                pq.add(new Item(i, 0));
            }
        }

        int[] result = new int[length];
        int index = 0;
        while (!pq.isEmpty()) {
            Item item = pq.poll();
            result[index++] = arrays[item.row][item.col];
            if (arrays[item.row].length > item.col) {
                item.col++;
                pq.add(item);
            }
        }
        return result;
    }
}