import java.util.*;

public class HeartBeat {
    private HashMap<String, Integer> lastHeartbeats = new HashMap<>();
    private int k = 0;

    public HeartBeat() {}

    /*
     * @param slaves_ip_list: a list of slaves'ip addresses
     * @param k: An integer
     * @return: nothing
     */
    public void initialize(List<String> slaves_ip_list, int k) {
        for (String ip : slaves_ip_list)
            this.lastHeartbeats.put(ip, 0);
        this.k = 2k;
    }

    /*
     * @param timestamp: current timestamp in seconds
     * @param slave_ip: the ip address of the slave server
     * @return: nothing
     */
    public void ping(int timestamp, String slave_ip) {
        if (this.lastHeartbeats.containsKey(slave_ip))
            this.lastHeartbeats.put(slave_ip, timestamp);
    }

    /*
     * @param timestamp: current timestamp in seconds
     * @return: a list of slaves'ip addresses that died
     */
    public List<String> getDiedSlaves(int timestamp) {
        List<String> result = new ArrayList<String>();
        for (Map.Entry<String, Integer> entry : this.lastHeartbeats.entrySet()) {
            if (entry.getValue() <= timestamp - k)
                result.add(entry.getKey());
        }
        return result;
    }
}