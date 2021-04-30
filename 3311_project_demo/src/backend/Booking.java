package backend; 
import java.sql.Timestamp;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;

public class Booking {
	
private boolean isConfirmed;
private ParkingSpace space;
private Duration duration;
private String id;
private Timestamp start;
private Timestamp end;
private Customer customer;
private double cost;
private Boolean granted;
private Boolean pay;
private Boolean paymentstatus;


public Booking(String start,String end,ParkingSpace s,Customer c,String granted,String pay, String paymentStatus) {
		 space = s;
		 isConfirmed = true; 
		 customer = c;
		 setId(c.getFname() + s.getNumber() + c.getEmail() + start);
		 this.start = Timestamp.valueOf(start);
		 this.end = Timestamp.valueOf(end);
		 duration = Duration.between(this.start.toLocalDateTime(), this.end.toLocalDateTime());
		 cost = this.calculate();
		 this.granted = Boolean.valueOf(granted);
		 this.pay = Boolean.valueOf(pay);
		 setPaymentstatus(Boolean.valueOf(paymentStatus));
}

public Boolean isGranted() {
	return granted;
}

public void setGranted(boolean granted) {
	this.granted = granted;
}

public Boolean isPay() {
	return pay;
}

public void setPay(boolean pay) {
	this.pay = pay;
}

public double getCost() {
	return this.calculate();
}

public void setCost(double cost) {
	this.cost = cost;
}
public boolean isConfirmed() {
	return isConfirmed;
}

public void setConfirmed(boolean isConfirmed) {
	this.isConfirmed = isConfirmed;
}

public ParkingSpace getSpace() {
	return space;
}

public void setSpace(ParkingSpace space) {
	this.space = space;
}

public String getId() {
	return id;
}

public void setId(String id) {
	this.id = id;
}

public Timestamp getStart() {
	return start;
}

public void setStart(Timestamp start) {
	this.start = start;
}

public Timestamp getEnd() {
	return end;
}

public void setEnd(Timestamp end) {
	this.end = end;
}

public void setDuration(Duration duration) {
	this.duration = duration;
}

public boolean addSpace(String s,String e,ParkingSpace space,String license) {
	
	boolean result = true;
	return result;
}


public Duration getDuration() {
	return duration;
}



public double calculate() {
	long mins = duration.toMinutes();
    double result = (double)mins/60*space.getHourlyprice();//*price;
    cost = Math.round(result*100.0)/100.0;
	return Math.round(result*100.0)/100.0;
}

public String toString()
{
    return space.getAddress()+" starts "+start+" ends "+end
    		+ "\n\tbooking id is "+id+"\n\t"+
    		"account is: "+customer.getEmail() + "\n\tis granted: " + granted + "\n\tis Paid: " + pay +
    		"\n\tPayment Status: "+ (this.getPaymentstatus()?"Settled":"Pending")+
    		"\n==========================================";
}


@Override
public boolean equals(Object o) {

    // If the object is compared with itself then return true  
    if (o == this) {
        return true;
    }

    /* Check if o is an instance of Booking or not
      "null instanceof [type]" also returns false */
    if (!(o instanceof Booking)) {
        return false;
    }
      
    // typecast o to Booking so that we can compare data members 
    Booking a = (Booking) o;
    
    Customer c = a.getCustomer();  
    // Compare the data members and return accordingly 
    return this.start.equals(a.getStart()) && this.end.equals(a.getEnd()) && this.space.equals(a.getSpace()) && c.equals(this.customer);
}

public Customer getCustomer() {
	return customer;
}

public void setCustomer(Customer customer) {
	this.customer = customer;
}

public boolean expires() {
	return  end.before(new Date());
}

public Boolean getPaymentstatus() {
	return paymentstatus;
}

public void setPaymentstatus(Boolean paymentstatus) {
	this.paymentstatus = paymentstatus;
}


}



