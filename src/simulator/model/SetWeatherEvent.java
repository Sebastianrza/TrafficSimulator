package simulator.model;

import java.util.List;

import simulator.misc.Pair;

public class SetWeatherEvent extends Event {

	List<Pair<String,Weather>> ws;
	public SetWeatherEvent(int time, List<Pair<String,Weather>> ws) {
		super(time);
		if(ws !=null) {
			this.ws = ws;
		}else {
			throw new IllegalArgumentException("The list pair ws is null");
		}
		
		// TODO Auto-generated constructor stub
	}
	@Override
	void execute(RoadMap map) {
		
		for(Pair<String,Weather> w : ws) {
			if(map.map_road.get(w.getFirst()) != null) {
				map.getRoad(w.getFirst()).setWeather(w.getSecond());
			}else {
				throw new IllegalArgumentException("The road not exist in the MapRoad");
			}
		}
		// TODO Auto-generated method stub
		
	}
	@Override
	public String toString() {
		String wS = "New Weather: [" ;
		for(Pair<String, Weather> p : ws){
			wS += "(" + p.getFirst() + ", " + p.getSecond()+"), ";
		}
		return wS+"]";
	}

}
