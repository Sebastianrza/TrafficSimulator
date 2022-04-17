package simulator.model;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public abstract class Road extends SimulatedObject{

	protected Junction src;
	protected Junction dest;
	protected int length;
	protected int maximum_speed;
	protected int current_speed_limit;
	protected int contLimit;
	protected Weather weather;
	protected int total_contamination;
	protected List<Vehicle> vehicles;
	protected VehicleCompare compare;
	
	protected Road(String id, Junction src, Junction dest, int maximum_speed,int contLimit, int length, 
			Weather weather) {
		super(id);
		this.total_contamination = 0;
		this.vehicles = new LinkedList<Vehicle>();
		if(src != null) {
		this.src = src;
		
		}else {
			throw new IllegalArgumentException("This junction src is null");
		}
		if (dest !=null) {
		this.dest = dest;
		
		}else {
			throw new IllegalArgumentException("This junction dest is null");
		}
		if(length >0) {
		this.length = length;
		}else {
			throw new IllegalArgumentException("This length is less than zero");
		}
		if (maximum_speed >0 ) {
		this.maximum_speed = maximum_speed;
		this.current_speed_limit = this.maximum_speed;
		}else {
			throw new IllegalArgumentException("This maxSpeed is less then zero");
		}
		
		if(contLimit >=0) {
		this.contLimit = contLimit;
		}else {
			throw new IllegalArgumentException("This contLimit is less then zero");
		}
		if(weather != null) {
		this.weather = weather;
		}else {
			throw new IllegalArgumentException("This weather is null");
		}
		src.addOutGoingRoad(this);
		dest.addIncommingRoad(this);
		// TODO Auto-generated constructor stub
	}
	
	public Junction getSrc() {
		return src;
	}

	public Junction getDest() {
		return dest;
	}

	public int getLength() {
		return length;
	}

	public int getMaximum_speed() {
		return maximum_speed;
	}

	public int getCurrent_speed_limit() {
		return current_speed_limit;
	}

	public int getContLimit() {
		return contLimit;
	}

	public Weather getWeather() {
		return weather;
	}

	public int getTotal_contamination() {
		return total_contamination;
	}

	public List<Vehicle> getVehicles() {
		return Collections.unmodifiableList(vehicles);
	}

	public void setSrc(Junction src) {
		this.src = src;
	}

	public void setDest(Junction dest) {
		this.dest = dest;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public void setMaximum_speed(int maximum_speed) {
		this.maximum_speed = maximum_speed;
	}

	public void setCurrent_speed_limit(int current_speed_limit) {
		this.current_speed_limit = current_speed_limit;
	}

	public void setContLimit(int contLimit) {
		this.contLimit = contLimit;
	}
	
	
	public void setTotal_contamination(int total_contamination) {
		this.total_contamination = total_contamination;
	}

	public void setVehicles(List<Vehicle> vehicles) {
		this.vehicles = vehicles;
	}

	//Métodos
	
	void enter(Vehicle v) {
		if(v.getCurrent_speed() == 0 && v.getLocation() == 0) {
			this.vehicles.add(v);
		}else {

			throw new IllegalArgumentException("The location or the Speed is diferent of 0");
		}
	}
	
	void exit(Vehicle v) {
		this.vehicles.remove(v);
	}
	
	void setWeather(Weather w) {
		if(w != null) {
			this.weather = w;
		}else {
			throw new IllegalArgumentException("The w is null");
		}
	}
	
	void addContamination(int c) {
		if(c >= 0) {
			this.total_contamination = this.total_contamination+c;
		}else {
			throw new IllegalArgumentException("The contamination c is negative");
		}
		
	}
	
	abstract void reduceTotalContamination();
	abstract void updateSpeedLimit();
	abstract int calculateVehicleSpeed(Vehicle v);
	@Override
	void advance(int time) {
		// TODO Auto-generated method stub
		this.reduceTotalContamination();
		this.updateSpeedLimit();
		for(Vehicle v : vehicles) {
				v.setSpeed(calculateVehicleSpeed(v));
				v.advance(time);
		}
		compare = new VehicleCompare();
		vehicles.sort(compare);
		
	}

	@Override
	public JSONObject report() {
		// TODO Auto-generated method stub
		JSONObject ro = new JSONObject();
		JSONArray vh = new JSONArray();
		for(int i = 0; i <= vehicles.size()-1; i++){
			vh.put(vehicles.get(i)._id);
		}
		ro.put("id", this._id);
		ro.put("speedlimit", current_speed_limit);
		ro.put("co2", this.total_contamination);
		ro.put("weather",this.weather.toString());
		ro.put("vehicles", vh);
		return ro;
	}

	

}
