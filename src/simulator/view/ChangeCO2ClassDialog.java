
package simulator.view;

import java.awt.Dimension;
import java.awt.Frame;
import java.util.List;

import javax.swing.*;

import simulator.model.Road;
import simulator.model.Vehicle;
import simulator.model.Weather;


public class ChangeCO2ClassDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JComboBox<Vehicle> vehicles;
	private DefaultComboBoxModel<Vehicle> vehiclesModel;
	private JComboBox<Integer> contClass;
	private int status;
	
	private JSpinner contTicks;

	public ChangeCO2ClassDialog(Frame parent) {
		super(parent, true);
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
		contClass = new JComboBox<>(new Integer[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10});
		middlePanel.add(contClass);

		JLabel ticksLabel = new JLabel("Ticks: ");
		middlePanel.add(ticksLabel);

		contTicks = new JSpinner(new SpinnerNumberModel(1, 1, 1000, 1));
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
		pack();
		setResizable(false);
		setVisible(false);
	}

	public int open(List<Vehicle> vehicles) {
		vehiclesModel.removeAllElements();
		for (Vehicle v : vehicles)
			vehiclesModel.addElement(v);

		setLocation(getParent().getLocation().x + ((getParent().getWidth() / 2) - (getWidth() /2)), getParent().getLocation().y + ((getParent().getHeight() / 2) - (getHeight() /2)));

		setVisible(true);
		return status;
	}
	
	public Vehicle getVehicle() {
		return (Vehicle) vehiclesModel.getSelectedItem();
	}

	public Integer getContClass() {
		return  (Integer) contClass.getSelectedItem();
	}
	
	public Integer getTime() {
		return (Integer) contTicks.getValue();
	}

}