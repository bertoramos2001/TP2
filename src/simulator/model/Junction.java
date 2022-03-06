package simulator.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class Junction extends SimulatedObject {

	private List<Road> enteringRoadList;
	private Map<Junction,Road> leavingRoadMap;
	private List<List<Vehicle>> queueList;
	private Map<Road,List<Vehicle>> queueMap; //Lo recomienda por eficiencia pero no es indispensable
	private int greenLightIndex;
	private int lastSwitchingStep;
	private LightSwitchingStrategy lsStrategy;
	private DequeuingStrategy dqStrategy;
	protected int xCoor, yCoor;//No se usan esta prÃ¡ctica
	
	Junction(String id, LightSwitchingStrategy lsStrategy, DequeuingStrategy dqStrategy, int xCoor, int yCoor) {
			super(id);
			
			if (lsStrategy == null) {
				throw new IllegalArgumentException("LightSwitchStrategy can't be null");
			}
			
			if (dqStrategy == null) {
				throw new IllegalArgumentException("DequeuingStrategy can't be null");
			}
			
			if (xCoor < 0 || yCoor < 0) {
				throw new IllegalArgumentException("Coordinates must be positive numbers");
			}
			
			greenLightIndex = -1;
			lastSwitchingStep = 0;
			this.xCoor = xCoor;
			this.yCoor = yCoor;
			this.lsStrategy = lsStrategy;
			this.dqStrategy = dqStrategy;
			enteringRoadList = new ArrayList<Road>();
			leavingRoadMap = new HashMap<Junction, Road>();
			queueList = new ArrayList<List<Vehicle>>();
			queueMap = new HashMap<Road, List<Vehicle>>();		
	}
	
	void addIncommingRoad(Road r) {
		if (r.getDest() != this) {
			throw new IllegalArgumentException("Entering road does not correspond to this junction");
		}
		
		enteringRoadList.add(r);
		queueList.add(new LinkedList<Vehicle>());
		queueMap.put(r,  new LinkedList<Vehicle>());
		
	}
	
	void addOutGoingRoad(Road r) {
		if (r.getSrc() != this) {
			throw new IllegalArgumentException("Leaving road does not correspond to this junction");
		}
		
		if (leavingRoadMap.containsKey(r.getDest())) {
			throw new IllegalArgumentException("Another road has the same dest junction");
		}
		
		leavingRoadMap.put(r.getDest(), r); //j es el cruce destino de la carretera r
	}
	
	void enter(Vehicle v) {
		//int i = 0;
		boolean found = false;
		int aux = 0;
		
		for (int i = 0; i < enteringRoadList.size(); i++) {
			if (v.getRoad() == enteringRoadList.get(i)) {
				found = true;
				aux = i;
				break;
			}
		}
		
		if (found) {
			queueList.get(aux).add(v);
			queueMap.get(enteringRoadList.get(aux)).add(v);
		}
	}
	
	Road roadTo(Junction j) {
		Road r;
		r = leavingRoadMap.get(j);
		return r;
	}

	@Override
	void advance(int time) {
		if (greenLightIndex != -1) {
			List<Vehicle> vehiclesToLeave = dqStrategy.dequeue(queueList.get(greenLightIndex));
			
			if (vehiclesToLeave != null) {
				for (Vehicle v : vehiclesToLeave) {
					v.moveToNextRoad();
				}
				queueList.get(greenLightIndex).removeAll(vehiclesToLeave); //borramos los vehiculos de la carretera de la que se van
				queueMap.get(enteringRoadList.get(greenLightIndex)).removeAll(vehiclesToLeave); //borramos tambien los vehiculos del mapa de colas
			}
		}
		
		int newGreenLightIndex = lsStrategy.chooseNextGreen(enteringRoadList, queueList, greenLightIndex, lastSwitchingStep, time);
		if (newGreenLightIndex != greenLightIndex) {
			greenLightIndex = newGreenLightIndex;
			lastSwitchingStep = time;
		}
		
	}

	@Override
	public JSONObject report() {
		JSONObject obj = new JSONObject();
		JSONArray arr = new JSONArray();
		
		obj.put("id", _id);
		if ((greenLightIndex != -1) && enteringRoadList.get(greenLightIndex) != null) {
			obj.put("green", enteringRoadList.get(greenLightIndex).getId());
		} else {
			obj.put("green", "none");
		}

		for (int i = 0 ; i < enteringRoadList.size(); i++) {
			JSONObject roadObj = new JSONObject();
			roadObj.put("road", enteringRoadList.get(i).getId());
			
			List<Vehicle> list = queueList.get(i);
			
			JSONArray roadArr = new JSONArray();
			for (Vehicle v : list) {
				roadArr.put(v.getId());
			}
			
			roadObj.put("vehicles", roadArr);
			arr.put(roadObj);
		}
		
		obj.put("queues", arr);

		return obj;
	}

}

