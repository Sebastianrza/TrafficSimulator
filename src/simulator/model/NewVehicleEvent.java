package simulator.model;

import java.util.ArrayList;
import java.util.List;

public class NewVehicleEvent extends Event{

	protected String id;
	protected int maxSpeed;
	protected int contClass;
	protected List<String> itinerary;
	protected int time;
	
	public NewVehicleEvent(int time, String id, int maxSpeed,
			int contClass, List<String> itinerary) {
		
		super(time);
		this.id = id;
		this.maxSpeed = maxSpeed;
		this.contClass = contClass;
		this.itinerary = itinerary;
		// TODO Auto-generated constructor stub
	}
	@Override
	void execute(RoadMap map) {
		ArrayList<Junction> listJunc = new ArrayList<Junction>(itinerary.size());
		
		for(String junc: itinerary) {
			listJunc.add(map.getJunction(junc));		
		}
		Vehicle v = new Vehicle(this.id, this.maxSpeed, this.contClass, listJunc);
		map.addVehicle(v);
		v.moveToNextRoad();
		// TODO Auto-generated method stub	
	}
	@Override
	public String toString() {
		
		return "New Vehicle '"+id+"', '"+maxSpeed+"', '"+contClass+"', '"+time+"'";
	}
}
