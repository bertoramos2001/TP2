package simulator.view;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import javax.swing.*;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class ControlPanel extends JPanel implements TrafficSimObserver {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Controller ctrl;

	ControlPanel(Controller ctrl) {
		this.ctrl = ctrl;
		ctrl.addObserver(this);
		initGUI();
	}
	
	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onError(String err) {
		// TODO Auto-generated method stub
		
	}
	
	 private void initGUI() {
		 this.setLayout(new BorderLayout());
		JPanel controlPanel = new JPanel(new BorderLayout());
		this.add(controlPanel);
		
		JToolBar myToolBar = new JToolBar();
		controlPanel.add(myToolBar);
		
		JFileChooser fc = new JFileChooser();
		fc.setCurrentDirectory(new File("./resources/examples"));
		
		JButton folderButton = new JButton();
		folderButton.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage("resources/icons/open.png")));
		folderButton.setToolTipText("Open a file");
		myToolBar.add(folderButton);
		folderButton.addActionListener((e) -> {loadFile(fc);});
		
		myToolBar.addSeparator();
		
		JButton contClassButton = new JButton();
		contClassButton.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage("resources/icons/co2class.png")));
		contClassButton.setToolTipText("Change CO2 Class of a Vehicle");
		myToolBar.add(contClassButton);
		//listener del boton
		
		JButton weatherButton = new JButton();
		weatherButton.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage("resources/icons/weather.png")));
		weatherButton.setToolTipText("Change Weather of a Road");
		myToolBar.add(weatherButton);
		//listener del boton
		
		myToolBar.addSeparator();
		
		JButton playButton = new JButton();
		playButton.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage("resources/icons/run.png")));
		playButton.setToolTipText("Run the simulator");
		myToolBar.add(playButton);
		//listener del boton
		
		JButton stopButton = new JButton();
		stopButton.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage("resources/icons/stop.png")));
		stopButton.setToolTipText("Stop the simulator");
		myToolBar.add(stopButton);
		//listener del boton
		
		JLabel ticks = new JLabel("Ticks");
		myToolBar.add(ticks);
		
		JSpinner contTicks = new JSpinner(new SpinnerNumberModel(1, 1, 1000, 1));
		contTicks.setMaximumSize(new Dimension(50, 50));
		myToolBar.add(contTicks);
		
		myToolBar.add(Box.createGlue());
		myToolBar.addSeparator();
		
		JButton exitButton = new JButton();
		exitButton.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage("resources/icons/exit.png")));
		exitButton.setToolTipText("Exit the simulator");
		myToolBar.add(exitButton);
		exitButton.addActionListener((e) -> {
			int confirm = JOptionPane.showConfirmDialog(null, "Do you want to exit program?");
			if (confirm == JOptionPane.YES_OPTION) {
				System.exit(0);
			}
		});
		
		myToolBar.setMargin(new Insets(5, 5, 5, 5));
	}
	
		//TODO: este metodo hay que cambiarlo a control panel junto a todo lo de la toolbar, preguntar como se hace
		private void loadFile(JFileChooser fc) {
			int i = fc.showOpenDialog(this);
			if (i == JFileChooser.APPROVE_OPTION) {
				//TODO: el file tiene que tener un throws pero eso me da fallos
				ctrl.reset();
				//try {
				//	ctrl.loadEvents(new FileInputStream(fc.getSelectedFile()));
				//} catch (FileNotFoundException e) {
				//	throw new FileNotFoundException("Selected file was not found");
				//}
				System.out.println(fc.getSelectedFile());
			}
		}
	

}
