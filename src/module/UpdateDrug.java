package module;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

import exceptions.DataInvalidException;
import misc.Validation;

public class UpdateDrug extends JFrame implements ActionListener {

	JLabel drugNameLabel;
	JLabel quantityLabel;
	JLabel drugCostPriceLabel;
	JLabel drugSalePriceLabel;
	JLabel companyNameLabel;
	JLabel supplierContactLabel;
	JLabel DrugBarcode;

	JTextField drugName;
	JTextField quantity;
	JTextField drugCostPrice;
	JTextField drugSalePrice;
	JTextField companyName;
	JTextField supplierContact;

	JButton reset;
	JButton update;

	JPanel panel;

	Connection con;

	public UpdateDrug() {
		/************* setting frame Behaviou******************************** */

		setTitle("update");
		setBackground(Color.WHITE);
		Dimension screenSize = getScreenSize();
		setSize(screenSize.width - 300, screenSize.height - 300);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		Container c = getContentPane();
		c.setLayout(null);

		/************** panel property****************************** */

		panel = new JPanel();
		panel.setLocation(50, 25);
		Dimension panelSize = getPanelSize(screenSize.width - 300, screenSize.height - 300);
		panel.setBackground(Color.WHITE);
		panel.setSize(panelSize);
		panel.setLayout(null);
		/**************** creating object Label ***************** */

		drugNameLabel = new JLabel("Drug Barcode:");
		quantityLabel = new JLabel("Quantity:");
		drugCostPriceLabel = new JLabel("Cost Price:");
		drugSalePriceLabel = new JLabel("Sale Price:");
		companyNameLabel = new JLabel("Company Name:");
		supplierContactLabel = new JLabel("Supplier Contact:");

		/********** creating object Text Field ******************* */

		drugName = new JTextField();
		quantity = new JTextField();
		drugCostPrice = new JTextField();
		drugSalePrice = new JTextField();
		companyName = new JTextField();
		supplierContact = new JTextField();

		/******** creating object Button**************** */

		reset = new JButton("RESET");
		update = new JButton("UPDATE");

		/******** Setting Component Location******* */

		drugNameLabel.setBounds(75, 25, 100, 50);
		quantityLabel.setBounds(75, 100, 100, 50);
		drugCostPriceLabel.setBounds(75, 175, 100, 50);
		drugSalePriceLabel.setBounds(75, 250, 100, 50);

		companyNameLabel.setBounds(75, 325, 100, 50);
		supplierContactLabel.setBounds(500, 25, 100, 50);

		drugName.setBounds(200, 40, 175, 20);
		quantity.setBounds(200, 120, 175, 20);
		drugCostPrice.setBounds(200, 190, 175, 20);
		drugSalePrice.setBounds(200, 270, 175, 20);

		companyName.setBounds(200, 340, 175, 20);
		supplierContact.setBounds(630, 40, 175, 20);

		update.setBounds(570, 250, 100, 40);
		reset.setBounds(700, 250, 100, 40);

		/********* Add component to frame********* */
		panel.add(drugNameLabel);
		panel.add(quantityLabel);

		panel.add(companyNameLabel);

		panel.add(drugCostPriceLabel);
		panel.add(drugSalePriceLabel);

		panel.add(supplierContactLabel);

		panel.add(drugName);
		panel.add(quantity);
		panel.add(drugCostPrice);
		panel.add(drugSalePrice);

		panel.add(companyName);
		panel.add(supplierContact);

		panel.add(update);
		panel.add(reset);

		reset.addActionListener(this);
		update.addActionListener(this);
		addWindowListener(new Validation());

		c.add(panel);

		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == update) {
			String query = "insert into medicine(drugName,drugCostPrice,drugSalePrice,supplierContact,drugQuantity) values (?,?,?,?,?,?,?,?,?,?,?,?);";
			try {

				Validation.characterStringValid(drugName.getText());
				Validation.isPhoneValid(supplierContact.getText());
				Validation.isNumberValid(quantity.getText());
				PreparedStatement ps = con.prepareStatement(query);
				ps.setString(1, drugName.getText());
				ps.setFloat(2, Float.parseFloat(drugCostPrice.getText()));
				ps.setFloat(3, Float.parseFloat(drugSalePrice.getText()));
				ps.setString(4, supplierContact.getText());
				ps.setInt(5, Integer.parseInt(quantity.getText()));
				ps.executeUpdate();
				JOptionPane.showMessageDialog(null, "Medicine added successfully");
				con.close();

			} catch (SQLException e1) {

				e1.printStackTrace();
			} catch (DataInvalidException e2) {
				JOptionPane.showMessageDialog(null, e2.getMessage());
			}

		}

		else if (ae.getSource() == reset) {

			drugName.setText("");
			quantity.setText("");
			drugCostPrice.setText("");
			drugSalePrice.setText("");
			companyName.setText("");
			supplierContact.setText("");

		}

	}

	private Dimension getScreenSize() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		return screenSize;
	}

	private Dimension getPanelSize(int width, int height) {
		Dimension panelSize = new Dimension();
		panelSize.setSize(width - 100, height - 100);
		return panelSize;

	}

}
