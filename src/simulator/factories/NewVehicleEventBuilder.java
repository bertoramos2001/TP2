package simulator.factories;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import simulator.model.Event;
import simulator.model.NewVehicleEvent;

public class NewVehicleEventBuilder extends Builder<Event> {
	
	private static final String CLASS_NAME = "new_vehicle";
	
	public NewVehicleEventBuilder() {
		super(CLASS_NAME);
	}

	@Override
	protected Event createTheInstance(JSONObject data) {
		int time = data.getInt("time");
		String id = data.getString("id");
		int maxspeed = data.getInt("maxspeed");
		int contClass = data.getInt("class");
		JSONArray arr = data.getJSONArray("itinerary");
		
		List<String> list = new ArrayList<String>();
		
		for (int i = 0; i < arr.length(); i++) {
			list.add(arr.get(i).toString());
		}
		
		Event ev = new NewVehicleEvent(time, id, maxspeed, contClass, list);
		
		return ev;
	}

}
