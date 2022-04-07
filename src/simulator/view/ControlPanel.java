package simulator.view;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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
	private boolean stopped;
	private RoadMap map;

	ControlPanel(Controller ctrl) {
		this.ctrl = ctrl;
		ctrl.addObserver(this);
		initGUI();
	}
	
	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		this.map = map;
		
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		this.map = map;
		
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		// TODO Auto-generated method stub
		this.map = map;
		
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		this.map = map;
		
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		this.map = map;
		
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
		//TODO: ver si esta gestión de errores ha de estar aqui
		folderButton.addActionListener((e) -> {try {
			loadFile(fc);
		} catch (IOException e1) {
			e1.printStackTrace();
		}});
		
		myToolBar.addSeparator();
		
		JButton contClassButton = new JButton();
		contClassButton.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage("resources/icons/co2class.png")));
		contClassButton.setToolTipText("Change CO2 Class of a Vehicle");
		myToolBar.add(contClassButton);
		contClassButton.addActionListener((e) -> {
			selectCont();
		});
		
		JButton weatherButton = new JButton();
		weatherButton.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage("resources/icons/weather.png")));
		weatherButton.setToolTipText("Change Weather of a Road");
		myToolBar.add(weatherButton);
		weatherButton.addActionListener((e) -> {
			selectWeather();
		});
		
		myToolBar.addSeparator();
		
		JSpinner contTicks = new JSpinner(new SpinnerNumberModel(1, 1, 1000, 1)); //declaramos aqui el spinner para poder usarlo en run(), mas abajo lo añadimos al toolbar
		
		JButton playButton = new JButton();
		playButton.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage("resources/icons/run.png")));
		playButton.setToolTipText("Run the simulator");
		myToolBar.add(playButton);
		playButton.addActionListener((e) -> {
			run_sim((Integer)contTicks.getValue());
		});
		
		JButton stopButton = new JButton();
		stopButton.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage("resources/icons/stop.png")));
		stopButton.setToolTipText("Stop the simulator");
		myToolBar.add(stopButton);
		stopButton.addActionListener((e) -> {
			stop();
		});
		//listener del boton
		
		JLabel ticks = new JLabel("Ticks");
		myToolBar.add(ticks);
		
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
	
		

		private void loadFile(JFileChooser fc) throws IOException {
			int i = fc.showOpenDialog(this);
			if (i == JFileChooser.APPROVE_OPTION) {
				ctrl.reset();
				ctrl.loadEvents(new FileInputStream(fc.getSelectedFile()));
				System.out.println(fc.getSelectedFile());
			}
		}
		
		private void selectCont() {
			ChangeCO2ClassDialog contDialog = new ChangeCO2ClassDialog((Frame)SwingUtilities.getWindowAncestor(this));
			int status = map != null ? contDialog.open(map.getVehicles()) : 0;
			
			if (status != 0) {
				//TODO: asignar la contaminacion al coche pedido por el usuario
			}
			
		}
		
		private void selectWeather() {
			ChangeWeatherDialog wDialog = new ChangeWeatherDialog((Frame)SwingUtilities.getWindowAncestor(this));
			int status = map != null ? wDialog.open(map.getRoads()) : 0;
			
		}
		
		private void enableToolBar(boolean enabled) {
			if (enabled) {
				//TODO: se ven los botones
			} else {
				//se inhabilitan los botones
			}
		}
		
		private void run_sim(int n) {
			if (n > 0 && !stopped) {
				try {
					ctrl.run(1);
				} catch (Exception e) {
					// TODO show error message
					stopped = true;
					return;
				}
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						run_sim(n - 1);
					}
				});
			} else {
				enableToolBar(true);
				stopped = true;
			}
		}
		
		private void stop() {
			stopped = true;
		}

}
