package simulator.model;

import java.util.Comparator;

public class VehicleCompare implements Comparator<Vehicle> {

	@Override
	public int compare(Vehicle o1, Vehicle o2) {
		// TODO Auto-generated method stub
		if(o1.getLocation() < o2.getLocation()) {
			
			return 1;
			
		}else if(o1.getLocation() > o2.getLocation()) {
			
			return -1;
					
		}
		return 0;
		
	}

}
