package simulator.model;

import java.util.LinkedList;
import java.util.List;

public class MoveAllStrategy implements DequeuingStrategy {

	@Override
	public List<Vehicle> dequeue(List<Vehicle> q) {
		List<Vehicle> Copydequeue = new LinkedList<>(q);
		
		return Copydequeue;
		// TODO Auto-generated method stub
	}
	
	@Override
	public String toString() {
		return "move_all_dqs";
	}

}
