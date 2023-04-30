package module;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import exceptions.DataInvalidException;
import misc.PharmacyDb;
import misc.TempBill;
import misc.Validation;

public class Sales extends JFrame implements ActionListener {
	public static List<TempBill> temp;
	JLabel BarCode, Name, Type, Purpose, Price, Qty;
	JTextField BatchNoTF;
	static JTextField NameTF;
	static JTextField TypeTF;
	static JTextField PurposeTF;
	static JTextField PriceTF;
	JTextField QtyTF;
	JButton check, Add, Continue, Reset;
	JPanel f;
	Connection con;
	PreparedStatement st;
	int quantity;
	public static JFrame frame;
	String date;
	public Sales() {
		
		ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("sale.jpg"));
		JLabel label = new JLabel(icon);
		Dimension size = label.getPreferredSize();
		label.setBounds(0, 0, size.width, size.height);
		
		
		frame=this;
		java.time.LocalDate d=java.time.LocalDate.now();
		 date=d.toString();
		
		temp = new <TempBill>ArrayList();
		f = new JPanel();
		BarCode = new JLabel("Bar Code : ");
		Name = new JLabel("Name : ");
		Type = new JLabel("Type : ");
		Purpose = new JLabel("Purpose : ");
		Price = new JLabel("Price : ");
		Qty = new JLabel("Quantity : ");

		BarCode.setBounds(50, 50, 80, 30);
		Name.setBounds(50, 100, 80, 30);
		Type.setBounds(50, 150, 80, 30);
		Purpose.setBounds(50, 200, 80, 30);
		Price.setBounds(50, 250, 80, 30);
		Qty.setBounds(50, 300, 80, 30);

		add(BarCode);
		add(Name);
		add(Type);
		add(Purpose);
		add(Price);
		add(Qty);
//        ---------------------------------------------------
		BatchNoTF = new JTextField();
		NameTF = new JTextField();
		TypeTF = new JTextField();
		PurposeTF = new JTextField();
		PriceTF = new JTextField();
		QtyTF = new JTextField();

		NameTF.setEditable(false);
		TypeTF.setEditable(false);
		PurposeTF.setEditable(false);
		PriceTF.setEditable(false);
		NameTF.setBackground(Color.WHITE);
		TypeTF.setBackground(Color.WHITE);
		PurposeTF.setBackground(Color.WHITE);
		PriceTF.setBackground(Color.WHITE);

		BatchNoTF.setBounds(150, 58, 80, 20);
		NameTF.setBounds(150, 108, 150, 20);
		TypeTF.setBounds(150, 158, 80, 20);
		PurposeTF.setBounds(150, 208, 150, 20);
		PriceTF.setBounds(150, 258, 80, 20);
		QtyTF.setBounds(150, 308, 80, 20);

		add(BatchNoTF);
		add(NameTF);
		add(TypeTF);
		add(PurposeTF);
		add(PriceTF);
		add(QtyTF);

//--------------------------------------------------

		check = new JButton("Check");
		Add = new JButton("Add");
		Continue = new JButton("Continue to Bill");
		Reset = new JButton("Reset");

		check.setBounds(300, 58, 80, 20);
		Add.setBounds(300, 308, 80, 20);
		Continue.setBounds(100, 400, 150, 20);
		Reset.setBounds(270, 400, 100, 20);

		add(check);
		add(Add);
		add(Continue);
		add(Reset);
		add(label);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		Reset.addActionListener(this);
		check.addActionListener(this);
		Add.addActionListener(this);
		Continue.addActionListener(this);

		setLayout(null);
		setSize(600, 600);
		
		con = PharmacyDb.getConnection();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == Reset) {
			reset();
		} else if (ae.getSource() == check) {
			try {
				String id = BatchNoTF.getText();
				String query="select drugName,drugType,drugPurpose,drugSalePrice,drugQuantity from medicine where drugBarcode='" + id+ "' AND  EXP>='"+date+"'";
				st = con.prepareStatement(query);
				ResultSet resultSet = st.executeQuery();
				if(resultSet.next()) {
				NameTF.setText(resultSet.getString(1));
				TypeTF.setText(resultSet.getString(2));
				PurposeTF.setText(resultSet.getString(3));
				PriceTF.setText(resultSet.getString("drugSalePrice"));
				quantity=resultSet.getInt("drugQuantity");
				}else {
					JOptionPane.showMessageDialog(null,"No Record Found");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else if (ae.getSource() == Continue) {

			MainBill mainBill = new MainBill(temp);
			setSaleVisible(false);

		} else if (ae.getSource() == Add) {
			
			
				try {
					Validation.isNumberValid(QtyTF.getText());
					if(Integer.parseInt(QtyTF.getText()) ==0) {
						throw new DataInvalidException("Drug Quantity can not be 0 Availabel quantity is: "+quantity);
					}
					if(quantity<Integer.parseInt( QtyTF.getText())) {
						throw new DataInvalidException("Drug Quantity is less only"+quantity+"is Availabel");
					}
					TempBill x = new TempBill(BatchNoTF.getText(), NameTF.getText(), PriceTF.getText(), QtyTF.getText());
					temp.add(x);
					reset();
					JOptionPane.showMessageDialog(null, "Medicine has been Added For Bill");
				} catch (DataInvalidException e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
				}
			
				
		}
	}

	private void reset() {
		BatchNoTF.setText("");
		NameTF.setText("");
		PurposeTF.setText("");
		PriceTF.setText("");
		TypeTF.setText("");
		QtyTF.setText("");

	}
	
	public static void setSaleVisible(boolean b) {
		frame.setVisible(b);
	}
	}
