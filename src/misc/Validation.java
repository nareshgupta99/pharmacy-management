package misc;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Date;

import javax.swing.JOptionPane;

import exceptions.DataInvalidException;
import exceptions.DateInvalidException;
import module.HomePage;

public class Validation implements WindowListener {
	
	public static void isExpiryValid(java.util.Date date) throws DateInvalidException  {
		
		if(date==null) {
			throw new DateInvalidException("expiry date is Not Enterd");
		}
		
		
		else if(date.getYear()==(new java.util.Date().getYear()) && date.getDay()==(new java.util.Date().getDay() )) {
			JOptionPane.showMessageDialog(null," Warning! expiry date is same as manufactured date");
		}
		
		else if(date.before(new java.util.Date())) {
		 throw new DateInvalidException("expiry date is before as manufactured date");
	 }
	 
	
	
	}

	
	public static void isMfgValid(java.util.Date date) throws DateInvalidException  {
		if(date==null) {
			throw new DateInvalidException("manufactured date is Not Enterd");
		}
		
		else if(date.after(new java.util.Date())) {
			 throw new DateInvalidException("manufacture date is after as expiry date");
		 }
		
		
		
	}
	
	public static void isPhoneValid(String msg) throws DataInvalidException,NullPointerException {
		
		if((msg.length()!=10)) {
			throw new DataInvalidException("Contact Number Not Valid");
		}else {
			
			char a[]=msg.toCharArray();
			for(char t:a) {
				if(!(t>='0' && t<='9')) {
					throw new DataInvalidException("contact number only numeric type ");
				}
				
			}
		}
			
	}

	public static void isPriceValid(String msg) throws DataInvalidException {
		char a[]=msg.toCharArray();
		for(char t:a) {
			if(!(t>='0' && t<='9' || t=='.')) 
				throw new DataInvalidException("price can only numeric type");
		}
	}
	

	
	public static void isNumberValid(String msg) throws DataInvalidException {
		if(msg==null) {
			throw new DataInvalidException("Quantity field cannot be null ");
		}
		char a[]=msg.toCharArray();
		for(char t:a) {
			if(!(t>='0' && t<='9')) {
				throw new DataInvalidException("Only numeric type Value Allowed ");
			}
		}
		
	}
	
	public static void characterStringValid(String msg) throws DataInvalidException {
		char a[]=msg.toCharArray();
		for(char t:a) {
			if(!(t>='a' && t<='z' || t>='A' && t<='Z')) {
				throw new DataInvalidException("Only Alphabate character are allowed");
			}
		}

	}
	
	public static void isdobValid(Date date) throws DateInvalidException {
		if(date==null) {
			throw new DateInvalidException("dob is Not Enterd");
		}
		
		else if(date.after(new java.util.Date())) {
			 throw new DateInvalidException("dob is Invalid");
		 }
		
		
	}
	
	@Override
	public void windowOpened(WindowEvent e) {
		HomePage.setHomePageActive(false);
	}

	@Override
	public void windowClosing(WindowEvent e) {
	}

	@Override
	public void windowClosed(WindowEvent e) {
		HomePage.setHomePageActive(true);
	}

	@Override
	public void windowIconified(WindowEvent e) {
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
	}

	@Override
	public void windowActivated(WindowEvent e) {
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
	}



}
