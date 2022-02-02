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
		}
		if(roads.size() == 0) {
			return -1;
		}
		if(currGreen == -1) {
			return Cola(0,roads, qs);
		}
		if((currTime-lastSwitchingTime) < this.timeSlot) {
			return currGreen;
		}
		else {
			return Cola((currGreen +1 )%(roads.size()), roads, qs);
	}
}
		public int Cola(int comenzar, List<Road> roads, List<List<Vehicle>> qs){
			int maximo = 0;
			int maximoI = 0;
			for(int i = comenzar; i!=comenzar; i = (i+1)%roads.size()) {
				int temp = qs.get(i).size();
				if(temp > maximo) {
					maximo = temp;
					maximoI = i;
				}
			}
			return maximoI-1;
		}
		@Override
		public String toString(){
			return "Most_Crowded_lss";
		}

}
