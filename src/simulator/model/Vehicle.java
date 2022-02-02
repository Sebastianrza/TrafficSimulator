package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONObject;

public class Vehicle extends SimulatedObject{
	
	protected List<Junction> itinerary;
	protected int maxSpeed;
	protected int actSpeed = 0;
	protected VehicleStatus status;
	protected Road road;
	protected int location = 0;
	protected int contamination = 0;
	protected int total_contamination = 0;
	protected int total_travelled_distance = 0;
	protected int lastJunction = 0;

	protected Vehicle(String id, int maxSpeed, int contamination, List<Junction> itinerary) {
		super(id);
		if(maxSpeed >= 0) {
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
			   this.lastJunction = this.location;
			   this.location = Math.min(this.location + this.actSpeed, road.getLength());
			   int contAct = contamination * ((this.location-this.lastJunction));
			   road.addContamination(contAct);
			   total_contamination += contAct ;
			   this.total_travelled_distance += (this.location-this.lastJunction);
			     
			   if (this.location >= road.getLength()) {
			    
				    this.actSpeed = 0;
				    status = VehicleStatus.WAITING;
				    road.getDest().enter(this);
			   }    
		}
		
	}

	void moveToNextRoad() {
		this.lastJunction += this.location; // siempre es ceros
		  if(this.status == VehicleStatus.PENDING){ // inicio del viaje
			   this.location = 0;
			   this.setSpeed(0);
			   road = itinerary.get(0).roadTo(itinerary.get(1));
			   road.enter(this);
			   this.status = VehicleStatus.TRAVELING;
		   
		  }else 
		   if(itinerary.indexOf(road.getDest())+1 == itinerary.size()){ // 
			    this.location = -1;
			    this.road.exit(this);
			    this.road = null;
			    this.status = VehicleStatus.ARRIVED;
			    this.actSpeed = 0;
		  }else{ 
			   this.location = 0;
			   int temp_indexJuntion = itinerary.indexOf(road.getDest())+1;
			   this.road.exit(this);
			   road = road.getDest().roadTo(itinerary.get(temp_indexJuntion));
			   this.road.enter(this);
			   this.status = VehicleStatus.TRAVELING;
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
		vh.put("status", this.status);
		vh.put("road", this.road);
		if(this.location !=-1)vh.put("location", this.location);
		// TODO Auto-generated method stub
		return vh;
	}

		
}
