package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

public class RoadMap {
	List<Junction> junctionList;
	List<Road> roadList;
	List<Vehicle> vehicleList;
	Map<String, Junction> junctionMapId;
	Map<String, Road> roadMapId;
	Map<String, Vehicle> vehicleMapId;
	
	RoadMap() {
		junctionList = new ArrayList<Junction>();
		roadList = new ArrayList<Road>();
		vehicleList = new ArrayList<Vehicle>();
		junctionMapId = new HashMap<String, Junction>();
		roadMapId = new HashMap<String, Road>();
		vehicleMapId = new HashMap<String, Vehicle>();
	}
	
	void addJunction(Junction j) {
		boolean found = junctionMapId.containsKey(j.getId());
		
		if (!found) { //si no hay ningun cruce con el mismo identificador en la lista, lo añadimos y modificamos la lista y el mapa
			junctionList.add(j);
			
			junctionMapId.put(j.getId(), j);
		}
	}
	
	void addRoad(Road r) {
		boolean found = roadMapId.containsKey(r.getId());
		
		//TODO: duda enunciado: los cruces que conecta la carretera existen en el mapa de carreteras o en el mapa de junction??
		//hacer lo de (ii) del enunciado y lanzar excepcion
		
		if (!found) {
			roadList.add(r);
			roadMapId.put(r.getId(), r);
		}
		
	}
	
	void addVehicle(Vehicle v) {
		boolean found = vehicleMapId.containsKey(v.getId());
		boolean valid = true;
		int i = 0;
		//TODO: falta comprobar itinerario valido
		List<Junction> itinerary = v.getItinerary();
		
		//while(i < itinerary.size() && valid) {
		//	i++;
		//}
		
		if (!found) {
			vehicleList.add(v);
			vehicleMapId.put(v.getId(), v);
		}
	}
	
	public Junction getJunction(String id) {
		return junctionMapId.get(id);
	}
	
	public Road getRoad(String id) {
		return roadMapId.get(id);
	}
	
	public Vehicle getVehicle(String id) {
		return vehicleMapId.get(id);
	}
	
	public List<Junction>getJunctions() {
		return Collections.unmodifiableList(junctionList);
	}
	
	public List<Road>getRoads() {
		return Collections.unmodifiableList(roadList);
	}
	
	public List<Vehicle>getVehicles() {
		return Collections.unmodifiableList(vehicleList);
	}
	
	void reset() {
		junctionList.clear();
		roadList.clear();
		vehicleList.clear();
		junctionMapId.clear();
		roadMapId.clear();
		vehicleMapId.clear();
	}
	
	public JSONObject report() {
		JSONObject obj = new JSONObject();
		obj.put("junctions", junctionList);
		obj.put("road", roadList);
		obj.put("vehicles", vehicleList);
		
		return obj;
	}
}
