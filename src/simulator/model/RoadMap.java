package simulator.model;

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
		//TODO: inicializar los atributos de arriba en el constructor
	}
	
	void addJunction(Junction j) {
		//TODO: hacer función
	}
	
	void addRoad(Road r) {
		//TODO: hacer función
	}
	
	void addVehicle(Vehicle v) {
		//TODO: hacer función
	}
	
	public Junction getJunction(String id) {
		//TODO: hacer función
		return null;
	}
	
	public Road getRoad(String id) {
		//TODO: hacer función
		return null;
	}
	
	public Vehicle getVehicle(String id) {
		//TODO: hacer función
		return null;
	}
	
	public List<Junction>getJunctions() {
		return null;
	}
	
	public List<Road>getRoads() {
		return null;
	}
	
	public List<Vehicle>getVehicles() {
		return null;
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
		//TODO: no se si esta bien asi
		obj.put("junctions", junctionList);
		obj.put("road", roadList);
		obj.put("vehicles", vehicleList);
		
		return obj;
	}
}
