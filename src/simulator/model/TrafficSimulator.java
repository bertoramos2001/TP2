package simulator.model;


import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import org.json.JSONObject;
import simulator.misc.SortedArrayList;

public class TrafficSimulator implements Observable<TrafficSimObserver>{
	
	protected RoadMap roadMap;
	protected List<Event> eventList; //ordenada por el tiempo de los eventos
	protected int time;
	private List<TrafficSimObserver> observerList;
	
	public TrafficSimulator() {
		roadMap = new RoadMap();
		eventList = new SortedArrayList<Event>();
		observerList = new ArrayList<TrafficSimObserver>();
		time = 0;
		
	}
	
	public void addEvent(Event e) {
		eventList.add(e);
		onEventAdded(roadMap, eventList, e, time);
	}
	
	public void advance() { 
		time++;
		int aux = 0;
		
		onAdvanceStart(roadMap, eventList, time);
			
			while (aux < eventList.size() && time == eventList.get(aux).getTime()) { //ejecutamos los eventos que coincidan en el turno actual, al estar ordenado por tiempo, si encontramos uno que no coincide con el tiempo actual, dejaremos de buscar
				eventList.get(aux).execute(roadMap);
				aux++;
			}
		
			for (int i = 0; i < aux; i++) { //borraremos todos los eventos ejecutados anteriormente (estan ordenados por tiempo)
				eventList.remove(0);
			}
			
		try {
			List<Junction> junctionList = roadMap.getJunctions();
			for (Junction j : junctionList) {
				j.advance(time);
			}
			
			List<Road> roadList = roadMap.getRoads();
			for (Road r : roadList) {
				r.advance(time);
			}
						
		} catch (Exception e){
			onError(e.getMessage());
			//JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
		
		onAdvanceEnd(roadMap, eventList, time);
	
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
		if(!observerList.contains(o)) {
			observerList.add(o);
		}
	}

	@Override
	public void removeObserver(TrafficSimObserver o) {
		if(observerList.contains(o)) {
			observerList.remove(o);
		}
	}
	
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		for (TrafficSimObserver o : observerList) {
			o.onAdvanceStart(map, events, time);
		}
	}
	
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		for (TrafficSimObserver o : observerList) {
			o.onAdvanceEnd(map, events, time);
		}
	}
	
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		for (TrafficSimObserver o : observerList) {
			o.onEventAdded(map, events, e, time);
		}
	}
	
	public void onReset(RoadMap map, List<Event> events, int time) {
		for (TrafficSimObserver o : observerList) {
			o.onReset(map, events, time);
		}
	}
	
	public void onRegister(RoadMap map, List<Event> events, int time) {
		for (TrafficSimObserver o : observerList) {
			o.onRegister(map, events, time);
		}
	}
	
	public void onError(String err) {
		for (TrafficSimObserver o : observerList) {
			o.onError(err);
		}
	}
	

}