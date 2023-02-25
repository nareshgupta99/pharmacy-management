package pharmacy;


import misc.PharmacyDb;
import module.Login;

public class Pharmacy {
	
	public static void main(String []args) {
		PharmacyDb.createTable();
		Login l=new Login();
	}

}
