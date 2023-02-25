package misc;

public class TempBill {
		
public String name;
 public Float price;
 public int qty; 
 public String barCode;
 
 public TempBill(String barCode,String name, String price, String qty) {
	 this.barCode=barCode;
	 this.name=name;
	 this.price=Float.parseFloat(price);
	 this.qty=Integer.parseInt(qty);
 }

@Override
public String toString() {
	
	return "["+this.barCode+" "+this.name+" "+this.price+" "+this.qty+"]";
}
 
 
}
