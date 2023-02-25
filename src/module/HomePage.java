package module;

import java.awt.Color;
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

public class HomePage implements ActionListener
{
       static JFrame jf;
       JMenuBar mbar;
       JMenu Drug,Drugdet,Pur,Sal,MUser,Rep;
       JMenuItem addMedicine,searchMedicine,deleteMedicine,update,expiredDrug,avilableDrug,supplierList,sales,addUser,deleteUser,salesReport,medicineList;
       JLabel background; 
       JButton changePasswordButton,logoutButton; 
      private String role;

       public HomePage(String role )
       {
    	   this.role=role;
           jf=new JFrame("HOME PAGE");
           jf.setLayout(null);
           jf.setSize(1000,650);
            jf.setResizable(false);
           jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
          jf.setLocationRelativeTo(null);
           background=new JLabel("",new ImageIcon("PHARMACYIMAGE3.png"),JLabel.CENTER);
           background.setBounds(0,0,1000,650);
           jf.add(background);
                   
            mbar=new JMenuBar();
            UIManager.put("MenuBar.background",Color.CYAN);
            jf.setJMenuBar(mbar);
 

            Font f=new Font("Arial",Font.BOLD,20);
            
            Drug=new JMenu("ManageDrug  ");
            Drug.setFont(f);
            Drug.setForeground(Color.WHITE);
 
            Drugdet=new JMenu("DrugDetails  ");
            Drugdet.setFont(f);
            Drugdet.setForeground(Color.WHITE);
 
            Pur=new JMenu("Supplier ");
            Pur.setFont(f);
            Pur.setForeground(Color.WHITE);
         
            Sal=new JMenu("Sales  ");
            Sal.setFont(f);
            Sal.setForeground(Color.WHITE);

            MUser=new JMenu("ManageUsers  ");
            MUser.setFont(f);
            MUser.setForeground(Color.WHITE);

            Rep=new JMenu("Report  ");
            Rep.setFont(f);
            Rep.setForeground(Color.WHITE);

            changePasswordButton=new JButton("Change Password");
            changePasswordButton.setFont(f);
            changePasswordButton.setBounds(80,450,210,50);
//            changePasswordButton.setBackground(Color.BLUE); 
            changePasswordButton.addActionListener(this);

                       
            logoutButton=new JButton("Log Out");
            logoutButton.setFont(f);
            logoutButton.setBounds(370,450,150,50);
//            logoutButton.setBackground(Color.BLUE);              
            logoutButton.addActionListener(this);


            addMedicine=new JMenuItem("Adding Drug");
            addMedicine.addActionListener(this);
             searchMedicine=new JMenuItem("Search Drug");
             searchMedicine.addActionListener(this);
             deleteMedicine=new JMenuItem("Delete Drug");
             Drug.add(searchMedicine);
             medicineList=new JMenuItem("Medicine List");
             medicineList.addActionListener(this);
             deleteMedicine.addActionListener(this);

     
                      
             update=new JMenuItem("Update ");
             update.addActionListener(this);
             expiredDrug=new JMenuItem("Expired Drug");
             expiredDrug.addActionListener(this);
             avilableDrug=new JMenuItem("Drug Availability");
             Drugdet.add(update);
             Drugdet.add(expiredDrug);
             Drugdet.add(avilableDrug);
             Drugdet.add(medicineList);
             avilableDrug.addActionListener(this);

            
             supplierList=new JMenuItem("List of Suppliers");
             supplierList.addActionListener(this);
             Pur.add(supplierList);
             
           
             sales=new JMenuItem("sale");
              sales.addActionListener(this);
              
              Sal.add(sales);
              addUser=new JMenuItem("Add User");

             
             deleteUser=new JMenuItem("Delete User");
             deleteUser.addActionListener(this);

             
            
             salesReport=new JMenuItem("Sales Report");
             salesReport.addActionListener(this);
             Rep.add(salesReport);
             
             mbar.add(Drug);
             mbar.add(Drugdet);
             mbar.add(Pur);
             mbar.add(Sal);
             mbar.add(Rep);

             jf.add(changePasswordButton);
             jf.add(logoutButton);

             addUser.addActionListener(this);
             checkRole();
             jf.validate();
             jf.setVisible(true);
           
      }

       public void actionPerformed(ActionEvent ae)
       {

                 if(ae.getSource()==changePasswordButton)
                 {
                      ChangePassword c=new ChangePassword();
                 }
               else if(ae.getSource()==logoutButton)
                  {
            	   Login.splashVisible=false;
            	   Login.progessValue=75;
            	   Login n=new Login();
                      jf.setVisible(false);
                      jf.dispose();
                  }
                else if(ae.getSource()==addMedicine)
                  {
                       AddMedicine am=new AddMedicine();
                  }
                 
                else if(ae.getSource()==searchMedicine)
                {
                     SearchMedicine sm=new SearchMedicine("Search Drug");
                }
               
             
               else if(ae.getSource()==sales)
               {
                    Sales sale=new Sales();
               }
             
               else if(ae.getSource()==update)
               {
                    UpdateDrug update=new UpdateDrug();
               }
                 
               else if(ae.getSource()==expiredDrug)
               {
                   ExpiredDrug update=new ExpiredDrug();
               }
               else if(ae.getSource()==addUser)
               {
            	   
                   AddUser addUser=new AddUser();
               } 
               else if(ae.getSource()==deleteMedicine) {
            	   DeleteDrug dm=new DeleteDrug();
               }
               else if(ae.getSource()==avilableDrug) {
            	   AvilabelDrug ad=new AvilabelDrug();
               }
               else if(ae.getSource()==deleteUser) {
            	   DeleteUser ad=new DeleteUser();
               }
               else if(ae.getSource()==medicineList) {
            	   MedicineList ad=new MedicineList();
               }
               else if(ae.getSource()==supplierList) {
            	   SupplierList s=new SupplierList();
               }
               else if(ae.getSource()==salesReport) {
            	   SalesReport salesReport=new SalesReport();
               }
        }
       
       public  static void setHomePageActive(boolean b) {

			jf.setEnabled(b);
       }
       
       private void checkRole() {
    	   if(role.equalsIgnoreCase("admin")) {
    		   Drug.add(addMedicine);
    		   Drug.add(deleteMedicine); 
    		   MUser.add(deleteUser);
    		   MUser.add(addUser);
    		   mbar.add(MUser);
    	   }
       }
        
}
