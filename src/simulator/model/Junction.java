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
			//TODO: creo que falta inicializar listas
			
	}
	
	void addIncommingRoad(Road r) {
		if (r.getDest() != this) {
			throw new IllegalArgumentException("Entering road does not correspond to this junction");
		}
		
		enteringRoadList.add(r);
		List<Vehicle> aux_r = new LinkedList<Vehicle>();
		queueList.add(aux_r);
		queueRoad.put(r, aux_r);
		//TODO: hacer metodo
	}
	
	void addOutGoingRoad(Road r) {
		//TODO: hacer funciÃ³n
	}
	
	void enter(Vehicle v) {
		Road r = v.getRoad();
		r.enter(v);
	}
	
	Road roadTo(Junction j) {
		Road r = null;
		int size = leavingRoadMap.size();
		int i = 0;
		//TODO: no se si este bucle esta bien
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
		// TODO: no se si esta bien esta funcion, sin acabar
		//1. emplear dequeuing strategy y recibir la lista de vehiculos que salen
		List<Vehicle> vehiclesToLeave = dqStrategy.dequeue(queueList.get(greenLightIndex));
		
		//2. pide a los vehiculos que se muevan a sus siguientes carreteras
		for (Vehicle v : vehiclesToLeave) {
			v.moveToNextRoad();
		}
		//3. elimina a los vehiculos de las colas (se hace automÃ¡ticamente en el paso anterior??)
		
		//4. utiliza lightswitching strategy para calcular la carretera en verde
		int newGreenLightIndex = lsStrategy.chooseNextGreen(enteringRoadList, queueList, greenLightIndex, lastSwitchingStep, time);
		//5. si esta es diferente a la actual, la pone en verde y pone el ultimo paso de cambio de semaforo al paso actual
		if (newGreenLightIndex != greenLightIndex) {
			greenLightIndex = newGreenLightIndex;
			lastSwitchingStep = time;
		}
		
	}

	@Override
	public JSONObject report() {
		JSONObject obj = new JSONObject();
		
		obj.put("id", _id);
		
		if (enteringRoadList.get(greenLightIndex) != null) {
			obj.put("green", enteringRoadList.get(greenLightIndex).getId());
		} else {
			obj.put("green", "none");
		}
		//TODO: ver como hacer esta parte del report()
		obj.put("queues", "");
		//pintar cada una de las listas dentro del apartado queues

		return obj;
	}

}
