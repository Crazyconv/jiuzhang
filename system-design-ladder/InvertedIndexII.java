import java.util.*;

class Document {
    public int id;
    public String content;
}

public class InvertedIndexII {
    /**
     * @param docs a list of documents
     * @return an inverted index
     */
    public Map<String, List<Integer>> invertedIndex(List<Document> docs) {
        HashMap<String, HashSet<Integer>> result = new HashMap<>();
        for (Document doc : docs) {
            for (String word : doc.content.split("[^\\w]+")) {
                if (!word.isEmpty()) {
                    result.putIfAbsent(word, new HashSet<>());
                    result.get(word).add(doc.id);
                }
            }
        }
        HashMap<String, List<Integer>> result2 = new HashMap<>();
        for (Map.Entry<String, HashSet<Integer>> entry : result.entrySet()) {
            ArrayList<Integer> list = new ArrayList<>(entry.getValue());
            Collections.sort(list);
            result2.put(entry.getKey(), list);
        }
        return result2;
    }
}