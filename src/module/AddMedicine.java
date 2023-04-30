package module;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.toedter.calendar.JDateChooser;

import exceptions.DataInvalidException;
import exceptions.DateInvalidException;
import misc.PharmacyDb;
import misc.Validation;

public class AddMedicine extends JFrame implements ActionListener{

    JLabel drugNameLabel;
    JLabel drugTypeLabel;
    JLabel supplierNameLabel;
    JLabel drugPurposeLabel;
    JLabel drugBarcodeLabel;
    JLabel drugCostPriceLabel;
    JLabel drugSalePriceLabel;
    JLabel drugQuantityLabel;
    JLabel companyNameLabel;
    JLabel supplierContactLabel;
    JLabel mfgLabel;
    JLabel expiryLabel;
    JLabel drugSupplierLabel;

    JTextField drugSupplier;
    JTextField drugName;
    JTextField drugType;
    JTextField supplierName;
    JTextField drugPurpose;
    JTextField drugBarcode;
    JTextField drugCostPrice;
    JTextField drugSalePrice;
    JTextField drugQuantity;
    JTextField companyName;
    JTextField supplierContact;
    
    JDateChooser mfg;
    JDateChooser expiry;
    JButton add;
    JButton reset;
    JPanel panel;
    String barcodeValue;
    
    Connection con;
    public AddMedicine() {
        /************* setting frame Behaviou******************************** */
       this.addWindowListener(new Validation());
    	setTitle("Add Medicine");
    	setBackground(Color.WHITE);    	
    	Dimension screenSize=getScreenSize();
    	setSize(screenSize.width-300,screenSize.height-300);
    	setResizable(false);
    	setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    	this.setLocationRelativeTo(null);
    	Container c = getContentPane();
    	c.setLayout(null);
    	
    	/************** panel property****************************** */ 
    	
        panel = new JPanel();
        panel.setLocation(50, 25);
        Dimension panelSize=getPanelSize(screenSize.width-300,screenSize.height-300);
        panel.setBackground(Color.WHITE);
        panel.setSize(panelSize);
        panel.setLayout(null);
        

        
        /**************** creating object  ***************** */
   		ImageIcon icon=new ImageIcon(getClass().getClassLoader().getResource("image2.jpg"));
   		JLabel label=new JLabel(icon);
        drugSupplierLabel=new JLabel("Drug Supplier:");
        drugNameLabel = new JLabel("Drug Name:");
        drugTypeLabel = new JLabel("Drug Type:");
        supplierNameLabel = new JLabel("Supplier Name:");
        drugPurposeLabel = new JLabel("Drug Purpose:");
        drugBarcodeLabel = new JLabel("Bar Code:");
        drugCostPriceLabel = new JLabel("Cost Price:");
        drugSalePriceLabel = new JLabel("Sale Price:");
        drugQuantityLabel = new JLabel("Drug Quantity:");
        companyNameLabel = new JLabel("Company Name:");
        supplierContactLabel = new JLabel("Supplier Contact:");
        mfgLabel = new JLabel("Mfg Date:");
        expiryLabel = new JLabel("Expiry Date:");
        drugSupplier=new JTextField();
        drugName = new JTextField();
        drugType = new JTextField();
        supplierName = new JTextField();
        drugBarcode = new JTextField();
        drugPurpose = new JTextField();
        drugCostPrice = new JTextField();
        drugSalePrice = new JTextField();
        drugQuantity = new JTextField();
        companyName = new JTextField();
        supplierContact = new JTextField();
        mfg =new JDateChooser() ;
        expiry = new JDateChooser();

        /******** creating object Button**************** */
        add = new JButton("ADD");
        reset = new JButton("RESET");
       
        /********Setting Component Location******* */
        
        
         drugNameLabel.setBounds(75, 25, 100, 50);
         drugTypeLabel.setBounds(75,75,100,50);
         supplierNameLabel.setBounds(75, 125, 100, 50);
         drugPurposeLabel.setBounds(75,175, 100, 50);
         drugBarcodeLabel.setBounds(75,225, 100, 50);
         drugCostPriceLabel.setBounds(75,275, 100, 50);
         drugSalePriceLabel.setBounds(75,325, 100, 50);
         
         drugQuantityLabel.setBounds(500,25, 100, 50);
         companyNameLabel.setBounds(500,75, 100, 50);
         supplierContactLabel.setBounds(500,125, 100, 50);
         mfgLabel.setBounds(500,175, 100, 50);
         expiryLabel.setBounds(500,225, 100, 50);

                
          drugName.setBounds(200, 40, 175, 20);
          drugType.setBounds(200, 90, 175, 20);
          supplierName.setBounds(200, 140, 175, 20);
          drugPurpose.setBounds(200, 190, 175, 20);
          drugBarcode.setBounds(200, 240, 175, 20);
          drugCostPrice.setBounds(200, 290, 175, 20);
          drugSalePrice.setBounds(200, 340, 175, 20);
          
          drugQuantity.setBounds(650, 40, 175, 20);
          companyName.setBounds(650, 90, 175, 20);
          supplierContact.setBounds(650, 140, 175, 20);
          mfg.setBounds(650, 190, 175, 20);
          expiry.setBounds(650, 240, 175, 20);
    
        add.setBounds(500, 300,	100, 40);
        reset.setBounds(650, 300,	100, 40);
        
        

        /********* Add component to frame********* */
         panel.add(drugNameLabel);
         panel.add(drugTypeLabel);
         panel.add(supplierNameLabel);
        panel.add(drugPurposeLabel);
        panel.add(companyNameLabel);
        panel.add(drugBarcodeLabel);
        panel.add(drugCostPriceLabel);
        panel.add(drugSalePriceLabel);
        panel.add(drugQuantityLabel);
        panel.add(companyNameLabel);
        panel.add(supplierContactLabel);
        panel.add(mfgLabel);
        panel.add(expiryLabel);
        
        
        panel.add(drugName);
        panel.add(drugType);
        panel.add(supplierName);
        panel.add(drugPurpose);
        panel.add(drugBarcode);
        panel.add(drugCostPrice);
        panel.add(drugSalePrice);
        panel.add(drugQuantity);
        panel.add(companyName);
        panel.add(supplierContact);
        panel.add(mfg);
        panel.add(expiry);
        
        Dimension size=label.getPreferredSize();
        label.setBounds(0, 0, size.width, size.height);
        
        panel.add(add);
        panel.add(reset);
        
        add.addActionListener(this);
        reset.addActionListener(this);
        
        drugBarcode.setEditable(false);
        drugBarcode.setBackground(Color.WHITE);
        panel.add(label);
        c.add(panel);
        setVisible(true);
     
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    	if(e.getSource()== add) {
    		if(barcodeValue==null || barcodeValue.equals("")) {
    			try {
    				con=PharmacyDb.getConnection();
					PreparedStatement ps=con.prepareStatement("select * from medicine where drugName='"+drugName.getText().toLowerCase()+"'");
					ResultSet rs=ps.executeQuery();
					if(rs.next()) {
						throw new DataInvalidException("Medicine Already present go to update section and update it");
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}catch(DataInvalidException e2) {
					JOptionPane.showMessageDialog(null,e2.getMessage());
				}
    			if(!(drugName.getText().equals("")|| drugName==null) ){
    				barcodeValue=PharmacyDb.genrateBarcode(drugName.getText());
    				drugBarcode.setText(barcodeValue);
    			}
    		}
    		
    		
         try {
        	 
        	 /* ********************** Validation *************************** */
        	 Validation.isAlphaNumericValid(drugName.getText(),"drug Name");
        	 Validation.isPriceValid(drugCostPrice.getText(),"Cost Price");
        	 Validation.isPriceValid(drugSalePrice.getText(),"Sale Price");
        	 Validation.isNumberValid(drugQuantity.getText());
        	 Validation.characterStringValid(companyName.getText(),"company Name");
        	 Validation.characterStringValid(supplierName.getText(),"supplierName");
        	 Validation.isPhoneValid(supplierContact.getText());
        	 Validation.isMfgValid(mfg.getDate());
        	 Validation.isExpiryValid(expiry.getDate());
        	 
        	 addMedicineData();
        	 
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
         catch(DateInvalidException de) {
        	 JOptionPane.showMessageDialog(null,de.getMessage());
         }
         catch(DataInvalidException e2) {
        	 JOptionPane.showMessageDialog(null,e2.getMessage());
         }
         catch(Exception e3) {
        	 JOptionPane.showMessageDialog(null,e3.getMessage());
         }
    }
    	
    	else  if(e.getSource()== reset) {
    			resetField();
          }
 			 
        
    }
    
    private Dimension getScreenSize() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        
        return screenSize;
    }


	private  Dimension getPanelSize(int width,int height) {
		Dimension panelSize=new Dimension();
		panelSize.setSize(width-100, height-100);
		return panelSize;
		
	}
	
	private void resetField() {
		drugSupplier.setText("");
		  drugName.setText("");
		  drugType.setText("");
		  supplierName.setText("");
		  drugBarcode.setText("");
		  drugPurpose.setText("");
		  drugCostPrice.setText("");
		  drugSalePrice.setText("");
		   drugQuantity.setText("");
		    companyName.setText("");
		    supplierContact.setText("");
		    mfg.setDate(null);
		    expiry.setDate(null);

	}
	
	private void addMedicineData() throws SQLException {
		con=PharmacyDb.getConnection();
      String query="insert into medicine(drugName,drugBarcode,supplierName,drugCostPrice,drugPurpose,drugSalePrice,supplierContact,mfg,exp,drugQuantity,companyNames,drugType) values (?,?,?,?,?,?,?,?,?,?,?,?);";
	  	 Date md=new  Date(mfg.getDate().getTime());
    	 Date exp=new Date(expiry.getDate().getTime());
    	 
		PreparedStatement ps=con.prepareStatement(query);
		ps.setString(1, drugName.getText().toLowerCase());
		ps.setString(2, barcodeValue);
		ps.setString(3, supplierName.getText());
		ps.setFloat(4, Float.parseFloat(drugCostPrice.getText()));
		ps.setString(5, drugPurpose.getText());
		ps.setFloat(6,Float.parseFloat(drugSalePrice.getText()));
		ps.setString(7, supplierContact.getText());
		ps.setDate(8,  md );
		ps.setDate(9,  exp);
		ps.setInt(10,Integer.parseInt(drugQuantity.getText()));
		ps.setString(11, companyName.getText());
		ps.setString(12, drugType.getText());
		ps.executeUpdate();
		 JOptionPane.showMessageDialog(null,barcodeValue+" Medicine added successfully");
		 barcodeValue=null;
		con.close();
		resetField();
	}
	
}