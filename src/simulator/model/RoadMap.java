package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class RoadMap {

	protected List<Junction> junction;
	protected List<Road> road;
	protected List<Vehicle> vehicle;
	protected Map<String, Junction> map_junction;
	protected Map<String, Road> map_road;
	protected Map<String, Vehicle> map_vehicle;
	
	protected RoadMap() {
		
		this.junction = new ArrayList<>();
		this.road = new ArrayList<>();
		this.vehicle = new ArrayList<>();
		this.map_junction = new LinkedHashMap<>();
		this.map_road = new LinkedHashMap<>();
		this.map_vehicle =new LinkedHashMap<>();
	}
	
	void addJunction(Junction j) {
		if(map_junction.get(j._id) == null) {
			this.junction.add(j);
			this.map_junction.put(j._id, j);
		}
	}
	
	
	@SuppressWarnings("unlikely-arg-type")
	void addRoad(Road r) {
		if(road.contains(r) == true && map_road.containsValue(r.getDest()) == false &&
				map_road.containsValue(r.getSrc()) == false){
					
		}else {
			road.add(r);
			map_road.put(r._id, r);
		}
	}
	
	void addVehicle(Vehicle v) {
		Iterator<Junction> ju = v.getItinerary().iterator();
		Junction curJun = null;
		Junction previusJun = null;

		while (ju.hasNext()) { // comprobando el itinerario valido 
			curJun = ju.next();
			if(curJun == null)break;
			if(previusJun != null){	
				if(previusJun.roadTo(curJun) == null){
					throw new IllegalArgumentException("");
				}
			}
			previusJun = curJun;
		}
		if(this.map_vehicle.containsKey(v._id)) {
			throw new IllegalArgumentException();
		}else {
			vehicle.add(v);
			map_vehicle.put(v._id, v); 
		}
	}
	
	public Junction getJunction(String id) {
		return map_junction.get(id);
	}
	
	public Road getRoad(String id){
		Road r = map_road.get(id);
		return r;
		
	}
	public Vehicle getVehicle(String id) {
		Vehicle v = map_vehicle.get(id);
		return v;
		
	}
	public List<Junction>getJunctions() {
		
		return Collections.unmodifiableList(this.junction);
	}
	
	public List<Road>getRoads() {
		
		return Collections.unmodifiableList(this.road);
	}
	
	public List<Vehicle>getVehicles() {
		return Collections.unmodifiableList(this.vehicle);
	}
	
	void reset() {
		this.junction.clear();
		this.road.clear();
		this.vehicle.clear();
		this.map_junction.clear();
		this.map_road.clear();
		this.map_vehicle.clear();
	}
	
	public JSONObject report() {
		JSONObject rmp = new JSONObject();
		JSONArray jo = new JSONArray();

		for (int i = 0; i<=junction.size()-1;i++) {
			jo.put(junction.get(i).report());
		}
		
		JSONArray ro = new JSONArray();
		for (int i = 0; i<=road.size()-1;i++) {
			ro.put(road.get(i).report());
		}
		JSONArray vo = new JSONArray();
		for (int i = 0; i<=vehicle.size()-1;i++) {
			vo.put(vehicle.get(i).report());
		}
		
		rmp.put("junctions", jo);
		rmp.put("roads", ro);
		rmp.put("vehicles", vo);
		
		return rmp;
	}
}
