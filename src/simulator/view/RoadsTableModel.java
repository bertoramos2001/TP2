package simulator.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class RoadsTableModel extends AbstractTableModel implements TrafficSimObserver {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<Road> roads;
	private String[] colNames = { "id", "Length", "Weather", "Max. Speed", "Speed Limit", "Total CO2", "CO2 Limit"};
	private Controller ctrl;
	
	public RoadsTableModel(Controller _ctrl) {
		roads = new ArrayList<Road>();
		ctrl = _ctrl;
		ctrl.addObserver(this);
	}
	
	public void update() {
		fireTableDataChanged();	
	}
	
	public void setRoadsList(List<Road> roadsList) {
		roads = roadsList;
		update();
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}

	@Override
	public String getColumnName(int col) {
		return colNames[col];
	}
	
	@Override
	public int getColumnCount() {
		return colNames.length;
	}

	@Override
	public int getRowCount() {
		return roads == null ? 0 : roads.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object s = null;
		switch (columnIndex) {
		
		case 0:
			s = roads.get(rowIndex).getId();
			break;
		case 1:
			s = roads.get(rowIndex).getLength();
			break;
		case 2:
			s = roads.get(rowIndex).getWeather();
			break;
		case 3:
			s = roads.get(rowIndex).getMaxSpeed();
			break;
		case 4:
			s = roads.get(rowIndex).getSpeedLimit();
			break;
		case 5:
			s = roads.get(rowIndex).getTotalCO2();
			break;
		case 6:
			s = roads.get(rowIndex).getContLimit();
			break;
		}
		return s;
	}
	
	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				setRoadsList(map.getRoads());
			}
		});
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				setRoadsList(map.getRoads());
			}
		});
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				setRoadsList(map.getRoads());
			}
		});
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				setRoadsList(map.getRoads());
			}
		});
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				setRoadsList(map.getRoads());
			}
		});
	}

	@Override
	public void onError(String err) {
		
	}

}