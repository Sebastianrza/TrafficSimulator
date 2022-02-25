package simulator.model;

import org.json.JSONObject;

public abstract class SimulatedObject {

	protected String _id;

	SimulatedObject(String id) {
		if(!id.isEmpty()) _id = id;
		else {
			throw new IllegalArgumentException("this id is null or is empty");
		}
	}

	public String getId() {
		return _id;
	}

	@Override
	public String toString() {
		return _id;
	}

	abstract void advance(int time) throws Exception;

	abstract public JSONObject report();
}
