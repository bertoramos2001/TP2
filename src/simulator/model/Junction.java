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
	private int lastSwitchingStep;
	private LightSwitchingStrategy lsStrategy;
	private DequeuingStrategy dqStrategy;
	protected int xCoor, yCoor;//No se usan esta práctica
	
	Junction(String id, LightSwitchingStrategy lsStrategy, DequeuingStrategy dqStrategy, int xCoor, int yCoor) {
			super(id);
			
			if (lsStrategy == null) {
				throw new IllegalArgumentException("LightSwitchStrategy can't be null");
			}
			
			if (dqStrategy == null) {
				throw new IllegalArgumentException("LightSwitchStrategy can't be null");
			}
			
			if (xCoor < 0 || yCoor < 0) {
				throw new IllegalArgumentException("Coor must be a positive number");
			}
			
			greenLightIndex = -1;
			lastSwitchingStep = 0;
			this.xCoor = xCoor;
			this.yCoor = yCoor;
			this.lsStrategy = lsStrategy;
			this.dqStrategy = dqStrategy;
			
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
