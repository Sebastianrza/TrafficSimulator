package simulator.model;


import java.util.LinkedList;
import java.util.List;

import org.json.JSONObject;

import simulator.misc.SortedArrayList;

public class TrafficSimulator {

	protected RoadMap roadmap;
	protected List<Event> list_event ;
	private int simulation_time;
	
	public TrafficSimulator() {
		this.roadmap = new RoadMap();
		this.list_event = new SortedArrayList<>();
		
	}
	
	public RoadMap getRoadmap() {
		return roadmap;
	}

	public List<Event> getList_event() {
		return list_event;
	}

	public int getSimulation_time() {
		return simulation_time;
	}

	public void setRoadmap(RoadMap roadmap) {
		this.roadmap = roadmap;
	}

	public void setList_event(List<Event> list_event) {
		this.list_event = list_event;
	}

	public void setSimulation_time(int simulation_time) {
		this.simulation_time = simulation_time;
	}

	public void addEvent (Event e) {
		this.list_event.add(e);
	}
	
	public void advance() {
		this.setSimulation_time(this.getSimulation_time() + 1);
		List<Event> removeEvents = new LinkedList<Event>();
		for(int i=0; i < this.list_event.size();i++){
			if(this.list_event.get(i)._time == this.getSimulation_time()){
				removeEvents.add(this.list_event.get(i));
			}
		}
		if(removeEvents.size()!= 0){
			for(Event e: removeEvents){
			    e.execute(roadmap);
			} 
			this.list_event.removeAll(removeEvents);
		}		  
		for(Junction jun : this.roadmap.getJunctions()) {
			jun.advance(this.simulation_time);
		}
		for(Road ro : this.roadmap.getRoads()) {
			ro.advance(this.simulation_time);
		}
	}
	
	public void reset() {
		this.roadmap.reset();
		this.list_event.clear();
		this.setSimulation_time(0);
	}
	
	public JSONObject report() {
		
		JSONObject ts = new JSONObject();
		ts.put("time", this.getSimulation_time());
		ts.put("state", roadmap.report());
		return ts;	
	}
}
