package simulator.model;

public class NewInterCityRoadEvent extends Event {
	
	private String id;
	private Junction srcJun;
	private Junction destJunc;
	private int length;
	private int co2Limit;
	private int maxSpeed;
	private Weather weather;
	
	public NewInterCityRoadEvent(int time, String id, Junction srcJun, Junction destJunc, int length, int co2Limit, int maxSpeed, Weather weather) {
		super(time);
		this.id = id;
		this.srcJun = srcJun;
		this.destJunc = destJunc;
		this.length = length;
		this.co2Limit = co2Limit;
		this.maxSpeed = maxSpeed;
		this.weather = weather;
	}
	
	@Override
	void execute(RoadMap map) {
		Road r = new IntercityRoad(id, srcJun, destJunc, maxSpeed, co2Limit, length, weather);
		map.addRoad(r);
	}

}
