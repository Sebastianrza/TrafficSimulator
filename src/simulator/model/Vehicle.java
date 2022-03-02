package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONObject;

public class Vehicle extends SimulatedObject{
	
	protected List<Junction> itinerary;
	protected int maxSpeed;
	protected int actSpeed;
	protected VehicleStatus status;
	protected Road road;
	protected int location;
	protected int contamination;
	protected int total_contamination;
	protected int total_travelled_distance;
	protected int lastJunction;

	protected Vehicle(String id, int maxSpeed, int contamination, List<Junction> itinerary) {
		super(id);
		this.location = 0;
		this.actSpeed = 0;
		this.total_contamination = 0;
		this.lastJunction = 0;
		this.total_travelled_distance = 0;
		this.road = null;
		if(maxSpeed > 0) {
			this.maxSpeed = maxSpeed;
		}else {
			throw new IllegalArgumentException("MaxSpeed has to be positive");
		}
		if(contamination >= 0 && contamination <=10) {
			this.contamination = contamination;
		}else {
			throw new IllegalArgumentException("ContClass It has to be between 0 and 10 ");
		}
		if(itinerary.size() >= 2) {
			this.itinerary = Collections.unmodifiableList(new ArrayList<>(itinerary));
		}else {
			throw new IllegalArgumentException("The itinerary must be greather than 2");
		}
		if(status == null) status = VehicleStatus.PENDING;
		// TODO Auto-generated constructor stub
	}

	//Getters and Setters
	

	public List<Junction> getItinerary() {
		return itinerary;
	}

	public void setItinerary(List<Junction> itinerary) {
		this.itinerary = itinerary;
	}

	public int getTotal_contamination() {
		return total_contamination;
	}

	public void setTotal_contamination(int total_contamination) {
		this.total_contamination = total_contamination;
	}

	public Road getRoad() {
		return road;
	}

	public void setRoad(Road road) {
		this.road = road;
	}
	
	public int getCurrent_speed() {
		return actSpeed;
	}

	public void setCurrent_speed(int current_speed) {
		this.actSpeed = current_speed;
	}
	
	public int getLocation() {
		return location;
	}

	public void setLocation(int location) {
		this.location = location;
	}

	public int getContamination() {
		return contamination;
	}
	
	public int getMaximum_speed() {
		return maxSpeed;
	}


	public void setMaximum_speed(int maximum_speed) {
		this.maxSpeed = maximum_speed;
	}

	public VehicleStatus getStatus() {
		return status;
	}

	public void setStatus(VehicleStatus status) {
		this.status = status;
	}

	
	//Métodos
	void setSpeed(int s) {
		if(s < 0) throw new IllegalArgumentException("s has to be positive");
		
		if (this.status == VehicleStatus.TRAVELING) {
			this.actSpeed = Math.min(s,this.maxSpeed);
		}
	}
	
	public void setContamination(int c) {
		if(c >= 0 && c <=10) {
			this.contamination = c;
		}else {
			throw new IllegalArgumentException("ContClass It has to be between 0 and 10 ");
		}
	}
	@Override
	void advance(int time) {
		if(this.status == VehicleStatus.TRAVELING) {
			   int locationaux = this.location;
			   this.location = Math.min(this.location + this.actSpeed, road.getLength());
			   int contAct = contamination * ((this.location-locationaux));
			   road.addContamination(contAct);
			   total_contamination += contAct ;
			   this.total_travelled_distance += (this.location-locationaux);
			     
			   if (this.location >= road.getLength()) {
				    this.lastJunction++;
				    status = VehicleStatus.WAITING;
				    this.actSpeed = 0;
				    road.getDest().enter(this);
				   
			   }    
		}
		
	}

	void moveToNextRoad() {
		
	
		if(this.status != VehicleStatus.PENDING && this.status != VehicleStatus.WAITING) {
			throw new IllegalArgumentException("This Vehicle is in status incorrect");
		}
		if(this.road != null) {//Vehiculo sale de la carretera 
			this.road.exit(this);
		}
		if(this.lastJunction == this.itinerary.size()-1) {//Si ya termino el recorrido
			this.status = VehicleStatus.ARRIVED;
			this.road = null;
			this.location = 0;
			this.actSpeed = 0;
		}else {//Si todavia le queda algun itinerario.
			
			this.road = this.itinerary.get(lastJunction).roadTo(this.itinerary.get(lastJunction+1));
			this.location = 0;
			this.status = VehicleStatus.TRAVELING;
			this.actSpeed = 0;
			this.road.enter(this);
		}
	}

	@Override
	public JSONObject report() {
		JSONObject vh = new JSONObject();
		
		vh.put("id", this._id);
		vh.put("speed", this.actSpeed);
		vh.put("distance", this.total_travelled_distance);
		vh.put("co2",this.total_contamination);
		vh.put("class",this.contamination);
		vh.put("status", this.status.toString());
		if(this.status != VehicleStatus.PENDING && this.status != VehicleStatus.ARRIVED) {
			vh.put("road", this.road);
			vh.put("location", this.location);
		}
		// TODO Auto-generated method stub
		return vh;
	}

		
}
