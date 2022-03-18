package simulator.model;

import java.util.List;

public class MostCrowdedStrategy implements LightSwitchingStrategy {

	protected int timeSlot;
	
	public MostCrowdedStrategy(int timeSlot) {
		// TODO Auto-generated constructor stub
		this.timeSlot = timeSlot;
	}
	@Override
	public int chooseNextGreen(List<Road> roads, List<List<Vehicle>> qs, int currGreen, int lastSwitchingTime,
			int currTime) {
		if(roads.isEmpty()) {
			
			return -1;
			
		}else if(currGreen == -1) {
			
			int queue = 0;
			int max = 0;
			
			for(int i = 0; i < qs.size(); i++) {
				if(queue < qs.get(i).size()) {
					queue = qs.get(i).size();
					max = i;
				}
			}
			return max;
			
		}else if((currTime-lastSwitchingTime) < this.timeSlot) {
			
			return currGreen;
			
		}
		else {
			int queue = 0;
			int max = 0;
			int buscar = (currGreen + 1)%qs.size();
			
			for(int i = 0; i < qs.size(); i++) {
				if(queue < qs.get(buscar%qs.size()).size()) {
					queue = qs.get(buscar%qs.size()).size();
					max = buscar;
				}
				buscar++;
				
			}
			return max;
	}
}
		@Override
		public String toString(){
			return "Most_Crowded_lss";
		}

}
