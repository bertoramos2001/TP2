package simulator.model;

import java.util.Collections;
import java.util.List;

import org.json.JSONObject;

public abstract class Road extends SimulatedObject {
	
	private Junction origin;
	private Junction destination;
	private int length;
	private int maxSpeed;
	private int actualSpeedLimit;
	private int contLimit;
	private Weather weather;
	private int totalCont;
	private List<Vehicle> vehicles; //debe estar ordenada por la localizacion de los vehiculos en orden descendente
	
	

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
		
		origin.addOutGoingRoad(this);
		destination.addIncommingRoad(this);
		
	}
	
	public void enter(Vehicle v) {
		if (v.getSpeed() != 0) {
			throw new IllegalArgumentException("Vehicle should have no speed at the beginning");
		}
		
		if (v.getLocation() != 0) {
			throw new IllegalArgumentException("Vehicle should be at position 0 at the beginning");
		}
		
		vehicles.add(v);
	}
	
	public void exit(Vehicle v) {
		vehicles.remove(v);
	}
	
	public void setWeather(Weather w) {
		if (w == null) {
			throw new IllegalArgumentException("Weather cannot be null");
		}
		
		weather = w;
	}
	
	public void addContamination(int contaminationThisStep) {
		if (contaminationThisStep < 0) {
			throw new IllegalArgumentException("Contamination cannot be negative");
		}
		
		totalCont += contaminationThisStep;
	}
	
	protected abstract void reduceTotalContamination();
	
	protected abstract void updateSpeedLimit();
	
	protected abstract int calculateVehicleSpeed(Vehicle v);

	@Override
	void advance(int time) {
		// TODO Auto-generated method stub
		
		//1. llamar a reduceTotalContamination
		
		//2. llamar a updateSpeedLimit
		
		//3. recorrer la lista y hacer lo siguiente: (hecho)
		
		for (int i = 0; i < vehicles.size(); i++) {
			Vehicle actV = vehicles.get(i);
			actV.setSpeed(calculateVehicleSpeed(actV));
			
			actV.advance(time);
		}
		
		//4. ordenar la lista de vehiculos por localizacion
		
	}

	@Override
	public JSONObject report() {
		JSONObject obj = new JSONObject();
		
		obj.put("id", _id);
		obj.put("speedLimit", actualSpeedLimit);
		obj.put("weather", weather);
		obj.put("co2", totalCont);
		obj.put("vehicles", vehicles);

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
