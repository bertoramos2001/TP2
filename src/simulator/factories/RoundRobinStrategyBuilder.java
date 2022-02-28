package simulator.factories;

import org.json.JSONObject;

import simulator.model.LightSwitchingStrategy;
import simulator.model.RoundRobinStrategy;

public class RoundRobinStrategyBuilder extends Builder<LightSwitchingStrategy> {
	
	private static final String CLASS_NAME = "round_robin_lss";

	public RoundRobinStrategyBuilder() {
		//TODO: se pasa asi el type directamente??
		super(CLASS_NAME);
	}

	@Override
	protected LightSwitchingStrategy createTheInstance(JSONObject data) {
		int timeSlot = data.has("timeslot") ? data.getInt("timeslot") : 1;
		
		LightSwitchingStrategy lss = new RoundRobinStrategy(timeSlot);
		
		return lss;
	}

}
