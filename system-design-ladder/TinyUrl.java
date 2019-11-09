import java.util.*;

public class TinyUrl {
    private static String prefix = "http://tiny.url/";
    private int autoId = 0;
    private char[] chars = new char[62]; 
    private HashMap<String, String> longToShortMap = new HashMap<>();
    private HashMap<String, String> shortToLongMap = new HashMap<>();

    public TinyUrl() {
        for (int i = 0; i <= 9; i++) {
            this.chars[i] = (char) ('0' + i);
        }
        for (int i = 10; i < 36; i++) {
            this.chars[i] = (char) (i - 10 + 'a');
        }
        for (int i = 36; i < 62; i++) {
            this.chars[i] = (char) (i - 36 + 'A');
        }
    }

    /*
     * @param long_url: a long url
     * @return: a short url starts with http://tiny.url/
     */
    public String longToShort(String long_url) {
        if (this.longToShortMap.containsKey(long_url))
            return TinyUrl.prefix + this.longToShortMap.get(long_url);
        
        int id = this.autoId;
        this.autoId++;

        StringBuilder sb = new StringBuilder();
        while (id > 0) {
            sb.append(this.chars[id%62]);
            id /= 62;
        }
        while (sb.length() < 6)
            sb.append(this.chars[0]);
        String key = sb.toString();
        this.longToShortMap.put(long_url, key);
        this.shortToLongMap.put(key, long_url);
        return TinyUrl.prefix + key;
    }

    /*
     * @param short_url: a short url starts with http://tiny.url/
     * @return: a long url
     */
    public String shortToLong(String short_url) {
        int index = short_url.lastIndexOf("/");
        String key = short_url.substring(index+1);
        return this.shortToLongMap.getOrDefault(key, "");
    }
}