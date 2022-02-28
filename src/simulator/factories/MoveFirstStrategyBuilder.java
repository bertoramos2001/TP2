package simulator.factories;

import org.json.JSONObject;

import simulator.model.DequeuingStrategy;
import simulator.model.MoveFirstStrategy;

public class MoveFirstStrategyBuilder extends Builder<DequeuingStrategy> {
	
	private static final String CLASS_NAME = "move_first_dqs";

	public MoveFirstStrategyBuilder() {
		super(CLASS_NAME);
	}

	@Override
	protected DequeuingStrategy createTheInstance(JSONObject data) {
		DequeuingStrategy dqs = new MoveFirstStrategy();
		
		return dqs;
	}

}
//TODO: ademas, falta crear las factory de dqs y lss (ver pg 21 y 22)
