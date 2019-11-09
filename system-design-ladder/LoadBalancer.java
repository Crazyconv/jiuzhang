import java.util.*;

public class LoadBalancer {
    private ArrayList<Integer> serverIds = new ArrayList<>();
    private HashMap<Integer, Integer> indexMap = new HashMap<>();
    private Random random = new Random();

    public LoadBalancer() { }

    /*
     * @param server_id: add a new server to the cluster
     * @return: nothing
     */
    public void add(int server_id) {
        if (this.indexMap.containsKey(server_id))
            return;
        this.serverIds.add(server_id);
        this.indexMap.put(server_id, this.serverIds.size()-1);
    }

    /*
     * @param server_id: server_id remove a bad server from the cluster
     * @return: nothing
     */
    public void remove(int server_id) {
        if (!this.indexMap.containsKey(server_id))
            return;
        if (this.serverIds.size() == 1) {
            this.indexMap.remove(server_id);
            this.serverIds.remove(0);
            return;
        } 
        int serverIndex = this.indexMap.get(server_id);
        int lastIndex = this.serverIds.size()-1;
        this.serverIds.set(serverIndex, this.serverIds.get(lastIndex));
        this.indexMap.put(this.serverIds.get(lastIndex), serverIndex);
        this.serverIds.remove(lastIndex);
        this.indexMap.remove(server_id);
    }

    /*
     * @return: pick a server in the cluster randomly with equal probability
     */
    public int pick() {
        return this.serverIds.get(this.random.nextInt(this.serverIds.size()));
    }
}