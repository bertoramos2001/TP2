package simulator.model;

import java.util.List;

import org.json.JSONObject;

public class Road extends SimulatedObject {
	
	private Junction origin;
	private Junction destination;
	private int length;
	private int maxSpeed;
	private int actualSpeedLimit;
	private int contLimit;
	private Weather weather;
	private int totalCont;
	private List<Vehicle> vehicles;
	
	

	Road(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length, Weather weather) {
		super(id);
		// TODO Auto-generated constructor stub
	}
	
	public int getLength() {
		// TODO necesario para la clase vehicle, aun sin implementar
		return 0;
	}
	
	public void addContamination(int contaminationThisStep) {
		// TODO necesario para la clase vehicle, aun sin implementar
		
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
