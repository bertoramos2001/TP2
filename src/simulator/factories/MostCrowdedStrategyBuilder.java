package simulator.factories;

import org.json.JSONObject;

import simulator.model.LightSwitchingStrategy;
import simulator.model.MostCrowdedStrategy;

public class MostCrowdedStrategyBuilder extends Builder<LightSwitchingStrategy> {
	
	private static final String CLASS_NAME = "most_crowded_lss";
	
	public MostCrowdedStrategyBuilder() {
		super(CLASS_NAME);
	}

	@Override
	protected LightSwitchingStrategy createTheInstance(JSONObject data) {
		int timeSlot = data.has("timeslot") ? data.getInt("timeslot") : 1;
		
		LightSwitchingStrategy lss = new MostCrowdedStrategy(timeSlot);

		return lss;
	}

}
