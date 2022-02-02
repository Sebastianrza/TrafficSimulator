package simulator.model;

public class NewJunctionEvent extends Event{

	protected String id;
	protected LightSwitchingStrategy lsStrategy;
	protected DequeuingStrategy dqStrategy;
	protected int xCoor;
	protected int yCoor;
	public NewJunctionEvent(int time, String id, LightSwitchingStrategy lsStrategy, DequeuingStrategy dqStrategy
			, int xCoor, int yCoor) {
		super(time);
		this.id = id;
		this.dqStrategy = dqStrategy;
		this.lsStrategy = lsStrategy;
		this.xCoor = xCoor;
		this.yCoor = yCoor;
		// TODO Auto-generated constructor stub
	}

	@Override
	void execute(RoadMap map) {
		Junction j = new Junction(this.id,this.lsStrategy, this.dqStrategy,
				this.xCoor, this.yCoor);
		map.addJunction(j);
	}
	
	@Override
	public String toString() {
		return "New Junction: '"+id+"', "+lsStrategy.toString()+"', "+dqStrategy.toString()+"', "+xCoor+"', "+yCoor+"'";
	}
}
