package simulator.control;

import simulator.model.TrafficSimulator;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import simulator.factories.Factory;
import simulator.model.Event;
import simulator.model.TrafficSimObserver;

public class Controller {
	protected TrafficSimulator simulator;
	protected Factory<Event> eventsFactory;
	
	public Controller(TrafficSimulator sim, Factory<Event> eventsFactory) {
		if (sim == null || eventsFactory == null) {
			throw new IllegalArgumentException("traffic simulator and events factory cannot be null");
		}
		
		this.simulator = sim;
		this.eventsFactory = eventsFactory;
	}
	
	public void loadEvents(InputStream in) {
		JSONObject jo = new JSONObject(new JSONTokener(in));
		JSONArray arr = jo.getJSONArray("events");
		
		if (arr == null) {
			throw new IllegalArgumentException("input information is not valid");
		}
		
		for (int i = 0; i < arr.length(); i++) {
			JSONObject obj = arr.getJSONObject(i);
			Event ev = eventsFactory.createInstance(obj);
			simulator.addEvent(ev);
		}
	}
	
	public void run(int n, OutputStream out) {
		JSONObject jo, finalObj;
		JSONArray arr = new JSONArray();
		
		for (int i = 0; i < n; i++) {
			simulator.advance();
			jo = simulator.report();
			arr.put(jo);
		}
		
		finalObj = new JSONObject();
		finalObj.put("states", arr);
		
		PrintStream p = new PrintStream(out);
		p.println(finalObj.toString(3));
		
	}
	
	public void reset() {
		simulator.reset();
	}
	
	public void addObserver(TrafficSimObserver o) {
		//TODO: no se si la funcion es package protected (no lo menciona)
		simulator.addObserver(o);
	}
	
	public void removeObserver(TrafficSimObserver o) {
		//no se si la funcion es package protected (no lo menciona)
		simulator.removeObserver(o);
	}
	
	public void addEvent(Event e) {
		//no se si la funcion es package protected (no lo menciona)
		simulator.addEvent(e);
	}
}
