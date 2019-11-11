import java.util.*;

class TrieNode {
    public NavigableMap<Character, TrieNode> children;
    public List<Integer> top10;
    public TrieNode() {
        children = new TreeMap<Character, TrieNode>();
        top10 = new ArrayList<Integer>();
    }
}

public class TrieService {

    private TrieNode root = null;

    public TrieService() {
        root = new TrieNode();
    }

    public TrieNode getRoot() {
        // Return root of trie root, and 
        // lintcode will print the tree struct.
        return root;
    }

    // @param word a string
    // @param frequency an integer
    public void insert(String word, int frequency) {
        TrieNode node = root;
        for (int i = 0; i < word.length(); i++) {
            node.children.putIfAbsent(word.charAt(i), new TrieNode());
            node = node.children.get(word.charAt(i));

            node.top10.add(frequency);
            int index = node.top10.size() - 2;
            while (index >= 0 && node.top10.get(index) < frequency) {
                node.top10.set(index+1, node.top10.get(index));
                index--;
            }
            node.top10.set(index+1, frequency);
            if (node.top10.size() > 10)
                node.top10.remove(node.top10.size()-1);
        }
    }
 }