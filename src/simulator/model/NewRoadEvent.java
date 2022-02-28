package simulator.model;

public abstract class NewRoadEvent extends Event {
	
	protected String id;
    protected String src;
    protected String dest;
    protected int maxSpeed;
    protected int length;
    protected Weather weather;
    protected int co2limit;

	
	NewRoadEvent(int time, String id, String src, String dest, int length,  int co2limit, int maxSpeed, Weather weather) {
		
		super(time);
		this.id = id;
		this.src = src;
		this.dest = dest;
		this.length = length;
		this.co2limit = co2limit;
		this.maxSpeed = maxSpeed;
		this.weather = weather;
		
		// TODO Auto-generated constructor stub
	}


	@Override
	void execute(RoadMap map) {
		
		if(src == null || dest ==null) {
			throw new IllegalArgumentException("Cruces Nulos");
		}else {
			Road r = createRoad(this.id, map.getJunction(src), map.getJunction(dest), this.maxSpeed, this.co2limit, this.length, this.weather);
			map.addRoad(r);
			map.getJunction(dest).addIncommingRoad(r);	
			map.getJunction(src).addOutGoingRoad(r);
		}
		
	}
	public abstract Road createRoad(String id, Junction src, Junction dest, int maxSpeed, int co2limit, int length ,
			Weather weather);
}