package simulator.factories;

import org.json.JSONObject;
import simulator.model.Event;
import simulator.model.NewInterCityRoadEvent;
import simulator.model.Weather;

public class NewInterCityRoadEventBuilder extends Builder<Event> {
	
	private static final String CLASS_NAME = "new_inter_city_road";
	
	public NewInterCityRoadEventBuilder() {
		super(CLASS_NAME);
	}

	@Override
	protected Event createTheInstance(JSONObject data) {
		int time = data.getInt("time");
		String id = data.getString("id");
		String src = data.getString("src");
		String dest = data.getString("dest");
		int length = data.getInt("length");
		int co2limit = data.getInt("co2limit");
		int maxspeed = data.getInt("maxspeed");
		String weatherString = data.getString("weather");
		Weather weather = Weather.valueOf(weatherString.toUpperCase());
		
		Event ev = new NewInterCityRoadEvent(time, id, src, dest, length, co2limit, maxspeed, weather);
		
		return ev;
	}
}
