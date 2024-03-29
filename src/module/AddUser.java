package module;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import com.toedter.calendar.JDateChooser;

import exceptions.DataInvalidException;
import exceptions.DateInvalidException;
import misc.PharmacyDb;
import misc.Validation;

public class AddUser extends JFrame implements ActionListener
{
	Container c;
	JLabel userNameLabel,dobLabel,addressLabel,phoneLabel,salaryLabel,genderLabel,passwordLabel,userTypeLabel,heading;
	JTextField userName,address,phone,salary;
	JPasswordField password;
	JButton add,reset;
	Font ft1;
	JDateChooser dob;
	 Connection con;
	 JComboBox role;
	 JRadioButton male,female;
	 ButtonGroup buttonGroup;
	 String gender;
         

       public AddUser()
        {
			ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("image3.jpg"));
			JLabel label = new JLabel(icon);
			Dimension size = label.getPreferredSize();
			label.setBounds(0, 0, size.width, size.height);
	    	   con=PharmacyDb.getConnection();
	 
            setTitle("Add User");
            setSize(940,520);
	    setResizable(false);
	    setLocationRelativeTo(null);
	    setLayout(null);
	    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	  
         
           c=getContentPane();

           ft1=new Font("Verdana",Font.BOLD,18);
	
           heading=new JLabel("ADD USER");
           heading.setBounds(400,10,300,50);
           Font ft2=new Font("Verdana",Font.BOLD,25);
           heading.setFont(ft2);

      
          
           userNameLabel=new JLabel("User Name");
           userNameLabel.setFont(ft1);
           userNameLabel.setBounds(25,85,120,30);
 
 
        userName=new JTextField();
        userName .setFont(ft1);
        userName.setBounds(190,90,210,25);


        dobLabel=new JLabel("DOB");
        dobLabel.setFont(ft1);
        dobLabel.setBounds(25,140,120,30);
      
       dob=new JDateChooser();
       dob.setBounds(190,145,210,25);
         
      
       addressLabel =new JLabel("Address");
       addressLabel .setFont(ft1);
       addressLabel.setBounds(25,195,120,30);
      
       
       address=new JTextField();
       address.setFont(ft1);
       address.setBounds(190,200,210,25);
 
 
       phoneLabel=new JLabel("Phone");
       phoneLabel.setFont(ft1);
       phoneLabel.setBounds(25,250,120,30);
       
       
       phone=new JTextField();
       phone.setFont(ft1);
       phone.setBounds(190,255,210,25);
        
        
       salaryLabel=new JLabel("Salary");
       salaryLabel.setFont(ft1);
       salaryLabel.setBounds(25,305,120,30);
        

       salary=new JTextField();
       salary.setFont(ft1);
       salary.setBounds(190,310,210,25);
        

        
       genderLabel=new JLabel("Gender");
       genderLabel.setFont(ft1);
     
       genderLabel.setBounds(25,360,120,30);

       male=new JRadioButton(" Male");    
       female=new JRadioButton(" Female"); 
       male.setFont(ft1);
      female.setFont(ft1);
       male.setBounds(190,365,100,30);
       female.setBounds(300,365,120,30);
       
        buttonGroup=new ButtonGroup();
       buttonGroup.add(male);
       buttonGroup.add(female);
       

       passwordLabel=new JLabel("Password");
       passwordLabel.setFont(ft1);
       passwordLabel.setBounds(25,425,165,30);
       
 
       password=new JPasswordField();
       password.setFont(ft1);
       password.setBounds(190,430,210,25);
        



         
       userTypeLabel=new JLabel("User Type");
       userTypeLabel.setFont(ft1);
       userTypeLabel.setBounds(460,80,120,30);
        

          String []values={"user"}; 
             role=new JComboBox(values); 
            role.setBounds(610,85,210,25);
            role.setFont(ft1);
            role.setBackground(Color.WHITE);
        
        

        


           add=new JButton("ADD");
           add.setBounds(480,220,100,50);
           add.setFont(ft1);
           add.addActionListener(this);

        reset=new JButton("RESET");
        reset.setBounds(630,220,100,50);
        reset.setFont(ft1);
        reset.addActionListener(this);
       
          
       
     
         

 

         
        c.add(userNameLabel);
         c.add(dobLabel);
         c.add(addressLabel);
          c.add(phoneLabel);
         c.add(salaryLabel);
          c.add(genderLabel);
          c.add(passwordLabel);
           c.add(userTypeLabel);
         c.add(heading);
         c.add(userName);
         c.add(dob);
          c.add(address);
          c.add(phone);
          c.add(salary);
          c.add(male);
          c.add(female);
           c.add(password);
           c.add(role);
           c.add(add);
            c.add(reset);
            c.add(label);
            
            addWindowListener(new Validation());
        setVisible(true);
        
    



}

     public void actionPerformed(ActionEvent ae)
     {
    	 if(male.isSelected()) {
    		 gender=male.getText();
    	 }
    	  if(female.isSelected()) {
    		 gender=female.getText();
    	 }
    	    if(add==ae.getSource())
               {
      			
            	  String query="insert into user(user_name,Dob,Address,phone,salary,Gender,password,role)values(?,?,?,?,?,?,?,?)";
            	  PreparedStatement ps;
				try {
					ps=con.prepareStatement("Select * from user where user_name='"+userName.getText()+"'");
					ResultSet rs=ps.executeQuery();
					if(rs.next()) {
						throw new DataInvalidException("Duplicate User Name :User is Already Present");
					}
					Validation.isUserValid(userName.getText(), "User Name");
					Validation.isdobValid(dob.getDate());
					Validation.characterStringValid(address.getText(), " Address");
					Validation.isPhoneValid(phone.getText());
					Validation.isPriceValid(salary.getText()," salary");
					Validation.checkPassword(password.getText());
					 Date db=new  Date(dob.getDate().getTime());
					ps = con.prepareStatement(query);
					ps.setString(1, userName.getText());
					ps.setDate(2,  db);
					ps.setString(3, address.getText());
					ps.setString(4,phone.getText());
					ps.setString(5,salary.getText());
					ps.setString(6,gender);
					ps.setString(7,password.getText());
					ps.setString(8, role.getSelectedItem().toString());
					ps.executeUpdate();
					JOptionPane.showMessageDialog(null,"User added successfully");
					reset();
				}  
				catch(DataInvalidException e2) {
		        	 JOptionPane.showMessageDialog(null,e2.getMessage()," warning",JOptionPane.WARNING_MESSAGE);
		         }
				catch(DateInvalidException e2) {
		        	 JOptionPane.showMessageDialog(null,e2.getMessage());
		         }
				catch (SQLException e) {
					e.printStackTrace();
				}
		        
            	  
               
               } 
        
             else if(reset==ae.getSource())
               {
            	reset();
            	}
    }
     
     private void reset() {
    	 userName.setText("");
    	 address.setText("");
    	 phone.setText("");
    	 salary.setText("");
	   password.setText("");
	    dob.setDate(null);
     }
     
    
}


