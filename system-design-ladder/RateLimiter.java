import java.util.ArrayList;
import java.util.HashMap;

public class RateLimiter {
    /*
     * @param timestamp: the current timestamp
     * @param event: the string to distinct different event
     * @param rate: the format is [integer]/[s/m/h/d]
     * @param increment: whether we should increase the counter
     * @return: true or false to indicate the event is limited or not
     */
    private HashMap<String, ArrayList<Integer>> requests = new HashMap<>();

    public boolean isRatelimited(int timestamp, String event, String rate, boolean increment) {
        String[] parts = rate.split("/");
        int limit = Integer.parseInt(parts[0]);
        if (limit == 0)
            return true;

        if (!this.requests.containsKey(event)) {
            if (increment) {
                this.requests.put(event, new ArrayList<Integer>());
                this.requests.get(event).add(timestamp);
            }
            return false;
        }
    
        int seconds = 1;
        if (parts[1].equals("m"))
            seconds = 60;
        else if (parts[1].equals("h"))
            seconds = 3600;
        else if (parts[1].equals("d"))
            seconds = 86400;

        ArrayList<Integer> records = this.requests.get(event);
        int index = this.getFirstGE(records, timestamp - seconds + 1);
        // System.out.printf("%d %d %d %d", records.size(), index, limit, timestamp - seconds + 1);
        if (records.size() - index >= limit)
            return true;
        
        if (increment)
            records.add(timestamp);
        return false;
    }

    private int getFirstGE(ArrayList<Integer> arr, int value) {
        int start = 0, end = arr.size() - 1;
        while (start <= end) {
            int mid = start + (end - start) / 2;
            if (arr.get(mid) < value) 
                start = mid + 1;
            else if (mid == start || arr.get(mid-1) < value)
                return mid;
            else
                end = mid - 1;
        }
        return start;
    }
}