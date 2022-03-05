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
	private Map<Road,List<Vehicle>> queueRoad; //Lo recomienda por eficiencia pero no es indispensable
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
			queueRoad = new HashMap<Road, List<Vehicle>>();		
	}
	
	void addIncommingRoad(Road r) {
		if (r.getDest() != this) {
			throw new IllegalArgumentException("Entering road does not correspond to this junction");
		}
		
		enteringRoadList.add(r);
		List<Vehicle> q = new LinkedList<Vehicle>();
		q = r.getVehicles();
		queueList.add(q);
		
		queueRoad.put(r, q);
		
	}
	
	void addOutGoingRoad(Road r) {
		if (r.getSrc() != this) {
			throw new IllegalArgumentException("Leaving road does not correspond to this junction");
		}
		
		Junction j = r.getDest();
		Road rd = this.roadTo(j);
		
		if (rd != null) {
			throw new IllegalArgumentException("Another road to the junction already exists");
		}
		
		leavingRoadMap.put(j, r); //j es el cruce destino de la carretera r
	}
	
	void enter(Vehicle v) {
		Road r = v.getRoad();
		r.enter(v);
	}
	
	Road roadTo(Junction j) {
		Road r = null;
		int size = leavingRoadMap.size();
		int i = 0;

		while (r == null && i < size) {
			if (leavingRoadMap.get(j) != null) {
				r = leavingRoadMap.get(j);
			}
			i++;
		}
		
		return r;
	}

	@Override
	void advance(int time) {
		// TODO: no se si esta bien esta funcion
		//TODO: no se si lo de greenLightIndex != -1 esta bien, que hace si estan todos en rojo?
		if (greenLightIndex != -1) {
			List<Vehicle> vehiclesToLeave = dqStrategy.dequeue(queueList.get(greenLightIndex));
			
			for (Vehicle v : vehiclesToLeave) {
				v.moveToNextRoad();
			}
			
			queueList.get(greenLightIndex).removeAll(vehiclesToLeave); //borramos los vehiculos de la carretera de la que se van
		
		}
		
		int newGreenLightIndex = lsStrategy.chooseNextGreen(enteringRoadList, queueList, greenLightIndex, lastSwitchingStep, time);
		if (newGreenLightIndex != greenLightIndex) {
			greenLightIndex = newGreenLightIndex;
			lastSwitchingStep = time;
		}
		
	}

	@Override
	public JSONObject report() {
		JSONObject obj = new JSONObject(), roadObj = new JSONObject();
		JSONArray arr = new JSONArray(), roadArr = new JSONArray();
		
		obj.put("id", _id);
		if ((greenLightIndex != -1) && enteringRoadList.get(greenLightIndex) != null) {
			obj.put("green", enteringRoadList.get(greenLightIndex).getId());
		} else {
			obj.put("green", "none");
		}

		for (Road r : enteringRoadList) {
			roadObj.put("road", r.getId());
			
			for (Vehicle v : r.getVehicles()) {
				roadArr.put(v.getId());
			}
			roadObj.put("vehicles", roadArr);
			arr.put(roadObj);
		}
		
		obj.put("queues", arr);

		return obj;
	}

}

