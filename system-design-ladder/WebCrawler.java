import java.util.*;

class HtmlHelper {
    public static List<String> parseUrls(String url) { return null; }
        // Get all urls from a webpage of given url. 
}

public class WebCrawler {
    /**
     * @param url: a url of root page
     * @return: all urls
     */
    public List<String> crawler(String url) {
        HashSet<String> parsed = new HashSet<>();
        LinkedList<String> list = new LinkedList<>();
        parsed.add(url);
        list.addLast(url);
        while (!list.isEmpty()) {
            url = list.pollFirst();
            for (String nextUrl : HtmlHelper.parseUrls(url)) {
                if (nextUrl.indexOf("wikipedia", 0) != -1 && parsed.add(nextUrl)) 
                    list.addLast(nextUrl);
            }
        }
        return new ArrayList<String>(parsed);
    }
}