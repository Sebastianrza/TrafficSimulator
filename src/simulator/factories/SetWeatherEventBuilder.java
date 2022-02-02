package simulator.factories;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.SetWeatherEvent;
import simulator.model.Weather;

public class SetWeatherEventBuilder extends Builder<Event> {

	public SetWeatherEventBuilder() {
		super("set_weather");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Event createTheInstance(JSONObject data) {
		
		if(!data.isEmpty()) {
			SetWeatherEvent nswe;
			String t;
			String t2;
			Weather tws;
			int time = data.getInt("time");
			List<Pair<String, Weather>> sw = new ArrayList<>();		
		
			for(int i = 0;  i < data.getJSONArray("info").length(); i++ ) {
			
				t = data.getJSONArray("info").getJSONObject(i).getString("weather");
				tws = Weather.valueOf(t.toUpperCase());
				t2 = data.getJSONArray("info").getJSONObject(i).getString("road");
				
				sw.add(new Pair<>(t2,tws));
			}
		
		try {
			nswe = new SetWeatherEvent(time, sw);
			return nswe;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			
		}	// TODO Auto-generated method stub

	}
		return null;

	}

}
