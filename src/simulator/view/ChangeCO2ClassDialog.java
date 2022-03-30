package simulator.view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.util.List;

import javax.swing.*;

import simulator.model.Vehicle;


public class ChangeCO2ClassDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JComboBox<Vehicle> vehicles;
	private DefaultComboBoxModel<Vehicle> vehiclesModel;
	private int status;
	
	public ChangeCO2ClassDialog(ControlPanel controlPanel) {
		//TODO: controlPanel va en el constructor? hay que llamar a super con el controlPanel?
		initGUI();
	}
	

	private void initGUI() {
		status = 0;
		
		setTitle("Change CO2 Class");
		
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		setContentPane(mainPanel);
		
		JLabel infoMsg = new JLabel("Schedule an event to change the CO2 class of a vehicle after a given number of simulation ticks from now");
		infoMsg.setAlignmentX(CENTER_ALIGNMENT);
		mainPanel.add(infoMsg);
		
		mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
		
		JPanel middlePanel = new JPanel();
		middlePanel.setAlignmentX(CENTER_ALIGNMENT);
		mainPanel.add(middlePanel);
		
		mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
		
		JPanel buttonsPanel = new JPanel();
		middlePanel.setAlignmentX(CENTER_ALIGNMENT);
		mainPanel.add(buttonsPanel);
		
		JLabel vehicleLabel = new JLabel("Vehicle: ");
		middlePanel.add(vehicleLabel);
		
		vehiclesModel = new DefaultComboBoxModel<>();
		vehicles = new JComboBox<>(vehiclesModel);
		middlePanel.add(vehicles);
		
		JLabel CO2Label = new JLabel("CO2 Class: ");
		middlePanel.add(CO2Label);
		//TODO: falta añadir el combo box de co2 class
		
		JLabel ticksLabel = new JLabel("Ticks: ");
		middlePanel.add(ticksLabel);
		
		JSpinner contTicks = new JSpinner(new SpinnerNumberModel(1, 1, 1000, 1));
		contTicks.setMaximumSize(new Dimension(20, 40));
		middlePanel.add(contTicks);
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener((e) -> {
			status = 0;
			setVisible(false);
		});
		buttonsPanel.add(cancelButton);
		
		JButton okButton = new JButton("OK");
		okButton.addActionListener((e) -> {
			status = 1;
			setVisible(false);
		});
		buttonsPanel.add(okButton);
		
		
		
		setLocation(300, 300);
		setSize(500, 200);
		setResizable(false);
		setVisible(true);
		pack();
	}
	
	public int open(List<Vehicle> vehicles) {
		vehiclesModel.removeAllElements();
		for (Vehicle v : vehicles)
			vehiclesModel.addElement(v);

		setLocation(getParent().getLocation().x + 10, getParent().getLocation().y + 10);

		setVisible(true);
		return status;
	}

}
