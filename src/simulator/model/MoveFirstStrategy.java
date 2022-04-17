package simulator.model;

import java.util.ArrayList;
import java.util.List;

public class MoveFirstStrategy implements DequeuingStrategy{

	@Override
	public List<Vehicle> dequeue(List<Vehicle> q) {
		// TODO Auto-generated method stub
		List<Vehicle> firstVehicle = new ArrayList<Vehicle>();
		if(!q.isEmpty()) firstVehicle.add(q.get(0));
		
		return firstVehicle;
	}

	@Override
	public String toString() {
		return "move_first_dqs";
	}
}
