package backend; 
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

public class Customer extends User {
private String cardno;
private String securityno;
private int amount;
private String license;

public Customer(String fname, String lname, String email, String password,String cardno,String securityno,String license) {
	super(fname,lname,email,password);
	this.cardno = cardno;
	this.securityno = securityno;
	this.license = license;
} 
public Customer() {
	
}


public String getLicense() {
	return license;
}
public void setLicense(String license) {
	this.license = license;
}

public int getAmount() {
	return amount;
}

public void setAmount(int amount) {
	this.amount = amount;
}


public String getCardno() {
	return cardno;
}
public void setCardno(String cardno) {
	this.cardno = cardno;
}
public String getSecurityno() {
	return securityno;
}
public void setSecurityno(String securityno) {
	this.securityno = securityno;
}


public String toString() {
	return "name: " + fname + "," + lname + "\n" +
           "ID: " + email +  "\n" +
		   "cardno: " + cardno;
}


@Override
public boolean equals(Object o) {

    // If the object is compared with itself then return true  
    if (o == this) {
        return true;
    }

    /* Check if o is an instance of ParkingSpace or not
      "null instanceof [type]" also returns false */
    if (!(o instanceof Customer)) {
        return false;
    }
      
    // typecast o to Booking so that we can compare data members 
    Customer a = (Customer) o;
    
   
    // Compare the data members and return accordingly 
    return this.getEmail().equals(a.getEmail()) ;
}




}

	

