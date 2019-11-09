import java.util.*;

class Column {
    public int key;
    public String value;
    public Column(int key, String value) {
        this.key = key;
        this.value = value;
    }
}

public class MiniCassandra {
    private HashMap<String, TreeMap<Integer, String>> db = new HashMap<>();

    public MiniCassandra() {}

    /*
     * @param raw_key: a string
     * @param column_key: An integer
     * @param column_value: a string
     * @return: nothing
     */
    public void insert(String row_key, int column_key, String value) {
        this.db.putIfAbsent(row_key, new TreeMap<Integer,String>());
        this.db.get(row_key).put(column_key, value);
    }

    /*
     * @param row_key: a string
     * @param column_start: An integer
     * @param column_end: An integer
     * @return: a list of Columns
     */
    public List<Column> query(String row_key, int column_start, int column_end) {
        ArrayList<Column> result = new ArrayList<>();

        if (this.db.containsKey(row_key)) {
            for (Map.Entry<Integer, String> entry : this.db.get(row_key).subMap(column_start, column_end+1).entrySet()) {
                result.add(new Column(entry.getKey(), entry.getValue()));
            }
        }

        return result;
    }
}