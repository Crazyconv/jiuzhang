import java.util.*;
import java.util.Map.Entry;

public class Hotel {
    public static final int DAY = 1*24*60*60*1000;
    
	private List<Room> rooms;
	public LRUCache cache;
	
	public Hotel(int cacheSize)
	{
        this.cache = new LRUCache(cacheSize);
        this.rooms = new ArrayList<>();
	}
    
    // $bug: correct implementation is in the comment below
	public Reservation makeReservation(ReservationRequest request)
	{
        SearchRequest searchRequest = new SearchRequest(request.getStartDate(), request.getEndDate());
        Map<RoomType, List<Room>> availableRooms = this.getAvailableRooms(searchRequest);
        Map<RoomType, Integer> roomsNeeded = request.getRoomsNeeded();

        Reservation reservation = new Reservation(request.getStartDate(), request.getEndDate());
        for (Entry<RoomType, Integer> entry : roomsNeeded.entrySet()) 
        {
            RoomType roomType = entry.getKey();
			int roomCount = entry.getValue();
			List<Room> rooms = availableRooms.get(roomType);
			
			if (roomCount > rooms.size())
			{
				cache.put(searchRequest, availableRooms);
				return null;
			}
			
			for (int i = 0; i < roomCount; i++)
			{	
				Room room = rooms.remove(0);
                reservation.getRooms().add(room);
                room.makeReservation(reservation.getStartDate(), reservation.getEndDate());
			}
        }

        this.cache.put(searchRequest, availableRooms);
        return reservation;
    }
    
    /*
    public Reservation makeReservation(ReservationRequest request)
	{
        SearchRequest searchRequest = new SearchRequest(request.getStartDate(), request.getEndDate());
        Map<RoomType, List<Room>> availableRooms = this.getAvailableRooms(searchRequest);
        Map<RoomType, Integer> roomsNeeded = request.getRoomsNeeded();

        Reservation reservation = new Reservation(request.getStartDate(), request.getEndDate());
        for (Entry<RoomType, Integer> entry : roomsNeeded.entrySet()) 
        {
            RoomType roomType = entry.getKey();
			int roomCount = entry.getValue();
			List<Room> rooms = availableRooms.get(roomType);
			
			if (roomCount > rooms.size())
			{
				cache.put(searchRequest, availableRooms);
				return null;
			}
			
			for (int i = 0; i < roomCount; i++)
			{	
				Room room = rooms.remove(0);
                reservation.getRooms().add(room);
                room.makeReservation(reservation.getStartDate(), reservation.getEndDate());
			}
        }

        this.cache.put(searchRequest, availableRooms);
        return reservation;
	}
     */
	
	public Map<RoomType, List<Room>> handleSearchResult(SearchRequest request)
	{
        if (this.cache.containsKey(request))
            return this.cache.get(request);
        
        Map<RoomType, List<Room>> result = this.getAvailableRooms(request);
        this.cache.put(request, result);
        return result;
	}
	
	public void cancelReservation(Reservation reservation)
	{
		for(Room room : reservation.getRooms())
		{
			room.cancelReservation(reservation);
		}
	}
	
	public List<Room> getRooms()
	{
		return this.rooms;
	}
	
	private Map<RoomType, List<Room>> getAvailableRooms(SearchRequest request)
	{
        HashMap<RoomType, List<Room>> result = new HashMap<>();
        result.put(RoomType.SINGLE, new LinkedList<Room>());
        result.put(RoomType.DOUBLE, new LinkedList<Room>());
        for (Room r : this.rooms) 
        {
            if (r.isValidRequest(request))
                result.get(r.getRoomType()).add(r);
        }
        return result;
	}
	
	public String printCache()
	{
		return "Printing Cache ...\n" + cache.toString() +
	 	       "*****************************\n";
	}
}

class Room {
    
    public static final int DAY = 1*24*60*60*1000;
    
	private int id;
	private RoomType roomType;
	private Set<Date> reservations;
	
	public Room(int id, RoomType roomType)
	{
        this.id = id;
        this.roomType = roomType;
        this.reservations = new HashSet<>();
	}
	
	public boolean isValidRequest(SearchRequest request)
	{
		Date date = new Date(request.getStartDate().getTime());
		for (; date.before(request.getEndDate()); date.setTime(date.getTime() + DAY))
		{
            Date tempDate = new Date(date.getTime());
            if (this.reservations.contains(tempDate))
                return false;
        }
        return true;
	}
	
	public void makeReservation(Date startDate, Date endDate)
	{
		Date date = new Date(startDate.getTime());
		for (; date.before(endDate); date.setTime(date.getTime() + DAY))
		{
			Date tempDate = new Date(date.getTime());
			this.reservations.add(tempDate);
		}
	}
	
	public RoomType getRoomType()
	{
		return this.roomType;
	}
	
	public int getId()
	{
		return this.id;
	}
	
    public void cancelReservation(Reservation reservation)
	{
		Date date = new Date(reservation.getStartDate().getTime());
		for (; date.before(reservation.getEndDate()); date.setTime(date.getTime() + DAY))
		{
			Date tempDate = new Date(date.getTime());
			reservations.remove(tempDate);
		}
	}
}

class LRUCache extends LinkedHashMap<SearchRequest, Map<RoomType, List<Room>>>{

	private static final long serialVersionUID = 1L;
	private int capacity;
	
	public LRUCache(int capacity)
	{
		super(capacity);
		this.capacity = capacity;
	}
	
	@Override
	protected boolean removeEldestEntry(Entry<SearchRequest, Map<RoomType, List<Room>>> eldest) {
		// TODO Auto-generated method stub
		return size() > this.capacity;
	}
	
	private String printAvailableRooms(Map<RoomType, List<Room>> rooms)
	{
		String res = "";
		for(Entry<RoomType, List<Room>> entry : rooms.entrySet())
		{
			res += "For room type: " + entry.getKey() + ", available rooms are: ";
			for(Room room : entry.getValue())
			{
				res += room.getId() + "; ";
			}
			res += ". ";
		}
		return res;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		
		String res = "";
		
		for(Entry<SearchRequest, Map<RoomType, List<Room>>> entry : super.entrySet())
		{
			res += ("Search Request -> " + entry.getKey().toString() + "\n");
			res += ("Value -> " + printAvailableRooms(entry.getValue()) + "\n");
			res += "\n";
		}

		return res;
	}
}

class ReservationRequest {
	private Date startDate;
	private Date endDate;
	private Map<RoomType, Integer> roomsNeeded;
	
	public ReservationRequest(Date startDate, Date endDate, Map<RoomType, Integer> roomsNeeded) {
		// TODO Auto-generated constructor stub
		this.startDate = startDate;
		this.endDate = endDate;
		this.roomsNeeded = roomsNeeded;
	}
	
	public Date getStartDate()
	{
		return startDate;
	}
	
	public Date getEndDate()
	{
		return endDate;
	}
	
	public Map<RoomType, Integer> getRoomsNeeded()
	{
		return roomsNeeded;
	}
}

class Reservation {
	private Date startDate;
	private Date endDate;
	private List<Room> rooms;
	
	public Reservation(Date startDate, Date endDate)
	{
		this.startDate = startDate;
		this.endDate = endDate;
		rooms = new ArrayList<>();
	}
	
	public Date getStartDate()
	{
		return startDate;
	}
	
	public Date getEndDate()
	{
		return endDate;
	}
	
	public List<Room> getRooms()
	{
		return rooms;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		
		
		String res = "Start date is: " + startDate.toLocaleString() + ", End date is: " + endDate.toLocaleString()
			+ ", rooms are: ";
		
		for(Room room : rooms)
		{
			res += room.getId() + "; ";
		}
		res += ". ";
		
		return res;
	}
}

enum RoomType {
	SINGLE,
	DOUBLE
}

class SearchRequest {
	private Date startDate;
	private Date endDate;
	
	public SearchRequest(Date startDate, Date endDate) {
		// TODO Auto-generated constructor stub
		this.startDate = startDate;
		this.endDate = endDate;
	}
	
	public Date getStartDate()
	{
		return startDate;
	}
	
	public Date getEndDate()
	{
		return endDate;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		
		String res = "Start date is: " + startDate.toLocaleString() + ", End date is: " + endDate.toLocaleString();
		
		return res;
	}
	
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if(obj == this) return true;
		if(!(obj instanceof SearchRequest)) return false;
		
		SearchRequest request = (SearchRequest) obj;
		
		return request.startDate.equals(this.startDate) && request.endDate.equals(this.endDate);
	}
	
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		int result = 17;
		result = 31 * result + startDate.hashCode();
		result = 31 * result + endDate.hashCode();
		return result;
	}
}