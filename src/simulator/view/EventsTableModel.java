package simulator.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class EventsTableModel extends AbstractTableModel implements TrafficSimObserver {

	private static final long serialVersionUID = 1L;
	
	private List<Event> events;
	private String[] _colNames = { "Time", "Desc." };
	private Controller ctrl;
	
	public EventsTableModel(Controller _ctrl) {
		events = new ArrayList<Event>();
		ctrl = _ctrl;
		ctrl.addObserver(this);
	}

	public void update() {
		// We need to notify changes, otherwise the table does not refresh.
		fireTableDataChanged();	
	}
	
	public void setEventsList(List<Event> events) {	
		this.events = events;
		update();
	}
	

	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}

	//this is for the column header
	@Override
	public String getColumnName(int col) {
		return _colNames[col];
	}

	@Override
	// mÃ©todo obligatorio
	// this is for the number of columns
	public int getColumnCount() {
		return _colNames.length;
	}

	@Override
	// mÃ©todo obligatorio
	// the number of row, like those in the events list
	public int getRowCount() {
		return events.size();
	}

	@Override
	// returns the value of a particular cell 
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object s = null;
		switch (columnIndex) {
		case 0:
			s = events.get(rowIndex).getTime();
			break;
		case 1:
			s = events.get(rowIndex).toString();
			break;
		}
		return s;
	}

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				setEventsList(events);
			}
		});
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				setEventsList(events);
			}
		});
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				setEventsList(events);
			}
		});
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				setEventsList(events);
			}
		});
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				setEventsList(events);
			}
		});
	}

	@Override
	public void onError(String err) {
	}
}