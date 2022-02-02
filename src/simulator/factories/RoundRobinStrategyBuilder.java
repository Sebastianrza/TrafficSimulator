package simulator.factories;

import org.json.JSONObject;

import simulator.model.LightSwitchingStrategy;
import simulator.model.RoundRobinStrategy;

public class RoundRobinStrategyBuilder extends Builder<LightSwitchingStrategy> {

	public RoundRobinStrategyBuilder() {
		super("round_robin_lss");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected LightSwitchingStrategy createTheInstance(JSONObject data) {
		
		
		if(!data.isEmpty()) {
		RoundRobinStrategy rrs;// TODO Auto-generated method stub
		rrs = new RoundRobinStrategy(data.getInt("timeslot"));
		return rrs;
		}else
		{	
			return null;
			}
		
	}
}
