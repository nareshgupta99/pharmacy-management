package pharmacy;

import misc.PharmacyDb;
import module.Login;

public class Pharmacy {
	
	public static void main(String []args) {
		PharmacyDb.isDbExist();
		PharmacyDb.createTable();
		PharmacyDb.createAdminUser();
		Login l=new Login();

	}

}
