import java.util.*;

class Solution {

    private int n;
    private int k;
    private HashMap<Integer, List<Integer>> machineToShardIdMap = new HashMap<>();
    private TreeMap<Integer, Integer> shardIdToMachineIdMap = new TreeMap<>();
    private Random random = new Random();

    public Solution(int n, int k) {
        this.n = n;
        this.k = k;
    }
    
    /*
     * @param n: a positive integer
     * @param k: a positive integer
     * @return: a Solution object
     */
    public static Solution create(int n, int k) {
        return new Solution(n, k);
    }

    /*
     * @param machine_id: An integer
     * @return: a list of shard ids
     */
    public List<Integer> addMachine(int machine_id) {
        if (this.machineToShardIdMap.containsKey(machine_id))
            return this.machineToShardIdMap.get(machine_id);
        
        ArrayList<Integer> hashIds = new ArrayList<Integer>();
        while (hashIds.size() < this.k) {
            int hashCode = this.random.nextInt(this.n);
            if (this.shardIdToMachineIdMap.containsKey(hashCode))
                continue;
            
            hashIds.add((hashCode));
            this.shardIdToMachineIdMap.put(hashCode, machine_id);
        }

        this.machineToShardIdMap.put(machine_id, hashIds);
        return hashIds;
    }

    /*
     * @param hashcode: An integer
     * @return: A machine id
     */
    public int getMachineIdByHashCode(int hashcode) {
        Map.Entry<Integer, Integer> entry = this.shardIdToMachineIdMap.ceilingEntry(hashcode);
        if (entry != null)
            return entry.getValue();
        return this.shardIdToMachineIdMap.firstEntry().getValue();
    }
}