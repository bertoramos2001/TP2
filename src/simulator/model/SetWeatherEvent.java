package simulator.model;

import java.util.List;
import simulator.misc.Pair;

public class SetWeatherEvent extends Event {
	
	private List<Pair<String,Weather>> ws;
	
	public SetWeatherEvent(int time, List<Pair<String,Weather>> ws) {
		super(time);
		if (ws == null) {
			throw new IllegalArgumentException("ws cannot be null");
		}
		this.ws = ws;
		}

	@Override
	void execute(RoadMap map) {
		for(int i = 0; i < ws.size(); i++) {
			if (map.getRoads() == null) {
				throw new IllegalArgumentException("ws cannot be null");
			}
			
		}
			//TODO: falta terminar, me quede aquix2
		
		
	}

}