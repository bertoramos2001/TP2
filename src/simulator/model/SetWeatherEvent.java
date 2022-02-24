package simulator.model;

import java.util.List;
import simulator.misc.Pair;

public class SetWeatherEvent extends Event {
	
	private List<Pair<String,Weather>> ws;
	
	public SetWeatherEvent(int time, List<Pair<String,Weather>> ws) {
		super(time);
		
		if (ws == null) {
			throw new IllegalArgumentException("weather-road list cannot be null");
		}
		
		this.ws = ws;
	}

	@Override
	void execute(RoadMap map) {
		
		for (Pair<String, Weather> w : ws) {
			Road r = map.getRoad(w.getFirst());
			
			if (r == null) {
				throw new IllegalArgumentException("Road does not exist in map");
			}
			
			r.setWeather(w.getSecond());
		}
	}

}