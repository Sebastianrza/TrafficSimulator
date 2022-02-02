package simulator.factories;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.NewSetContClassEvent;

public class SetContClassEventBuilder extends Builder<Event>{

	public SetContClassEventBuilder() {
		super("set_cont_class");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Event createTheInstance(JSONObject data) {
	
		if(!data.isEmpty()) {
			NewSetContClassEvent nscce;
			String t;
			int t2;
			
		
		int time = data.getInt("time");
		
		List<Pair<String, Integer>> sw = new ArrayList<>();		
		
		for(int i= 0;  i < data.getJSONArray("info").length(); i++ ) {
			
			t = data.getJSONArray("info").getJSONObject(i).getString("vehicle");
			t2 = data.getJSONArray("info").getJSONObject(i).getInt("class");
			
			
			sw.add(new Pair<>(t,t2));
		}
		
			try {
				nscce = new NewSetContClassEvent(time, sw);
				return nscce;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				
			}
		}
		return null;
	}
		
}
