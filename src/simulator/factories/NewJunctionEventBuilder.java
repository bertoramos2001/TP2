package simulator.factories;


import org.json.JSONArray;
import org.json.JSONObject;
import simulator.model.DequeuingStrategy;
import simulator.model.Event;
import simulator.model.Junction;
import simulator.model.LightSwitchingStrategy;
import simulator.model.NewJunctionEvent;

public class NewJunctionEventBuilder extends Builder<Event> {
	
	private static final String CLASS_NAME = "new_junction";
	private Factory<LightSwitchingStrategy> lssFactory;
	private Factory<DequeuingStrategy> dqsFactory;

	public NewJunctionEventBuilder(Factory<LightSwitchingStrategy> lssFactory, Factory<DequeuingStrategy> dqsFactory) {
		super(CLASS_NAME);
		this.lssFactory = lssFactory;
		this.dqsFactory = dqsFactory;
	}

	@Override
	protected Event createTheInstance(JSONObject data) {
		int time = data.getInt("time");
		String id = data.getString("id");
		JSONArray arr = data.getJSONArray("coor");
		
		LightSwitchingStrategy lss = lssFactory.createInstance(data.getJSONObject("ls_strategy"));
		DequeuingStrategy dqs = dqsFactory.createInstance(data.getJSONObject("dq_strategy"));
		
		Event ev = new NewJunctionEvent(time, id, lss, dqs, arr.getInt(0), arr.getInt(1));
		
		return ev;
	}

}
