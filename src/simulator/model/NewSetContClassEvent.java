package simulator.model;

import java.util.List;

import simulator.misc.Pair;

public class NewSetContClassEvent extends Event{

	protected List<Pair<String,Integer>> cs;
	
	public NewSetContClassEvent(int time, List<Pair<String, Integer>> cs) {
		super(time);
		if(cs == null) {
			throw new IllegalArgumentException("This cs is null");
		}else {
			this.cs = cs;
		}
	}
	@Override
	void execute(RoadMap map) {
		for(Pair<String,Integer> c : cs){ 
			if(map.map_vehicle.get(c.getFirst()) != null) {
				map.getVehicle(c.getFirst()).setContamination(c.getSecond());
			}else {
				throw new IllegalArgumentException("This vehicle not exist in the RoadMap");
			}
		}
		// TODO Auto-generated method stub
	}
	@Override
	public String toString() {
		String sC = "Change contamination class: [";
		for(Pair<String,Integer> c : cs){
			sC += "(" + c.getFirst() + ", " + c.getSecond() + "), ";
		}
		return sC + "]";
	}
}
