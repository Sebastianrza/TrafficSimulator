package simulator.model;

import java.util.List;

public class RoundRobinStrategy implements LightSwitchingStrategy {

	protected int timeSlot;
	
	public RoundRobinStrategy(int timeSlot) {
		this.timeSlot = timeSlot;
	}
	@Override
	public int chooseNextGreen(List<Road> roads, List<List<Vehicle>> qs, int currGreen, int lastSwitchingTime,
			int currTime) {
		if(qs.isEmpty()) return -1;
		if(currGreen == -1) return 0;
		else if((currTime-lastSwitchingTime) < timeSlot) return currGreen;
		else return ((currGreen+1)%roads.size());
	}
	@Override
	public String toString(){
		return "round_robin_lss";
	}

}
