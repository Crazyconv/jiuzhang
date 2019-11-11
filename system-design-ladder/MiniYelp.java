import java.util.*;

class Location {
    public double latitude, longitude;
    public static Location create(double lati, double longi) {
        // This will create a new location object
        return new Location();
    }
};

class Restaurant {
    public int id;
    public String name;
    public Location location;
    public static Restaurant create(String name, Location location) {
        // This will create a new restaurant object,
        // and auto fill id
        return new Restaurant();
    }
};

class Helper {
    public static double get_distance(Location location1, Location location2) {
        // return distance between location1 and location2.
        return 0;
    }
};
class GeoHash {
    public static String encode(Location location) {
        return "";
    }
    public static Location decode(String hashcode) {
        return new Location();
    }
};

public class MiniYelp {
    private HashMap<Integer, Restaurant> restaurants = new HashMap<>();

    public MiniYelp() {
        // initialize your data structure here.
    }

    // @param name a string
    // @param location a Location
    // @return an integer, restaurant's id
    public int addRestaurant(String name, Location location) {
        Restaurant restaurant = Restaurant.create(name, location);
        this.restaurants.put(restaurant.id, restaurant);
        return restaurant.id;
    }

    // @param restaurant_id an integer
    public void removeRestaurant(int restaurant_id) {
        this.restaurants.remove(restaurant_id);
    }

    // @param location a Location
    // @param k an integer, distance smaller than k miles
    // @return a list of restaurant's name and sort by 
    // distance from near to far.
    public List<String> neighbors(Location location, double k) {
        List<Item> result = new ArrayList<>();
        for (Map.Entry<Integer, Restaurant> entry : this.restaurants.entrySet()) {
            double distance = Helper.get_distance(entry.getValue().location, location);
            if (distance < k)
                result.add(new Item(entry.getValue().name, distance));
        }
        Collections.sort(result, (a, b) -> Double.compare(a.distance, b.distance));

        List<String> names = new ArrayList<>(result.size());
        for (Item item : result)
            names.add(item.name);
        return names;
    }

    class Item {
        public String name;
        public double distance;
        public Item(String name, double distance) {
            this.name = name;
            this.distance = distance;
        }
    }
};