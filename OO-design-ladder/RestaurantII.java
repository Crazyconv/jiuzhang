import java.util.*;

class NoTableException extends Exception{

	public NoTableException(Party p)
	{
		super("No table available for party size: " + p.getSize());
	}
}

class Meal {
	private float price;
	
	public Meal(float price)
	{
		this.price = price;
	}
	
	public float getPrice()
	{
		return this.price;
	}
}

class Order {
	private List<Meal> meals;
	
	public Order()
	{
		meals = new ArrayList<Meal>();
	}
	
	public List<Meal> getMeals()
	{
		return meals;
	}
	
	public void mergeOrder(Order order)
	{
		if(order != null)
		{
			for(Meal meal : order.getMeals())
			{
				meals.add(meal);
			}
		}
	}
	
	public float getBill()
	{
		int bill = 0;
		for(Meal meal : meals)
		{
			bill += meal.getPrice();
		}
		return bill;
	}
}

class Party {
	private int size;
	
	public Party(int size)
	{
		this.size = size;
	}
	
	public int getSize()
	{
		return this.size;
	}
}

class Table implements Comparable<Table>{
	private int id;
	private int capacity;
	private boolean available;
	private Order order;
	List<Date> reservations;
    
    
	public Table(int id, int capacity)
	{
		this.id = id;
		this.capacity = capacity;
		available = true;
		order = null;
		reservations = new ArrayList<>();
	}
	
	public int getId()
	{
		return this.id;
	}
	
	public int getCapacity()
	{
		return this.capacity;
	}
	
	public List<Date> getReservation()
	{
		return reservations;
	}
	
	public boolean isAvailable()
	{
		return this.available;
	}
	
	public void markAvailable()
	{
		this.available = true;
	}
	
	public void markUnavailable()
	{
		this.available = false;
	}
	
	public Order getCurrentOrder()
	{
		return this.order;
	}
	
	public void setOrder(Order o)
	{
		if(order == null)
		{
			this.order = o;
		}
		else 
		{
			if(o != null)
			{
				this.order.mergeOrder(o);
			}
		}
	}

	@Override
	public int compareTo(Table compareTable) {
		// TODO Auto-generated method stub
		return this.capacity - compareTable.getCapacity();
	}
	
	private int findDatePosition(Date d)
	{
        int start = 0, end = this.reservations.size()-1;
        while (start <= end) {
            int mid = start + (end - start) / 2;
            if (this.reservations.get(mid).getTime() < d.getTime())
                start = mid + 1;
            else if (mid == start || this.reservations.get(mid-1).getTime() < d.getTime())
                return mid;
            else
                end = mid - 1;
        }
        return start;
	}
	
	public boolean noFollowReservation(Date d)
	{
        int pos = this.findDatePosition(d);
        
        return pos == this.reservations.size() || (this.reservations.get(pos).getTime() - d.getTime()) / Restaurant.HOUR >= Restaurant.MAX_DINEHOUR;
	}
	
	public boolean reserveForDate(Date d)
	{
        int pos = this.findDatePosition(d);
        if (pos >= 1 && (d.getTime() - this.reservations.get(pos-1).getTime()) / Restaurant.HOUR < Restaurant.MAX_DINEHOUR)
            return false;
        if (pos < this.reservations.size() && (this.reservations.get(pos).getTime() - d.getTime()) / Restaurant.HOUR < Restaurant.MAX_DINEHOUR)
            return false;
        this.reservations.add(pos, d);
        return true;
	}
	
	public void removeReservation(Date d)
	{
		this.reservations.remove(d);
	}
}

class Reservation {
	private Table table;
	private Date date;
	
	public Reservation(Table table, Date date)
	{
		this.table = table;
		this.date = date;
	}
	
	public Date getDate()
	{
		return date;
	}
	
	public Table getTable()
	{
		return table;
	}
}

class Restaurant {
	private List<Table> tables;
	private List<Meal> menu;
	public static final int MAX_DINEHOUR = 2;
	public static final long HOUR = 3600*1000;
	
	public Restaurant()
	{
		tables = new ArrayList<Table>();
		menu = new ArrayList<Meal>();
	}
	
	public void findTable(Party p) throws NoTableException
	{
        Date currentDate = new Date();
        for (Table table : this.tables) 
        {
            if (table.isAvailable() && table.getCapacity() >= p.getSize() && table.noFollowReservation(currentDate))
            {
                table.markUnavailable();
                return;
            }
        }
        throw new NoTableException(p);
	}
	
	public void takeOrder(Table t, Order o)
	{
		t.setOrder(o);
	}
	
	public float checkOut(Table t)
	{
        float bill = 0;
        Order order = t.getCurrentOrder();
        if (order != null)
            bill = order.getBill();
        
        t.markAvailable();
        t.setOrder(null);
        return bill;
	}
	
	public List<Meal> getMenu()
	{
		return menu;
	}
	
	public void addTable(Table t)
	{
        this.tables.add(t);
        Collections.sort(this.tables);
	}
	
	public Reservation findTableForReservation(Party p, Date date)
	{
        for (Table table : this.tables) 
        {
            if (table.getCapacity() >= p.getSize() && table.reserveForDate(date))
                return new Reservation(table, date);
        }
        return null;
	}
	
	public void cancelReservation(Reservation r)
	{
		Date date = r.getDate();
		r.getTable().removeReservation(date);
	}
	
	public void redeemReservation(Reservation r)
	{
		Date date = r.getDate();
		Table table = r.getTable();
		
		table.markUnavailable();
		table.removeReservation(date);
	}
	
	public String restaurantDescription()
	{
		String description = "";
		for(int i = 0; i < tables.size(); i++)
		{
			Table table = tables.get(i);
			description += ("Table: " + table.getId() + ", table size: " + table.getCapacity() + ", isAvailable: " + table.isAvailable() + ".");
			if(table.getCurrentOrder() == null)
				description += " No current order for this table"; 
			else
				description +=  " Order price: " + table.getCurrentOrder().getBill();
			
			description += ". Current reservation dates for this table are: ";
			
			for(Date date : table.getReservation())
			{
				description += date.toGMTString() + " ; ";
			}
			
			description += ".\n";
		}
		description += "*****************************************\n";
		return description;
	}
}