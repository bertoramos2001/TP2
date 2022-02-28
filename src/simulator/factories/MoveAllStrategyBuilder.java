package simulator.factories;

import org.json.JSONObject;

import simulator.model.DequeuingStrategy;
import simulator.model.MoveAllStrategy;

public class MoveAllStrategyBuilder extends Builder<DequeuingStrategy> {
	
	private static final String CLASS_NAME = "move_all_dqs";
	
	public MoveAllStrategyBuilder() {
		super(CLASS_NAME);
	}

	@Override
	protected DequeuingStrategy createTheInstance(JSONObject data) {
		DequeuingStrategy dqs = new MoveAllStrategy();
		
		return dqs;
	}

}
