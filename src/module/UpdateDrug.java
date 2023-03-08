package module;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

import com.mysql.cj.x.protobuf.MysqlxNotice.Warning;
import com.toedter.calendar.JDateChooser;

import exceptions.DataInvalidException;
import exceptions.DateInvalidException;
import misc.PharmacyDb;
import misc.Validation;

public class UpdateDrug extends JFrame implements ActionListener {

	JLabel drugBarcodeLabel;
	JLabel quantityLabel;
	JLabel drugCostPriceLabel;
	JLabel drugSalePriceLabel;
	JLabel companyNameLabel;
	JLabel supplierContactLabel;
	JLabel mfgLabel;
	JLabel expiryLabel;

	JTextField drugBarcode;
	JTextField quantity;
	JTextField drugCostPrice;
	JTextField drugSalePrice;
	JTextField companyName;
	JTextField supplierContact;
	JDateChooser mfg;
	JDateChooser expiry;

	JButton reset;
	JButton update;

	JPanel panel;

	Connection con;

	public UpdateDrug() {
		
		 ImageIcon icon=new ImageIcon("image2.jpg");
	   		JLabel label=new JLabel(icon);
	   		Dimension size=label.getPreferredSize();
	   		label.setBounds(0, 0, size.width, size.height);

		/************* setting frame Behaviour******************************** */

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

		drugBarcodeLabel = new JLabel("Drug Barcode:");
		quantityLabel = new JLabel("Quantity:");
		drugCostPriceLabel = new JLabel("Cost Price:");
		drugSalePriceLabel = new JLabel("Sale Price:");
		companyNameLabel = new JLabel("Company Name:");
		supplierContactLabel = new JLabel("Supplier Contact:");
		mfgLabel = new JLabel("MFG");
		expiryLabel = new JLabel("EXP");

		/********** creating object Text Field ******************* */

		drugBarcode = new JTextField();
		quantity = new JTextField();
		drugCostPrice = new JTextField();
		drugSalePrice = new JTextField();
		companyName = new JTextField();
		supplierContact = new JTextField();
		mfg = new JDateChooser();
		expiry = new JDateChooser();

		/******** creating object Button**************** */

		reset = new JButton("RESET");
		update = new JButton("UPDATE");

		/******** Setting Component Location******* */

		drugBarcodeLabel.setBounds(75, 25, 100, 50);
		quantityLabel.setBounds(75, 100, 100, 50);
		drugCostPriceLabel.setBounds(75, 175, 100, 50);
		drugSalePriceLabel.setBounds(75, 250, 100, 50);

		companyNameLabel.setBounds(75, 325, 100, 50);
		companyName.setBounds(200, 340, 175, 20);

		drugBarcode.setBounds(200, 40, 175, 20);
		quantity.setBounds(200, 120, 175, 20);
		drugCostPrice.setBounds(200, 190, 175, 20);
		drugSalePrice.setBounds(200, 270, 175, 20);

		supplierContactLabel.setBounds(500, 25, 100, 50);
		supplierContact.setBounds(630, 40, 175, 20);

		mfgLabel.setBounds(520, 70, 100, 50);
		mfg.setBounds(630, 85, 175, 20);

		expiryLabel.setBounds(520, 115, 100, 50);
		expiry.setBounds(630, 130, 175, 20);

		update.setBounds(570, 250, 100, 40);
		reset.setBounds(700, 250, 100, 40);

		/********* Add component to frame********* */
		panel.add(drugBarcodeLabel);
		panel.add(quantityLabel);

		panel.add(companyNameLabel);

		panel.add(drugCostPriceLabel);
		panel.add(drugSalePriceLabel);

		panel.add(supplierContactLabel);

		panel.add(drugBarcode);
		panel.add(quantity);
		panel.add(drugCostPrice);
		panel.add(drugSalePrice);

		panel.add(companyName);
		panel.add(supplierContact);

		panel.add(mfgLabel);
		panel.add(mfg);
		panel.add(expiryLabel);
		panel.add(expiry);

		panel.add(update);
		panel.add(reset);

		reset.addActionListener(this);
		update.addActionListener(this);
		addWindowListener(new Validation());

		panel.add(label);
		c.add(panel);

		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == update) {
			con = PharmacyDb.getConnection();
			try {

				/* ********************** Validation *************************** */
				Validation.isNumberValid(quantity.getText());
				Validation.isPhoneValid(supplierContact.getText());
				Validation.isPriceValid(drugCostPrice.getText()," Cost Price");
				Validation.isPriceValid(drugSalePrice.getText()," Sale Price");
				Validation.isMfgValid(mfg.getDate());
				Validation.isExpiryValid(expiry.getDate());
				Validation.characterStringValid(companyName.getText(),"companyName");

				getData();

			} catch (SQLException e1) {
				e1.printStackTrace();
			} catch (DataInvalidException e2) {
				JOptionPane.showMessageDialog(null, e2.getMessage());
			} catch (DateInvalidException e3) {
				JOptionPane.showMessageDialog(null, e3.getMessage());
			}
		}

		else if (ae.getSource() == reset) {

			reset();
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

	private void getData() throws SQLException {
		String updateQuery = "update medicine set drugCostPrice=?,drugSalePrice=?,supplierContact=?,drugQuantity=?,mfg=?,EXP=?,companyNames=? where drugBarcode='"
				+ drugBarcode.getText() + "'";
		Date md = new Date(mfg.getDate().getTime());
		Date exp = new Date(expiry.getDate().getTime());

		PreparedStatement ps = con.prepareStatement(updateQuery);
		ps.setFloat(1, Float.parseFloat(drugCostPrice.getText()));
		ps.setFloat(2, Float.parseFloat(drugSalePrice.getText()));
		ps.setString(3, supplierContact.getText());
		ps.setInt(4, Integer.parseInt(quantity.getText()));
		ps.setDate(5, md);
		ps.setDate(6, exp);
		ps.setString(7, companyName.getText());
		int result = ps.executeUpdate();
		if (result != 0) {
			JOptionPane.showMessageDialog(null, "Medicine updated successfully");
			reset();
		} else {
			JOptionPane.showMessageDialog(null, "Barcode is Wrong", "", JOptionPane.WARNING_MESSAGE);
		}
		con.close();

	}

	private void reset() {
		drugBarcode.setText("");
		quantity.setText("");
		drugCostPrice.setText("");
		drugSalePrice.setText("");
		companyName.setText("");
		supplierContact.setText("");
		mfg.setDate(null);
		expiry.setDate(null);
	}

}
