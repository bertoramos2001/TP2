package simulator.view;

import java.util.List;

import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;
import simulator.model.Vehicle;

public class VehiclesTableModel extends AbstractTableModel implements TrafficSimObserver {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<Vehicle> vehicles;
	private String[] colNames = { "id", "Location", "Itinerary", "CO2 Class", "Max. Speed", "Speed", "Total CO2", "Distance"};
	private Controller ctrl;
	
	public VehiclesTableModel(Controller _ctrl) {
		vehicles = null;
		ctrl = _ctrl;
		ctrl.addObserver(this);
	}
	
	public void update() {
		fireTableDataChanged();	
	}
	
	public void setVehiclesList(List<Vehicle> vehiclesList) {
		vehicles = vehiclesList;
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
		return vehicles == null ? 0 : vehicles.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object s = null;
		switch (columnIndex) {
		case 0:
			s = vehicles.get(rowIndex).getId();
			break;
		case 1:	
			s = vehicles.get(rowIndex).getRoad() + ":" + vehicles.get(rowIndex).getLocation();
			break;
		case 2:
			s = vehicles.get(rowIndex).getItinerary();
			break;
		case 3:
			s = vehicles.get(rowIndex).getContClass();
			break;
		case 4:
			s = vehicles.get(rowIndex).getMaxSpeed();
			break;
		case 5:
			s = vehicles.get(rowIndex).getSpeed();
			break;
		case 6:
			s = vehicles.get(rowIndex).getTotalCO2();
			break;
		case 7:
			s = vehicles.get(rowIndex).getDistance();
			break;
		}
		return s;
	}
	
	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				setVehiclesList(map.getVehicles());
			}
		});
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				setVehiclesList(map.getVehicles());
			}
		});
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				setVehiclesList(map.getVehicles());
			}
		});
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				setVehiclesList(map.getVehicles());
			}
		});
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				setVehiclesList(map.getVehicles());
			}
		});
	}

	@Override
	public void onError(String err) {
		
	}

}
