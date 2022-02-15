package simulator.model;

public class CityRoad extends Road {

	CityRoad(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length, Weather weather) {
		super(id, srcJunc, destJunc, maxSpeed, contLimit, length, weather);
	}

	@Override
	protected void reduceTotalContamination() {
		int x;
		
		if ((weather == Weather.STORM) || (weather == Weather.WINDY)) {
			x = 10;
		} else {
			x = 2;
		}
		
		totalCont -= x;
		
		if (totalCont < 0) {
			totalCont = 0;
		}
		
	}

	@Override
	protected void updateSpeedLimit() {
		// La velocidad no cambia en este tipo de carreteras
		
	}

	@Override
	protected int calculateVehicleSpeed(Vehicle v) {
		v.setSpeed(((11 - v.getContClass()) * actualSpeedLimit) / 11);
		return 0;
	}

}
