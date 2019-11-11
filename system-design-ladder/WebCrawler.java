import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

class HtmlHelper {
    public static List<String> parseUrls(String url) { return null; }
        // Get all urls from a webpage of given url. 
}

public class WebCrawler {
    class CrawlerThread extends Thread {
        private ConcurrentHashMap<String, Boolean> parsed;
        private ConcurrentLinkedQueue<String> queue;
        private AtomicInteger remainings;

        public CrawlerThread(ConcurrentHashMap<String, Boolean> parsed, ConcurrentLinkedQueue<String> queue, AtomicInteger remainings) {
            this.parsed = parsed;
            this.queue = queue;
            this.remainings = remainings;
        }

        public void run() 
        { 
            while (this.remainings.get() > 0) {
                String url = queue.poll();
                if (url == null)
                    continue;
                
                for (String nextUrl : HtmlHelper.parseUrls(url)) {
                    if (nextUrl.indexOf("wikipedia", 0) != -1 && parsed.put(nextUrl, false) == null) {
                        this.queue.add(nextUrl);
                        this.remainings.incrementAndGet();
                    }
                }
                this.remainings.decrementAndGet();
            }
        } 
    }

    
    /**
     * @param url: a url of root page
     * @return: all urls
     */
    public List<String> crawler(String url) {
        ConcurrentHashMap<String, Boolean> parsed = new ConcurrentHashMap<>();
        ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();
        AtomicInteger remainings = new AtomicInteger(1);
        parsed.put(url, false);
        queue.add(url);

        Thread[] threads = new Thread[3];
        for (int i = 0; i < 3; i++)
            threads[i] = new CrawlerThread(parsed, queue, remainings);
        for (int i = 0; i < 3; i++)
            threads[i].start();
        for (int i = 0; i < 3; i++) {
            try {
                threads[i].join();
            } catch (Exception e) {

            }
        }

        return new ArrayList<String>(parsed.keySet());
    }
}