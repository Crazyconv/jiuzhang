import java.util.*;

class Tweet {
    public int id;
    public int user_id;
    public String text;
    public static Tweet create(int user_id, String tweet_text) {
        return new Tweet();
    }
}


public class MiniTwitter {

    private int currentTime = 0;
    
    private HashMap<Integer, LinkedList<ExtendedTweet>> timeLineTable = new HashMap<>();

    private HashMap<Integer, HashSet<Integer>> followsTable = new HashMap<>();

    public MiniTwitter() { }

    /*
     * @param user_id: An integer
     * @param tweet_text: a string
     * @return: a tweet
     */
    public Tweet postTweet(int user_id, String tweet_text) {
        Tweet tweet = Tweet.create(user_id, tweet_text);

        this.timeLineTable.putIfAbsent(user_id, new LinkedList<ExtendedTweet>());
        LinkedList<ExtendedTweet> tweets = this.timeLineTable.get(user_id);
        tweets.addFirst(new ExtendedTweet(this.currentTime, tweet));
        if (tweets.size() > 10)
            tweets.removeLast();
        
        this.currentTime ++;

        return tweet;
    }

    /*
     * @param user_id: An integer
     * @return: a list of 10 new feeds recently and sort by timeline
     */
    public List<Tweet> getNewsFeed(int user_id) {
        PriorityQueue<Pair> latest = new PriorityQueue<Pair>((a, b) -> Integer.compare(b.current.timeStamp, a.current.timeStamp));
        
        if (this.timeLineTable.containsKey(user_id)) {
            Iterator<ExtendedTweet> iterator = this.timeLineTable.get(user_id).iterator();
            latest.add(new Pair(iterator.next(), iterator));
        }

        if (this.followsTable.containsKey(user_id)) {
            for (int followedId : this.followsTable.get(user_id)) {
                if (this.timeLineTable.containsKey(followedId)) {
                    Iterator<ExtendedTweet> iterator = this.timeLineTable.get(followedId).iterator();
                    latest.add(new Pair(iterator.next(), iterator));
                }
            }
        }
        
        ArrayList<Tweet> newsFeed = new ArrayList<Tweet>(10);
        while (!latest.isEmpty() && newsFeed.size() < 10) {
            Pair pair = latest.poll();
            newsFeed.add(pair.current.tweet);
            if (pair.iterator.hasNext()) {
                latest.add(new Pair(pair.iterator.next(), pair.iterator));
            }
        }
        return newsFeed;
    }

    /*
     * @param user_id: An integer
     * @return: a list of 10 new posts recently and sort by timeline
     */
    public List<Tweet> getTimeline(int user_id) {
        if (!this.timeLineTable.containsKey(user_id))
            return new ArrayList<Tweet>();
        
        ArrayList<Tweet> timeline = new ArrayList<>(this.timeLineTable.get(user_id).size());
        for (ExtendedTweet extendedTweet : this.timeLineTable.get(user_id)) 
            timeline.add(extendedTweet.tweet);
        return timeline;
    }

    /*
     * @param from_user_id: An integer
     * @param to_user_id: An integer
     * @return: nothing
     */
    public void follow(int from_user_id, int to_user_id) {
        this.followsTable.putIfAbsent(from_user_id, new HashSet<Integer>());
        this.followsTable.get(from_user_id).add(to_user_id);
    }

    /*
     * @param from_user_id: An integer
     * @param to_user_id: An integer
     * @return: nothing
     */
    public void unfollow(int from_user_id, int to_user_id) {
        if (this.followsTable.containsKey(from_user_id))
            this.followsTable.get(from_user_id).remove(to_user_id);
    }

    class Pair {
        public ExtendedTweet current;
        public Iterator<ExtendedTweet> iterator;
        public Pair(ExtendedTweet current, Iterator<ExtendedTweet> iterator) {
            this.current = current;
            this.iterator = iterator;
        }
    }

    class ExtendedTweet {
        public int timeStamp;
        public Tweet tweet;
        public ExtendedTweet(int timeStamp, Tweet tweet) {
            this.timeStamp = timeStamp;
            this.tweet = tweet;
        }
    }
}