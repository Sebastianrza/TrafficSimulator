package simulator.factories;

import org.json.JSONObject;

import simulator.model.Event;

import simulator.model.Weather;

public abstract class NewRoadEventBuilder extends Builder<Event> {

	NewRoadEventBuilder(String type) {
		super(type);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Event createTheInstance(JSONObject data) {
		// TODO Auto-generated method stub
		int time = data.getInt("time");
		String id = data.getString("id");
		String src = data.getString("src");
		String dest = data.getString("dest");
		int length = data.getInt("length");
		int co2limit = data.getInt("co2limit");
		int maxspeed = data.getInt("maxspeed");
		String weather = data.getString("weather");
		Weather w = Weather.valueOf(weather.toUpperCase());
		NewRoadEventBuilder be;
		
		if(this._type == "new_city_road") {
			be = new NewCityRoadEventBuilder();
			return be.createRoad(time, id, src, dest, maxspeed, co2limit, length, w);
		}else {
			be = new NewInterCityRoadEventBuilder();
			return be.createRoad(time, id, src, dest, maxspeed, co2limit, length, w);
		}
		
	}

	public abstract Event createRoad(int time, String id, String src,
			String dest, int maxspeed, int co2limit, int length, Weather w);
}
