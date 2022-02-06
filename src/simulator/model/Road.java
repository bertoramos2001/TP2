package simulator.model;

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
			throw new IllegalArgumentException("the max speed must be positive");
		}
		
		if (contLimit < 0) {
			throw new IllegalArgumentException("contamination limit must be non negative");
		}
		
		if (length <= 0) {
			throw new IllegalArgumentException("the road length must be positive");
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
		
	}
	
	public void exit(Vehicle v) {
		
	}
	
	public void setWeather(Weather w) {
		
	}
	
	public void addContamination(int contaminationThisStep) {
		// TODO necesario para la clase vehicle, aun sin implementar
		
	}
	
	protected abstract void reduceTotalContamination();
	
	protected abstract void updateSpeedLimit();
	
	protected abstract int calculateVehicleSpeed(Vehicle v);

	@Override
	void advance(int time) {
		// TODO Auto-generated method stub
		
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
		//TODO: debe devolver una lista de solo lectura
		return vehicles;
	}

}
