package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
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
		
		if (found) {
			throw new IllegalArgumentException("Cannot add junction, repeated id");
		}
		
		//si no hay ningun cruce con el mismo identificador en la lista, lo añadimos y modificamos la lista y el mapa
		junctionList.add(j);
		junctionMapId.put(j.getId(), j);
	}
	
	void addRoad(Road r) {
		boolean found = roadMapId.containsKey(r.getId());
		
		if (found) { //si se encuentra otra carretera con el mismo id en el mapa
			throw new IllegalArgumentException("Cannot have repeated roads in the map");
		}
		
		boolean foundj1 = false, foundj2 = false;
		int i = 0;
		while(i < junctionList.size() && (!foundj1 || !foundj2)) {
			if (junctionList.get(i) == r.getSrc()) {
				foundj1 = true;
			}
			if (junctionList.get(i) == r.getDest()) {
				foundj2 = true;
			}
			i++;
		}
		
		if (!foundj1 || !foundj2) {
			throw new IllegalArgumentException("Cannot find junctions to be joint by new road");
		}
		
		roadList.add(r);
		roadMapId.put(r.getId(), r);
		
	}
	
	void addVehicle(Vehicle v) {
		boolean found = vehicleMapId.containsKey(v.getId());
		
		if (found) { //si se encuentra otro vehiculo con el mismo id en el mapa
			throw new IllegalArgumentException("Cannot have repeated vehicles in the map");
		}
		
		boolean roadExists;
		int i = 0, j = 1, k;
		List<Junction> itinerary = v.getItinerary();

		while(j < itinerary.size()) { //bucle que recorre todo el itinerario, cogiendo pares consecutivos de cruces
			k = 0;
			roadExists = false;
			while (k < roadList.size() && !roadExists) { //bucle que recorre la lista de carreteras, buscando carreteras que unan los cruces del itinerario
				if (roadList.get(k).getSrc() == itinerary.get(i) && roadList.get(k).getDest() == itinerary.get(j)) {
					roadExists = true;
				}
				k++;
			}
			if (!roadExists) {
				throw new IllegalArgumentException("Vehicle itinerary is not valid");
			}
			i++;
			j++;
		}
		
		vehicleList.add(v);
		vehicleMapId.put(v.getId(), v);
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
		JSONArray arr = new JSONArray();
		
		for (Junction j : junctionList) {
			arr.put(j.report());
		}
		obj.put("junctions", arr);
		arr = new JSONArray(); //reseteamos el JSONArray para introducir nuevos elementos en roadList
		
		for (Road r : roadList) {
			arr.put(r.report());
		}
		obj.put("roads", arr);
		arr = new JSONArray(); //reseteamos el JSONArray para introducir nuevos elementos en vehicleList
		
		for (Vehicle v : vehicleList) {
			arr.put(v.report());
		}
		obj.put("vehicles", arr);
		
		return obj;
	}
}
