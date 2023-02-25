package module;

import misc.Validation;

public class DeleteDrug extends SearchMedicine  {

	String barcode;
	public DeleteDrug( ) {
		super("DELETE DRUG");
		deleteButton.setVisible(true);
		addWindowListener(new Validation());
	}
	
	}
