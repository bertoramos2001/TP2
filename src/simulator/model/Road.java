package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public abstract class Road extends SimulatedObject {
	
	protected Junction origin;
	protected Junction destination;
	protected int length;
	protected int maxSpeed;
	protected int actualSpeedLimit;
	protected int contLimit;
	protected Weather weather;
	protected int totalCont;
	protected List<Vehicle> vehicles; //debe estar ordenada por la localizacion de los vehiculos en orden descendente
	
	

	Road(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length, Weather weather) {
		super(id);
		
		if (maxSpeed <= 0) {
			throw new IllegalArgumentException("The max speed must be positive");
		}
		
		if (contLimit < 0) {
			throw new IllegalArgumentException("Contamination limit must be non negative");
		}
		
		if (length <= 0) {
			throw new IllegalArgumentException("The road length must be positive");
		}
		
		if (srcJunc == null || destJunc == null || weather == null) {
			throw new IllegalArgumentException("sorce junction, destination junction and weather cannot be null");
		}
		
		origin = srcJunc;
		destination = destJunc;
		this.maxSpeed = maxSpeed;
		actualSpeedLimit = maxSpeed;
		this.contLimit = contLimit;
		this.length = length;
		this.weather = weather;
		this.vehicles = new ArrayList<Vehicle>();
		
		origin.addOutGoingRoad(this);
		destination.addIncommingRoad(this);
		
	}
	
	void enter(Vehicle v) {
		if (v.getSpeed() != 0) {
			throw new IllegalArgumentException("Vehicle should have no speed at the beginning");
		}
		
		if (v.getLocation() != 0) {
			throw new IllegalArgumentException("Vehicle should be at position 0 at the beginning");
		}
		
		vehicles.add(v);
	}
	
	void exit(Vehicle v) {
		vehicles.remove(v);
	}
	
	void setWeather(Weather w) {
		if (w == null) {
			throw new IllegalArgumentException("Weather cannot be null");
		}
		
		weather = w;
	}
	
	void addContamination(int contaminationThisStep) {
		if (contaminationThisStep < 0) {
			throw new IllegalArgumentException("Contamination cannot be negative");
		}
		
		totalCont += contaminationThisStep;
	}
	
	abstract void reduceTotalContamination();
	
	abstract void updateSpeedLimit();
	
	abstract int calculateVehicleSpeed(Vehicle v);

	@Override
	void advance(int time) {
		this.reduceTotalContamination();
		this.updateSpeedLimit();
		
		for (int i = 0; i < vehicles.size(); i++) {
			Vehicle actV = vehicles.get(i);
			actV.setSpeed(calculateVehicleSpeed(actV));
			actV.advance(time);
		}
		//ordenar vehiculos de la carretera por localizacion. Esto está definido en la clase compareVehicles
		vehicles.sort(new compareVehicles());
	}

	@Override
	public JSONObject report() {
		JSONObject obj = new JSONObject();
		
		obj.put("id", _id);
		obj.put("speedlimit", actualSpeedLimit);
		obj.put("co2", totalCont);
		obj.put("weather", weather.toString());
		
		JSONArray arr = new JSONArray();
		
		for (Vehicle v : vehicles) {
			arr.put(v.getId());
		}
		
		obj.put("vehicles", arr);
		
		return obj;
	}
	
	public int getLength() {
		return length;
	}

	public Junction getSrc() {
		return origin;
	}

	public Junction getDest() {
		return destination;
	}

	public int getMaxSpeed() {
		return maxSpeed;
	}

	public int getSpeedLimit() {
		return actualSpeedLimit;
	}

	public int getContLimit() {
		return contLimit;
	}

	public Weather getWeather() {
		return weather;
	}

	public int getTotalCO2() {
		return totalCont;
	}

	public List<Vehicle> getVehicles() {
		//devuelve lista de solo lectura
		return Collections.unmodifiableList(vehicles);
	}

}
