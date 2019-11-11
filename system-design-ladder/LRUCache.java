import java.util.HashMap;

public class LRUCache {
    class Node {
        public int key;
        public int value;
        public Node prev;
        public Node next;
    }

    private Node fakeHead;
    private Node fakeTail;
    private HashMap<Integer, Node> map;
    private int capacity;

    /*
    * @param capacity: An integer
    */public LRUCache(int capacity) {
        this.capacity = capacity;
        this.fakeHead = new Node();
        this.fakeTail = new Node();
        this.fakeHead.next = this.fakeTail;
        this.fakeTail.prev = this.fakeHead;
        this.map = new HashMap<>();
    }

    /*
     * @param key: An integer
     * @return: An integer
     */
    public int get(int key) {
        if (!this.map.containsKey(key))
            return -1;
        Node node = this.map.get(key);
        this.moveToHead(node);
        return node.value;
    }

    /*
     * @param key: An integer
     * @param value: An integer
     * @return: nothing
     */
    public void set(int key, int value) {
        Node node;
        if (this.map.containsKey(key)) {
            node = this.map.get(key);
            node.value = value;
            this.moveToHead(node);
            return;
        }

        node = new Node();
        node.key = key;
        node.value = value;
        this.addToHead(node);
        this.map.put(key, node);

        if (this.map.size() > this.capacity) {
            node = this.fakeTail.prev;
            this.remove(node);
            this.map.remove(node.key);
        }
    }

    private void addToHead(Node node) {
        node.prev = this.fakeHead;
        node.next = this.fakeHead.next;
        this.fakeHead.next = node;
        node.next.prev = node;
    }

    private void remove(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    private void moveToHead(Node node) {
        this.remove(node);
        this.addToHead(node);
    }
}