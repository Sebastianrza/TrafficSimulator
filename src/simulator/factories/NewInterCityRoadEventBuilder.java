package simulator.factories;

import simulator.model.Event;
import simulator.model.NewInterCityRoadEvent;
import simulator.model.Weather;

public class NewInterCityRoadEventBuilder extends NewRoadEventBuilder{

	public NewInterCityRoadEventBuilder() {
		super("new_inter_city_road");
		// TODO Auto-generated constructor stub
	}

	@Override
	public Event createRoad(int time, String id, String src, String dest, int maxspeed, int co2limit, int length,
			Weather w) {
		// TODO Auto-generated method stub
		return new NewInterCityRoadEvent(time, id, src, dest, maxspeed, co2limit, length, w) ;
	}

	

}
