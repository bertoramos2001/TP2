package simulator.model;

public class NewInterCityRoadEvent extends NewRoadEvent {
	
	public NewInterCityRoadEvent(int time, String id, String srcJun, String destJunc, int length, int co2Limit, int maxSpeed, Weather weather) {
		super(time, id, srcJun, destJunc, length, co2Limit, maxSpeed, weather);
	}
	
	@Override
	void execute(RoadMap map) {
		Junction src = map.getJunction(srcJun);
		Junction dest = map.getJunction(destJunc);
		
		Road r = new InterCityRoad(id, src, dest, maxSpeed, co2Limit, length, weather);
		map.addRoad(r);
	}

}
