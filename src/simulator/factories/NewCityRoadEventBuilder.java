package simulator.factories;

import org.json.JSONObject;
import simulator.model.Weather;
import simulator.model.Event;
import simulator.model.NewCityRoadEvent;

public class NewCityRoadEventBuilder extends Builder<Event> {
	
	private static final String CLASS_NAME = "new_city_road";
	
	public NewCityRoadEventBuilder() {
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
		
		Event ev = new NewCityRoadEvent(time, id, src, dest, length, co2limit, maxspeed, weather);
		
		return ev;
	}

}

//TODO: refactorizar builders de carretera en una clase padre
