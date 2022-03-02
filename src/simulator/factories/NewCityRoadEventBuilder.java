package simulator.factories;

import simulator.model.Event;
import simulator.model.NewCityRoadEvent;
import simulator.model.Weather;

public class NewCityRoadEventBuilder extends NewRoadEventBuilder {

	public NewCityRoadEventBuilder() {
		super("new_city_road");
	}

	@Override
	public Event createRoad(int time, String id, String src, String dest, int maxspeed, int co2limit, int length,
			Weather w) {
		// TODO Auto-generated method stub
		return new NewCityRoadEvent(time, id, src, dest, length, co2limit, maxspeed , w);
	}

}
