import java.util.HashMap;
import java.util.Map;

class Trip {
    public int id; // trip's id, primary key
    public int driver_id, rider_id; // foreign key
    public double lat, lng; // pick up location
    public Trip(int rider_id, double lat, double lng) {}
}

class Helper {
    public static double get_distance(double lat1, double lng1, double lat2, double lng2) {
        return 0;
    }
};

public class MiniUber {
    class Driver {
        public int driverId;
        public double lat;
        public double lng;
        public Trip trip;
        public Driver(int driverId, double lat, double lng) {
            this.driverId = driverId;
            this.lat = lat;
            this.lng = lng;
        }
    }

    private HashMap<Integer, Driver> availableDrivers = new HashMap<>();
    private HashMap<Integer, Driver> unavailableDrivers = new HashMap<>();

    public MiniUber() {
        // initialize your data structure here.
    }

    // @param driver_id an integer
    // @param lat, lng driver's location
    // return matched trip information if there have matched rider or null
    public Trip report(int driver_id, double lat, double lng) {
        Driver driver = this.availableDrivers.getOrDefault(driver_id, null);
        if (driver == null)
            driver = this.unavailableDrivers.getOrDefault(driver_id, null);
        if (driver != null) {
            driver.lat = lat;
            driver.lng = lng;
            return driver.trip;
        }

        this.availableDrivers.put(driver_id, new Driver(driver_id, lat, lng));
        return null;
    }

    // @param rider_id an integer
    // @param lat, lng rider's location
    // return a trip
    public Trip request(int rider_id, double lat, double lng) {
        Driver nearestDriver = null;
        double minDistance = 0;
        for (Map.Entry<Integer, Driver> entry : this.availableDrivers.entrySet()) {
            double distance = Helper.get_distance(lat, lng, entry.getValue().lat, entry.getValue().lng);
            if (nearestDriver == null || distance < minDistance) {
                nearestDriver = entry.getValue();
                minDistance = distance;
            }
        }

        Trip trip = new Trip(rider_id, lat, lng);
        trip.driver_id = nearestDriver.driverId;
        nearestDriver.trip = trip;
        this.availableDrivers.remove(nearestDriver.driverId);
        this.unavailableDrivers.put(nearestDriver.driverId, nearestDriver);

        return trip;
    }
}