import java.util.*;

class TrieNode {
    public NavigableMap<Character, TrieNode> children;
    public TrieNode() {
        children = new TreeMap<Character, TrieNode>();
    }
}

class TrieSerialization {
    /**
     * This method will be invoked first, you should design your own algorithm 
     * to serialize a trie which denote by a root node to a string which
     * can be easily deserialized by your own "deserialize" method later.
     */
    public String serialize(TrieNode root) {
        StringBuilder sb = new StringBuilder();

        LinkedList<TrieNode> queue = new LinkedList<>();
        queue.addLast(root);
        while (!queue.isEmpty()) {
            TrieNode node = queue.pollFirst();
            for (Map.Entry<Character, TrieNode> entry : node.children.entrySet()) {
                sb.append(entry.getKey());
                queue.addLast(entry.getValue());
            }
            sb.append(";");
        }
        if (sb.length() > 0)
            sb.setLength(sb.length()-1);
        return sb.toString();
    }

    /**
     * This method will be invoked second, the argument data is what exactly
     * you serialized at method "serialize", that means the data is not given by
     * system, it's given by your own serialize method. So the format of data is
     * designed by yourself, and deserialize it here as you serialize it in 
     * "serialize" method.
     */
    public TrieNode deserialize(String data) {
        LinkedList<TrieNode> queue = new LinkedList<>();
        TrieNode root = new TrieNode();
        TrieNode node = root;
        for (int i = 0; i < data.length(); i++) {
            if (data.charAt(i) != ';') {
                TrieNode child = new TrieNode();
                node.children.put(data.charAt(i), child);
                queue.addLast(child);
            } else {
                node = queue.pollFirst();
            }
        }
        return root;
    }
}
