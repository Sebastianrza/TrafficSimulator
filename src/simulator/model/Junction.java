package simulator.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

public class Junction extends SimulatedObject{
	
	protected List<Road> incoming_roads; //Este cruce es el destieno de estas carreteras
	protected Map<Junction,Road> outgoing_roads ; //this --Road-->Jun
	protected List<List<Vehicle>> queue; 
	protected Map<Road, List<Vehicle>> road_queue;
 	protected int green_traffic_light ;
	protected int traffic_light_change;
	protected LightSwitchingStrategy lsStrategy;
	protected DequeuingStrategy dqStrategy;
	protected int xCoor;
	protected int yCoor;	
	
	protected Junction(String id, LightSwitchingStrategy lsStrategy, DequeuingStrategy dqStrategy
			, int xCoor, int yCoor) {
		super(id);
		if(lsStrategy != null) {
			this.lsStrategy = lsStrategy;
			}else {
				throw new IllegalArgumentException("The lsStrategy is Null");
			}
			if(dqStrategy != null) {
			this.dqStrategy = dqStrategy;
			}else {
				throw new IllegalArgumentException("The dqStrategy is Null");
			}
			if(xCoor >= 0) {
			this.xCoor = xCoor;
			}else {
				throw new IllegalArgumentException("The xCoor is negative");
			}
			if(yCoor >=0) {
			this.yCoor = yCoor;
			}else {
				throw new IllegalArgumentException("The yCoor is negative");
			}
			this.green_traffic_light = -1;
			this.traffic_light_change = 0;
			this.incoming_roads =  new LinkedList<Road>();
			this.road_queue = new HashMap<Road, List<Vehicle>>();
			this.outgoing_roads = new HashMap<Junction,Road>();
			queue = new LinkedList<List<Vehicle>>();
		// TODO Auto-generated constructor stub
	}
	
	public List<Road> getIncoming_roads() {
		return incoming_roads;
	}

	public Map<Junction, Road> getOutgoing_roads() {
		return outgoing_roads;
	}

	public List<List<Vehicle>> getQueue() {
		return queue;
	}
	public List<Vehicle> getQueue(Road r){
		for(int i=0; i < this.incoming_roads.size(); i++){
			if(this.incoming_roads.get(i).equals(r)) {return this.queue.get(i);}
		}
		return null;
	}
	public Map<Road, List<Vehicle>> getRoad_queue() {
		return road_queue;
	}

	public int getGreen_traffic_light() {
		return green_traffic_light;
	}

	public int getTraffic_light_change() {
		return traffic_light_change;
	}

	public LightSwitchingStrategy getLsStrategy() {
		return lsStrategy;
	}

	public DequeuingStrategy getDqStrategy() {
		return dqStrategy;
	}

	public int getxCoor() {
		return xCoor;
	}

	public int getyCoor() {
		return yCoor;
	}

	public void setIncoming_roads(List<Road> incoming_roads) {
		this.incoming_roads = incoming_roads;
	}

	public void setOutgoing_roads(Map<Junction, Road> outgoing_roads) {
		this.outgoing_roads = outgoing_roads;
	}

	public void setQueue(List<List<Vehicle>> queue) {
		this.queue = queue;
	}

	public void setRoad_queue(Map<Road, List<Vehicle>> road_queue) {
		this.road_queue = road_queue;
	}

	public void setGreen_traffic_light(int green_traffic_light) {
		this.green_traffic_light = green_traffic_light;
	}

	public void setTraffic_light_change(int traffic_light_change) {
		this.traffic_light_change = traffic_light_change;
	}

	public void setLsStrategy(LightSwitchingStrategy lsStrategy) {
		this.lsStrategy = lsStrategy;
	}

	public void setDqStrategy(DequeuingStrategy dqStrategy) {
		this.dqStrategy = dqStrategy;
	}

	public void setxCoor(int xCoor) {
		this.xCoor = xCoor;
	}

	public void setyCoor(int yCoor) {
		this.yCoor = yCoor;
	}

	void addIncommingRoad(Road r) {
		if(this.equals(r.getDest())) {
			incoming_roads.add(r);
			List<Vehicle> aux = new LinkedList<Vehicle>();
			queue.add(aux);
			road_queue.put(r, aux);
		}else {
			throw new IllegalArgumentException("This junction not exist");
		}
		
	}
	
	void addOutGoingRoad(Road r) {
		
		if(!this.equals(r.getSrc())) {
			throw new IllegalArgumentException("Don't exist the junction");
		}
		
		if(outgoing_roads.get(r.getDest()) != null) {
			throw new IllegalArgumentException("One More junction");
		}
		
		outgoing_roads.put(r.getDest(), r);
	}
	
	Road roadTo(Junction j) {
		return outgoing_roads.get(j);
		
	}
	void enter(Vehicle v) {
		Road r = v.getRoad();
		List<Vehicle> q = road_queue.get(r);
		q.add(v);
	}
	@Override
	void advance(int time) {
		// TODO Auto-generated method stub
		if(this.green_traffic_light != -1) {
			if(queue.get(green_traffic_light).size() > 0) {
				List<Vehicle> vtemp = dqStrategy.dequeue(queue.get(green_traffic_light));
				for(Vehicle v: vtemp) {
					v.moveToNextRoad();
					this.queue.get(this.green_traffic_light).remove(v);
				}
			}
		}
		
		int light = this.lsStrategy.chooseNextGreen(incoming_roads, this.queue, this.green_traffic_light, this.traffic_light_change, time);
		
		if(light != green_traffic_light) {
			this.green_traffic_light = light;
			this.traffic_light_change = time;
		}
	}

	@Override
	public JSONObject report() {
		JSONObject junc = new JSONObject();
		JSONArray Queus = new JSONArray();
		JSONObject road = new JSONObject();
		JSONArray vacio = new JSONArray();
		JSONArray prueba = new JSONArray();
		  
		if(this.incoming_roads.size() <= 0){
		  }else{
		   for(int i = 0; i<=this.incoming_roads.size()-1; i++) {
			   road.put("road",this.incoming_roads.get(i)._id);
			    if(this.queue.size() <= 0){
			     road.put("vehicles", "");
			    }else{
			    	if(queue.get(i).size() == 0){
			    		road.put("vehicles",vacio);
			    	}else{
			    		for(int j = 0; j <= queue.get(i).size()-1; j++) {
			    			prueba.put(queue.get(i).get(j)._id);
			    			road.put("vehicles", prueba);
			    		}
			    	}
			    }
		    Queus.put(road);
		    road = new JSONObject();
		   }
		  
		  }
		  junc.put("id", this._id);
		  if (this.green_traffic_light == -1) {
		   junc.put("green", "none");
		  }else {
		    junc.put("green", this.incoming_roads.get(this.green_traffic_light)._id);
		  }
		  junc.put("queues", Queus);
		  // TODO Auto-generated method stub
		  return junc;
	}
 
}
