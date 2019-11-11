import java.util.*;

public class Typeahead {
    HashMap<String, List<String>> dic = new HashMap<>();

    /*
    * @param dict: A dictionary of words dict
    */
    public Typeahead(Set<String> dict) {
        HashMap<String, HashSet<String>> dicTemp = new HashMap<>();
        for (String word : dict) {
            for (int i = 0; i < word.length(); i++) {
                for (int j = i+1; j <= word.length(); j++) {
                    String substring = word.substring(i, j);
                    dicTemp.putIfAbsent(substring, new HashSet<>());
                    dicTemp.get(substring).add(word);
                }
            }
        }

        for (Map.Entry<String, HashSet<String>> entry : dicTemp.entrySet()) {
            ArrayList<String> list = new ArrayList<>(entry.getValue());
            this.dic.put(entry.getKey(), list);
        }
    }

    /*
     * @param str: a string
     * @return: a list of words
     */
    public List<String> search(String str) {
        return this.dic.getOrDefault(str, new ArrayList<String>());
    }
}