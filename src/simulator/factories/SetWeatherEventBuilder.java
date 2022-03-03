package simulator.factories;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import simulator.model.Event;
import simulator.model.SetWeatherEvent;
import simulator.model.Weather;
import simulator.misc.Pair;

public class SetWeatherEventBuilder extends Builder<Event> {
	
	private static final String CLASS_NAME = "set_weather";

	public SetWeatherEventBuilder() {
		super(CLASS_NAME);
	}

	@Override
	protected Event createTheInstance(JSONObject data) {
		int time = data.getInt("time");
		JSONArray arr = data.getJSONArray("info");
		List<Pair<String, Weather>> list = new ArrayList<Pair<String, Weather>>();
		
		for (int i = 0; i < arr.length(); i++) {
			String id = ((JSONObject)arr.get(i)).getString("road");
			String weatherString = ((JSONObject)arr.getJSONObject(i)).getString("weather");
			Weather weather = Weather.valueOf(weatherString.toUpperCase());
			
			Pair<String, Weather> pair = new Pair<String, Weather>(id, weather);
			list.add(pair);
		}
		
		Event ev = new SetWeatherEvent(time, list);
		
		return ev;
	}

}
