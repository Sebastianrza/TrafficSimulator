package simulator.model;


import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import simulator.misc.SortedArrayList;

public class TrafficSimulator implements Observable<TrafficSimObserver>{

	protected RoadMap roadmap;
	protected List<Event> list_event ;
	protected List<TrafficSimObserver> listObserver;
	private int simulation_time;
	
	public TrafficSimulator() {
		this.roadmap = new RoadMap();
		this.list_event = new SortedArrayList<Event>();
		this.simulation_time = 0;
		this.listObserver = new ArrayList<>();
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
		if(e.getTime() <=simulation_time) {
   		 for(TrafficSimObserver obs : listObserver) {
       		 obs.onError("Cannot add events for the past");
       	 }
   		 throw new IllegalArgumentException("Cannot add events for the past!");
   		 
   	 	}else {
	   	 	this.list_event.add(e);
			for (TrafficSimObserver o : listObserver) {
	            o.onEventAdded(roadmap, list_event, e, getSimulation_time());
		}
   	 	}
		
	}
	
	public void advance() {
		this.setSimulation_time(this.getSimulation_time() + 1);
		for (TrafficSimObserver o : listObserver) {
            o.onAdvanceStart(roadmap, list_event, getSimulation_time());
        }
		while(list_event.size() > 0 && list_event.get(0).getTime() == simulation_time) {
			list_event.remove(0).execute(roadmap);
		}
		
		for(Junction jun : this.roadmap.getJunctions()) {
			jun.advance(this.simulation_time);
		}
		for(Road ro : this.roadmap.getRoads()) {
			ro.advance(this.simulation_time);
		}
		for (TrafficSimObserver o : listObserver) {
            o.onAdvanceEnd(roadmap, list_event, getSimulation_time());
		}
	}
	
	public void reset() {
		this.roadmap.reset();
		this.list_event.clear();
		this.setSimulation_time(0);
		for(TrafficSimObserver o : listObserver) {
			o.onReset(roadmap, list_event, getSimulation_time());
		}
	}
	
	public JSONObject report() {
		
		JSONObject ts = new JSONObject();
		ts.put("time", this.getSimulation_time());
		ts.put("state", roadmap.report());
		return ts;	
	}

	@Override
	public void addObserver(TrafficSimObserver o) {
		// TODO Auto-generated method stub
		this.listObserver.add(o);
		o.onRegister(roadmap, list_event, getSimulation_time());
	}

	@Override
	public void removeObserver(TrafficSimObserver o) {
		// TODO Auto-generated method stub
		this.listObserver.remove(o);
	}
}
