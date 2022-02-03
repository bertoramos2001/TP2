package simulator.model;

import org.json.JSONObject;

public class Road extends SimulatedObject {

	Road(String id) {
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
