import java.util.*;

enum Direction {
	UP, DOWN
}

enum Status {
	UP, DOWN, IDLE
}

class Request {
	private int level;
	
	public Request(int l)
	{
		level = l;
	}
	
	public int getLevel()
	{
		return level;
	}
}

class ElevatorButton {
	private int level;
	private Elevator elevator;
	
	public ElevatorButton(int level, Elevator e)
	{
		this.level = level;
		this.elevator = e;
	}
	
	public void pressButton()
	{
		InternalRequest request = new InternalRequest(level);
		elevator.handleInternalRequest(request);
	}
}

class ExternalRequest extends Request{

	private Direction direction;
	
	public ExternalRequest(int l, Direction d) {
		super(l);
		// TODO Auto-generated constructor stub
		this.direction = d;
	}
	
	public Direction getDirection()
	{
		return direction;
	}
}

class InternalRequest extends Request{

	public InternalRequest(int l) {
		super(l);
		// TODO Auto-generated constructor stub
	}
}

public class Elevator {
	
	private List<ElevatorButton> buttons;
	
	private List<Boolean> upStops;
	private List<Boolean> downStops;
	
	private int currLevel;
	private Status status;
	
	public Elevator(int n)
	{
		buttons = new ArrayList<ElevatorButton>();
		upStops = new ArrayList<Boolean>();
		downStops = new ArrayList<Boolean>();
		currLevel = 0;
		status = Status.IDLE;
		
		for(int i = 0; i < n; i++)
		{
			upStops.add(false);
			downStops.add(false);
		}
	}
	
	public void insertButton(ElevatorButton eb)
	{
		buttons.add(eb);
	}
    
    // update status and upStops/downStops
	public void handleExternalRequest(ExternalRequest r)
	{
        if (r.getDirection() == Direction.UP) 
        {
            this.upStops.set(r.getLevel()-1, true);
            if (this.noRequests(this.downStops)) 
                this.status = Status.UP;
        } 
        else 
        {
            this.downStops.set(r.getLevel()-1, true);
            if (this.noRequests(this.upStops)) 
                this.status = Status.DOWN;
        }
	}
    
    // update status? and upStops/downStops
	public void handleInternalRequest(InternalRequest r)
	{
		if (this.status == Status.IDLE)
		{
			if (r.getLevel()-1 >= this.currLevel)
			{
				this.status = Status.UP;
				this.upStops.set(r.getLevel()-1, true);
			}
			else {
				this.status = Status.DOWN;
				this.downStops.set(r.getLevel()-1, true);
			}
			return;
		}

        if (this.status == Status.UP && r.getLevel()-1 >= this.currLevel) 
        {
            this.upStops.set(r.getLevel()-1, true);
            return;
        }
        
        if (this.status == Status.DOWN && r.getLevel()-1 <= this.currLevel)
        {
            this.downStops.set(r.getLevel()-1, true);
        }
	}
    
    // update currLevel and upStops/downStops
	public void openGate() throws Exception
	{
        if (this.status == Status.UP)
        {
            for (int i = 0; i < this.upStops.size(); i++) {
                int checkLevel = (this.currLevel + i) % this.upStops.size();
                if (this.upStops.get(checkLevel)) {
                    this.upStops.set(checkLevel, false);
                    this.currLevel = checkLevel;
                    return;
                }
            }
            return; 
		} 
		
		if (this.status == Status.DOWN)
		{
			for (int i = 0; i < this.downStops.size(); i++) {
				int checkLevel = (this.currLevel - i + this.downStops.size()) % this.downStops.size();
				if (this.downStops.get(checkLevel)) {
					this.downStops.set(checkLevel, false);
					this.currLevel = checkLevel;
					return;
				}
			}
		}
	}
    
    // update status
	public void closeGate()
	{
        if (this.status == Status.IDLE)
		{
			if (this.noRequests(this.downStops))
			{
				this.status = Status.UP;
			}
			else if (this.noRequests(this.upStops))
			{
				this.status = Status.DOWN;
            }
            return;
        }
        
        if (this.status == Status.UP)
        {
            if (this.noRequests(this.upStops))
                this.status = this.noRequests(this.downStops) ? Status.IDLE : Status.DOWN;
            return;
        } 

        if (this.noRequests((this.downStops)))
            this.status = this.noRequests((this.upStops)) ? Status.IDLE : Status.UP;
	}
	
	private boolean noRequests(List<Boolean> stops)
	{
		for(int i = 0; i < stops.size(); i++)
		{
			if(stops.get(i))
			{
				return false;
			}
		}
		return true;
	}
	
	public String elevatorStatusDescription()
	{	
		String description = "Currently elevator status is : " + status 
				+ ".\nCurrent level is at: " + (currLevel + 1)
				+ ".\nup stop list looks like: " + upStops
				+ ".\ndown stop list looks like:  " + downStops
				+ ".\n*****************************************\n";
		return description;
	}
}