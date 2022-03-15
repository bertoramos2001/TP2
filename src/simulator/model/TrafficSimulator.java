package simulator.model;


import java.util.List;
import org.json.JSONObject;
import simulator.misc.SortedArrayList;

public class TrafficSimulator implements Observable<TrafficSimObserver>{
	
	protected RoadMap roadMap;
	protected List<Event> eventList; //ordenada por el tiempo de los eventos
	protected int time;
	
	public TrafficSimulator() {
		roadMap = new RoadMap();
		eventList = new SortedArrayList<Event>();
		time = 0;
		
	}
	
	public void addEvent(Event e) {
		eventList.add(e);
	}
	
	public void advance() {
		time++;
		int aux = 0;
		
		while (aux < eventList.size() && time == eventList.get(aux).getTime()) { //ejecutamos los eventos que coincidan en el turno actual, al estar ordenado por tiempo, si encontramos uno que no coincide con el tiempo actual, dejaremos de buscar
			eventList.get(aux).execute(roadMap);
			aux++;
		}

		for (int i = 0; i < aux; i++) { //borraremos todos los eventos ejecutados anteriormente (estan ordenados por tiempo)
			eventList.remove(0);
		}
		
		List<Junction> junctionList = roadMap.getJunctions();
		for (Junction j : junctionList) {
			j.advance(time);
		}
		
		List<Road> roadList = roadMap.getRoads();
		for (Road r : roadList) {
			r.advance(time);
		}
	
	}
	
	public void reset() {
		roadMap.reset();
		eventList.clear();
		time = 0;
		
	}
	
	public JSONObject report() {
		JSONObject obj = new JSONObject();
	
		obj.put("time", time);
		obj.put("state", roadMap.report());
		
		return obj;
	}

	@Override
	public void addObserver(TrafficSimObserver o) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeObserver(TrafficSimObserver o) {
		// TODO Auto-generated method stub
		
	}
	
	void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		//TODO: ver si todos estos son package protected, si se implementan aqui, como implementarlos y cuando se llaman
	}
	
	void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		
	}
	
	void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		
	}
	
	void onReset(RoadMap map, List<Event> events, int time) {
		
	}
	
	void onRegister(RoadMap map, List<Event> events, int time) {
		
	}
	
	void onError(String err) {
		
	}
	

}