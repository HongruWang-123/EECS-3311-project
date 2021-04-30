package backend;
import java.sql.Timestamp;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ParkingSpace {
private String address;	

//private Timestamp start;
//private Timestamp end;
//private Duration duration;
private double hourlyprice;
private String number;


private Map<Timestamp,Timestamp> bookedTimes;



public ParkingSpace(String address,String number,String hourly) {
	this.address = address;
	this.number = number;
	hourlyprice = Double.parseDouble(hourly);
	bookedTimes = new HashMap<Timestamp,Timestamp>();
	
}

//public boolean addnewtime(String start,String end) {
//	Timestamp starttime = Timestamp.valueOf(start);
//	Timestamp endtime = Timestamp.valueOf(end);
//	if(bookedTimes.containsKey(start) && bookedTimes.get(start).equals(end)) {
//    	System.out.println("your input start time and end time already exists");
//    	return false;
//    }    
//	if(isOccupied(start,end)) {
//		System.out.println("invalid time");
//		return false;
//	}
//	bookedTimes.put(starttime, endtime);
//	return true;
//}
//
//public boolean changeTime(String s, String e,String ns, String ne) {
//    Timestamp start = Timestamp.valueOf(s);
//    Timestamp end = Timestamp.valueOf(e);
//    
//    if(!(bookedTimes.containsKey(start) && bookedTimes.get(start).equals(end))) {
//    	System.out.println("your original start time and end time are not correct");
//    	return false;
//    }
//
//    if(!addnewtime(ns,ne)) {
//    	System.out.println("your change is invalid");
//    	bookedTimes.put(start, end);
//    	return false;
//    }
//    return true;
//}
//
//public boolean removeTime(String s,String e) {
//	Timestamp start = Timestamp.valueOf(s);
//    Timestamp end = Timestamp.valueOf(e);
//    if(!(bookedTimes.containsKey(start) && bookedTimes.get(start).equals(end))) {
//    	System.out.println("your input start time and end time are not valid");
//    	return false;
//    }    
//    bookedTimes.remove(start);
//    return true;
//}


public String getAddress() {
	return address;
}

public void setAddress(String address) {
	this.address = address;
}

//public boolean isOccupied(String s,String e) 
//{
//    Timestamp newStart = Timestamp.valueOf(s);
//    Timestamp newEnd = Timestamp.valueOf(e);
//
//    boolean isOccupied = true;
//
//    if(!isGood(newStart,newEnd) || expires(newEnd))
//    {
//        System.out.println("invalid time");//put exception here
//        return true;
//    }
//
//    for(Timestamp t : bookedTimes.keySet())
//    {
//        Timestamp end = bookedTimes.get(t);
//        Timestamp start = t;
//
//        if(newEnd.before(start) || newStart.after(end)) 
//        {
//            isOccupied = false;//put different exception here too
//            break;
//        }
//
//    }
//    return isOccupied;
//}
//
//
//
//public boolean isGood(Timestamp s,Timestamp e) {
//	return s.before(e);
//}




public Double getHourlyprice() {
	return hourlyprice;
}

public void setHourlyprice(double hourlyprice) {
	this.hourlyprice = hourlyprice;
}

public String toString() {
	
	return "address: " + address + " no: " + number +"\n" 
	       +"hourly price :" + hourlyprice;
}

public String getNumber() {
	return number;
}

public void setNumber(String number) {
	this.number = number;
}

@Override
public boolean equals(Object o) {

    // If the object is compared with itself then return true  
    if (o == this) {
        return true;
    }

    /* Check if o is an instance of ParkingSpace or not
      "null instanceof [type]" also returns false */
    if (!(o instanceof ParkingSpace)) {
        return false;
    }
      
    // typecast o to Booking so that we can compare data members 
    ParkingSpace a = (ParkingSpace) o;
    
   
    // Compare the data members and return accordingly 
    return this.getNumber().equals(a.getNumber()) ;
}


}
