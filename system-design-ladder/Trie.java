public class Trie {
    class Node {
        public boolean isEnd;
        public Node[] children = new Node[26];
    }

    private Node root = new Node();

    public Trie() {}

    /*
     * @param word: a word
     * @return: nothing
     */
    public void insert(String word) {
        Node node = root;
        for (int i = 0; i < word.length(); i++) {
            int index = word.charAt(i) - 'a';
            if (node.children[index] == null)
                node.children[index] = new Node();
            node = node.children[index];
        }
        node.isEnd = true;
    }

    /*
     * @param word: A string
     * @return: if the word is in the trie.
     */
    public boolean search(String word) {
        Node node = root;
        for (int i = 0; i < word.length(); i++) {
            int index = word.charAt(i) - 'a';
            node = node.children[index];
            if (node == null)
                return false;
        }
        return node.isEnd;
    }

    /*
     * @param prefix: A string
     * @return: if there is any word in the trie that starts with the given prefix.
     */
    public boolean startsWith(String prefix) {
        Node node = root;
        for (int i = 0; i < prefix.length(); i++) {
            int index = prefix.charAt(i) - 'a';
            node = node.children[index];
            if (node == null)
                return false;
        }
        return true;
    }
}