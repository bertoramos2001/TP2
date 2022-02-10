package simulator.model;

import java.util.List;
import java.util.Map;

import org.json.JSONObject;

public class Junction extends SimulatedObject {

	private List<Road> enteringRoadList;
	private Map<Junction,Road> leavingRoadMap;
	private List<List<Vehicle>> queueList;
	private Map<Road,List<Vehicle>> queueRoad; //Lo recomienda por eficiencia pero no es indispensable
	private int greenLightIndex;
	private int lastStepLightChange;
	//private LightSwitchStrategy lsStrategy;
	//private DequeingStrategy dqStrategy;
	private int coor; //No se usan esta práctica
	
	//Junction(String id, LightSwitchStrategy lsStrategy, DequeingStrategy dqStrategy, int xCoor, int yCoor)
	Junction(String id){
			super(id);
			
			if (lsStrategy == null) {
				throw new IllegalArgumentException("LightSwitchStrategy can't be null");
			}
			
			if (dqStrategy == null) {
				throw new IllegalArgumentException("LightSwitchStrategy can't be null");
			}
			
			if () {
				throw new IllegalArgumentException("itinerary size must be at least 2");
			}
			
			greenLightIndex = -1;
			lastStepLightChange = 0;
			
	}
	
	void addIncommingRoad(Road r) {
		
	}
	
	void addOutGoingRoad(Road r) {
		
	}

	@Override
	void advance(int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public JSONObject report() {
		// TODO Auto-generated method stub
		return null;
	}

}
