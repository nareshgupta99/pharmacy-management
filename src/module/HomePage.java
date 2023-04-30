package module;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.UIManager;

import misc.Security;

public class HomePage implements ActionListener {
	static JFrame jf;
	JMenuBar mbar;
	JMenu Drug, Drugdet, Pur, Sal, MUser, Rep;
	JMenuItem addMedicine, searchMedicine, deleteMedicine, update, expiredDrug, avilableDrug, supplierList, sales,
			addUser, deleteUser, salesReport, medicineList, userList;
	JLabel background;
	JButton changePasswordButton, logoutButton, resetPasswordButton;
	Container c;

	public HomePage() {

		/*
		 * ************************************Creating Object *************************
		 */

		jf = new JFrame("Pharmacy Management");
		mbar = new JMenuBar();
		Font f = new Font("Arial", Font.BOLD, 20);
		Drug = new JMenu("ManageDrug  ");
		Drugdet = new JMenu("DrugDetails  ");
		Pur = new JMenu("Supplier ");
		Drugdet.setFont(f);
		addMedicine = new JMenuItem("Adding Drug");
		searchMedicine = new JMenuItem("Search Drug");
		deleteMedicine = new JMenuItem("Delete Drug");
		background = new JLabel("", new ImageIcon(getClass().getClassLoader().getResource("PHARMACYIMAGE3.png")), JLabel.CENTER);
		Sal = new JMenu("Sales  ");
		MUser = new JMenu("ManageUsers  ");
		Rep = new JMenu("Report  ");
		logoutButton = new JButton("Log Out");
		resetPasswordButton = new JButton("Reset Password");
		changePasswordButton = new JButton("Change Password");
		medicineList = new JMenuItem("Medicine List");
		update = new JMenuItem("Update ");
		expiredDrug = new JMenuItem("Expired Drug");
		avilableDrug = new JMenuItem("Drug Availability");
		supplierList = new JMenuItem("List of Suppliers");
		sales = new JMenuItem("sale");
		addUser = new JMenuItem("Add User");
		deleteUser = new JMenuItem("Delete User");
		salesReport = new JMenuItem("Sales Report");
		userList = new JMenuItem("Users Detail");

		c = jf.getContentPane();

		jf.setLayout(null);
		jf.setSize(1000, 650);
		jf.setResizable(false);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setLocationRelativeTo(null);
		background.setBounds(0, 0, 1000, 650);

		jf.add(background);

		UIManager.put("MenuBar.background", Color.CYAN);
		jf.setJMenuBar(mbar);

		Drug.setFont(f);
		Drug.setForeground(Color.WHITE);

		Drugdet.setForeground(Color.WHITE);

		Pur.setFont(f);
		Pur.setForeground(Color.WHITE);

		Sal.setFont(f);
		Sal.setForeground(Color.WHITE);

		MUser.setFont(f);
		MUser.setForeground(Color.WHITE);

		Rep.setFont(f);
		Rep.setForeground(Color.WHITE);

		changePasswordButton.setFont(f);
		changePasswordButton.setBounds(80, 150, 250, 50);
		changePasswordButton.addActionListener(this);

		logoutButton.setFont(f);
		logoutButton.setBounds(80, 210, 250, 50);
		logoutButton.addActionListener(this);

		resetPasswordButton.setBounds(80, 270, 250, 50);
		resetPasswordButton.setFont(f);
		resetPasswordButton.addActionListener(this);

		addMedicine.addActionListener(this);
		searchMedicine.addActionListener(this);
		Drug.add(searchMedicine);
		medicineList.addActionListener(this);
		deleteMedicine.addActionListener(this);

		update.addActionListener(this);
		expiredDrug.addActionListener(this);
//		Drugdet.add(update);
		Drugdet.add(expiredDrug);
		Drugdet.add(avilableDrug);
		Drugdet.add(medicineList);
		avilableDrug.addActionListener(this);

		supplierList.addActionListener(this);
		Pur.add(supplierList);

		sales.addActionListener(this);

		Sal.add(sales);

		deleteUser.addActionListener(this);

		salesReport.addActionListener(this);
		Rep.add(salesReport);

		userList.addActionListener(this);
		mbar.add(Drug);
		mbar.add(Drugdet);
		mbar.add(Pur);
		mbar.add(Sal);
		mbar.add(Rep);

		c.add(changePasswordButton);
		c.add(logoutButton);

		addUser.addActionListener(this);
		checkRole();
		jf.validate();
		jf.setVisible(true);

	}

	public void actionPerformed(ActionEvent ae) {

		if (ae.getSource() == changePasswordButton) {
			ChangePassword c = new ChangePassword();
		} else if (ae.getSource() == logoutButton) {
			Login.splashVisible = false;
			Login.progessValue = 75;
			Login n = new Login();
			jf.setVisible(false);
			jf.dispose();
		} else if (ae.getSource() == resetPasswordButton) {
			ResetPassword rp = new ResetPassword();
		} else if (ae.getSource() == addMedicine) {
			AddMedicine am = new AddMedicine();
		}

		else if (ae.getSource() == searchMedicine) {
			SearchMedicine sm = new SearchMedicine("Search Drug");
		}

		else if (ae.getSource() == sales) {
			Sales sale = new Sales();
		}

		else if (ae.getSource() == update) {
			UpdateDrug update = new UpdateDrug();
		}

		else if (ae.getSource() == expiredDrug) {
			ExpiredDrug update = new ExpiredDrug();
		} else if (ae.getSource() == addUser) {

			AddUser addUser = new AddUser();
		} else if (ae.getSource() == deleteMedicine) {
			DeleteDrug dm = new DeleteDrug();
		} else if (ae.getSource() == avilableDrug) {
			AvilabelDrug ad = new AvilabelDrug();
		} else if (ae.getSource() == deleteUser) {
			DeleteUser ad = new DeleteUser();
		} else if (ae.getSource() == medicineList) {
			MedicineList ad = new MedicineList();
		} else if (ae.getSource() == supplierList) {
			SupplierList s = new SupplierList();
		} else if (ae.getSource() == salesReport) {
			SalesReport salesReport = new SalesReport();
		} else if (ae.getSource() == userList) {
			 new UserList();
		}
	}

	public static void setHomePageActive(boolean b) {

		jf.setEnabled(b);
	}

	private void checkRole() {
		if (Security.getRole().equalsIgnoreCase("admin")) {
			Drug.add(addMedicine);
			Drug.add(deleteMedicine);
			Drug.add(update);
			MUser.add(deleteUser);
			MUser.add(addUser);
			MUser.add(userList);
			mbar.add(MUser);
			c.add(resetPasswordButton);
		}
	}

}
