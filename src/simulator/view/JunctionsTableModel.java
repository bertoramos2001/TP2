package simulator.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Junction;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class JunctionsTableModel extends AbstractTableModel implements TrafficSimObserver {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<Junction> junctions;
	private String[] colNames = { "id", "Green", "Queues"};
	private Controller ctrl;
	
	public JunctionsTableModel(Controller _ctrl) {
		junctions = new ArrayList<Junction>();
		ctrl = _ctrl;
		ctrl.addObserver(this);
	}
	
	public void update() {
		fireTableDataChanged();	
	}
	
	public void setJunctionsList(List<Junction> junctionsList) {
		junctions = junctionsList;
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
		return junctions == null ? 0 : junctions.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object s = null;
		switch (columnIndex) {
		case 0:
			s = junctions.get(rowIndex).getId();
			break;
		case 1:
			int greenLight = junctions.get(rowIndex).getGreenLightIndex();
			
			if(greenLight == -1) {
				s = "NONE";	
			} else {
				s = junctions.get(rowIndex).getInRoads().get(greenLight);
			}
			break;
		case 2:
			String sb = "";
			for (int i = 0; i < junctions.get(rowIndex).getInRoads().size(); i++) {
				Road actRoad = junctions.get(rowIndex).getInRoads().get(i);
				sb = sb + actRoad.getId() + ":" + junctions.get(rowIndex).getQueueList().get(i) + " "; //mediante esta linea añadimos al modelo las listas de coches que estan esperando en el cruce actual en todas las carreteras
			}
			
			s = sb;
			break;
		}	
		return s;
	}

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		setJunctionsList(map.getJunctions());		
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		setJunctionsList(map.getJunctions());
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		setJunctionsList(map.getJunctions());
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		setJunctionsList(map.getJunctions());
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		setJunctionsList(map.getJunctions());
	}

	@Override
	public void onError(String err) {
		// TODO Auto-generated method stub
		
	}

}
