package simulator.factories;

import org.json.JSONObject;

import simulator.model.DequeuingStrategy;
import simulator.model.Event;
import simulator.model.LightSwitchingStrategy;
import simulator.model.NewJunctionEvent;

public class NewJunctionEventBuilder extends Builder<Event>{

	protected Factory<LightSwitchingStrategy> lssFactory;
	protected Factory<DequeuingStrategy> dqsFactory;
	public NewJunctionEventBuilder(Factory<LightSwitchingStrategy> lssFactory, Factory<DequeuingStrategy> dqsFactory) {
		super("new_junction");
		this.lssFactory = lssFactory;
		this.dqsFactory = dqsFactory;
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Event createTheInstance(JSONObject data) {
		// TODO Auto-generated method stub
		if(!data.isEmpty()) {
			NewJunctionEvent nje;
			
			int time = data.getInt("time");
			String id = data.getString("id");
			int xCoor = data.getJSONArray("coor").getInt(0);
			int yCoor = data.getJSONArray("coor").getInt(1);
			LightSwitchingStrategy lss= this.lssFactory.createInstance(data.getJSONObject("ls_strategy"));
			DequeuingStrategy dqs = this.dqsFactory.createInstance(data.getJSONObject("dq_strategy"));
			nje =  new NewJunctionEvent(time, id, lss, dqs, xCoor, yCoor);
			return nje;
		}else {
			return null;
		}
	}

}
