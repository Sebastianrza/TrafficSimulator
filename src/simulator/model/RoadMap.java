package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
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
		
		this.junction = new ArrayList<Junction>();
		this.road = new ArrayList<Road>();
		this.vehicle = new ArrayList<Vehicle>();
		this.map_junction = new LinkedHashMap<String, Junction>();
		this.map_road = new LinkedHashMap<String, Road>();
		this.map_vehicle =new LinkedHashMap<String, Vehicle>();
	}
	
	void addJunction(Junction j) {
		if(!map_junction.containsKey(j.getId())) {
			this.junction.add(j);
			this.map_junction.put(j._id, j);
		}else {
			throw new IllegalArgumentException("This junction already exists");
		}
	}
	
	
	
	void addRoad(Road r) {
		if(map_road.containsKey(r.getId()) && map_junction.containsValue(r.getDest()) &&
				map_junction.containsValue(r.getSrc())){
				
			throw new IllegalArgumentException("This road already exists");
		}else {
			road.add(r);
			map_road.put(r.getId(), r);
		}
	}
	
	void addVehicle(Vehicle v) {
		
		List<Junction> l = v.getItinerary();
		
		if(vehicle.contains(v)) {
			throw new IllegalArgumentException("This vehicle exist in the list");
		}
		
		for(int i = 0; i < l.size()-1; i++) {
			if(l.get(i).roadTo(l.get(i+1)) == null) {
				throw new IllegalArgumentException("This itinerary is null");
			}
		}
		
		vehicle.add(v);
		map_vehicle.put(v.getId(), v);
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
