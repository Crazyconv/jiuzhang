import java.util.*;

public class FriendshipService {
    private HashMap<Integer, TreeSet<Integer>> followersTable = new HashMap<>();
    private HashMap<Integer, TreeSet<Integer>> followingsTable = new HashMap<>();

    public FriendshipService() {}

    /*
     * @param user_id: An integer
     * @return: all followers and sort by user_id
     */
    public List<Integer> getFollowers(int user_id) {
        if (this.followersTable.containsKey(user_id))
            return new ArrayList<Integer>(this.followersTable.get(user_id));
        return new ArrayList<Integer>();
    }

    /*
     * @param user_id: An integer
     * @return: all followings and sort by user_id
     */
    public List<Integer> getFollowings(int user_id) {
        if (this.followingsTable.containsKey(user_id))
            return new ArrayList<Integer>(this.followingsTable.get(user_id));
        return new ArrayList<Integer>();
    }

    /*
     * @param from_user_id: An integer
     * @param to_user_id: An integer
     * @return: nothing
     */
    public void follow(int to_user_id, int from_user_id) {
        this.followersTable.putIfAbsent(to_user_id, new TreeSet<Integer>());
        this.followersTable.get(to_user_id).add(from_user_id);
        this.followingsTable.putIfAbsent(from_user_id, new TreeSet<Integer>());
        this.followingsTable.get(from_user_id).add(to_user_id);
    }

    /*
     * @param from_user_id: An integer
     * @param to_user_id: An integer
     * @return: nothing
     */
    public void unfollow(int to_user_id, int from_user_id) {
        if (this.followersTable.containsKey(to_user_id)) 
            this.followersTable.get(to_user_id).remove(from_user_id);
        if (this.followingsTable.containsKey(from_user_id))
            this.followingsTable.get(from_user_id).remove(to_user_id);
    }
}