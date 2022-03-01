package simulator.factories;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.NewSetContClassEvent;

public class SetContClassEventBuilder extends Builder<Event>{
	
	private static final String CLASS_NAME = "set_cont_class";
	
	public SetContClassEventBuilder(String type) {
		super(CLASS_NAME);
	}

	@Override
	protected Event createTheInstance(JSONObject data) {
		int time = data.getInt("time");
		JSONArray arr = data.getJSONArray("info");
		List<Pair<String, Integer>> list = new ArrayList<Pair<String, Integer>>();
		//TODO: esta parte me huele fatal
		for (int i = 0; i < arr.length(); i++) {
			String id = ((JSONObject)arr.get(i)).getString("vehicle");
			Integer contClass = ((JSONObject)arr.get(i)).getInt("class");
			
			Pair<String, Integer> pair = new Pair<String, Integer>(id, contClass);
			list.add(pair);
		}
		
		Event ev = new NewSetContClassEvent(time, list);
		
		return ev;
	}

}
