package simulator.factories;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import simulator.model.Event;
import simulator.model.NewVehicleEvent;


public class NewVehicleEventBuilder extends Builder<Event> {

	public NewVehicleEventBuilder() {
		super("new_vehicle");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Event createTheInstance(JSONObject data) {
		// TODO Auto-generated method stub
		
			
			int time = data.getInt("time");
			String id = data.getString("id");
			int maxSpeed = data.getInt("maxspeed");
			int contClass = data.getInt("class");
			List<String> it = new ArrayList<>();
			
			for(int i = 0; i < data.getJSONArray("itinerary").length(); i++) {
				it.add(data.getJSONArray("itinerary").getString(i));
			}
			return  new NewVehicleEvent(time, id, maxSpeed, contClass, it);
			
		
	}

}
