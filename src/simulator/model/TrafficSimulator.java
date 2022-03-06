package simulator.model;


import java.util.List;
import org.json.JSONObject;
import simulator.misc.SortedArrayList;

public class TrafficSimulator {
	
	protected RoadMap roadMap;
	protected List<Event> eventList; //ordenada por el tiempo de los eventos
	protected int time;
	
	public TrafficSimulator() {
		roadMap = new RoadMap();
		eventList = new SortedArrayList<Event>();
		time = 0;
		
	}
	
	public void addEvent(Event e) {
		//TODO ver si se estan añadiendo ya ordenados en el sortedArray
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
	

}