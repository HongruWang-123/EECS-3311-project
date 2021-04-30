package backend;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AdminForRequest {
	private MaintainRequests maintain = new MaintainRequests();
	
	
	public AdminForRequest() throws Exception {
		maintain.load("src\\requests.csv");
	}
	
	public boolean add(String start,String end,Customer c,ParkingSpace p) throws Exception {
		this.view();
		ArrayList<Booking> requests = maintain.getRequests();
		Map<Timestamp,Timestamp> bookedtimes = new HashMap<Timestamp,Timestamp>();
		
		for(Booking b : requests) {
			if(p.getNumber().equals(b.getSpace().getNumber())) {
				bookedtimes.put(b.getStart(), b.getEnd());
			}
		}
		ArrayList<Booking> bfc = this.view(c);
		
		if(bfc.size() >= 3) {
			System.out.println("Already meet the booking limit");
			return false;
		}
		if(this.addnewtime(start, end, bookedtimes)) {
			requests.add(new Booking(start,end,p,c,"false","false", "false"));
			maintain.update("src\\requests.csv");
			return true;
		}
		return false;
	}
	
	public boolean granted(String id) throws Exception {
		this.view();
		ArrayList<Booking> requests = maintain.getRequests();
		for(Booking a : requests) {
			if(a.getId().equals(id)) {
				if(!a.expires() && !a.isPay() && !a.isGranted()) {
					a.setGranted(true);
					maintain.update("src\\requests.csv");
					return true;
				}
				else {
					return false;
				}
			}
		}
		System.out.println("the id is invalid");
		return false;
	}

	public boolean remove(String id) throws Exception {
		this.view();
		ArrayList<Booking> requests = maintain.getRequests();
		for(Booking a : requests) {
			if(a.getId().equals(id)) {
				if(!a.expires() && !a.isPay() && !a.isGranted()) {
					requests.remove(a);
					maintain.update("src\\requests.csv");
					return true;
				}
				else {
					System.out.println("This booking is no longer a request");
					return false;
				}
			}
		}
		System.out.println("the id is invalid");
		return false;
	}
	
	public double pay(String cardno, String securityno, String id){
        this.view();
        double cost = 0; 
        boolean flag1 = false;
        boolean flag2 = false;
        AdminForCustomer adc = new AdminForCustomer();
        for(Customer c : adc.getMaintain().getCustomers()) {
            if(c.getCardno().equals(cardno) && c.getSecurityno().equals(securityno)) {
                flag1 = true;
                for(Booking b : maintain.getRequests()) {
                    if(b.getId().equals(id) && b.isGranted() && !b.isPay()) {
                        cost = b.calculate();
                        b.setPay(true);
                        flag2 = true;
                        try {
                            maintain.update("src\\requests.csv");
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        break;
                    }
                }
            }
        }
        if(!flag1) {
            System.out.println("invalid payment info");
        }
        if(!flag2) {
            System.out.println("invalid id");
        }
        return cost;
	}
	
	
	
	public double getCost(String id)
	{
		this.view();
		for(Booking b: maintain.getRequests()){
			if(b.getId().equals(id)){
				return b.calculate();
			}
		}
		
		System.out.println("Invalid Id");
		return -1.0;
	}
	
	public ArrayList<Booking> view(Customer c){
		this.view();
		ArrayList<Booking> bfc = new ArrayList<Booking>();
	    for(Booking b : this.view()) {
	    	if(b.getCustomer().equals(c)) {
	    		bfc.add(b);
	    	}
	    }
	    return bfc;
	}
	
	public ArrayList<Booking> view(ParkingSpace p){
		this.view();
		ArrayList<Booking> bfc = new ArrayList<Booking>();
	    for(Booking b : this.view()) {
	    	if(b.getSpace().equals(p)) {
	    		bfc.add(b);
	    	}
	    }
	    return bfc;
	}
	
	public double pay(String id){
		this.view();
		double cost = 0;
	    for(Booking b : maintain.getRequests()) {
	    	if(b.getId().equals(id)) {
	    		cost = b.calculate();
	    	}
	    }
	    return cost;
	}
	
	public ArrayList<Booking> view(){
		ArrayList<Booking> nonexpires = maintain.getRequests();
	    for(Booking b : maintain.getRequests()) {
	    	if(b.expires()) {
	    		nonexpires.remove(b);
	    	}
	    	try {
	    	//	System.out.println(b.getPaymentstatus()+" here");
	    		maintain.update("src\\requests.csv");
				
			} catch (Exception e) {
				e.printStackTrace();
			}
	    }
	    return nonexpires;
	}
	
	public boolean addnewtime(String start,String end,Map<Timestamp,Timestamp>bookedTimes) {
	Timestamp starttime = Timestamp.valueOf(start);
	Timestamp endtime = Timestamp.valueOf(end);
	if(bookedTimes.containsKey(starttime) && bookedTimes.get(starttime).equals(endtime)) {
    	System.out.println("your input start time and end time already exists");
    	return false;
    }    
	if(isOccupied(start,end,bookedTimes)) {
		System.out.println("occupied");
		return false;
	}
	return true;
}


public boolean isOccupied(String s,String e, Map<Timestamp,Timestamp> bookedTimes) 
{
  Timestamp newStart = Timestamp.valueOf(s);
  Timestamp newEnd = Timestamp.valueOf(e);

  boolean isOccupied = false;
  if(!isGood(newStart,newEnd) || expires(newEnd))
  {
      System.out.println("invalid time");//put exception here
      System.out.println("here");
      return true;
  }

  for(Timestamp t : bookedTimes.keySet())
  {
      Timestamp end = bookedTimes.get(t);
      Timestamp start = t;

      if(newEnd.before(start) || newStart.after(end)) 
      {
          isOccupied = false;//put different exception here too
      }
      else {
    	  return true;
      }

  }
  return isOccupied;
}

public boolean isGood(Timestamp s,Timestamp e) {
return s.after(new Date()) && s.before(e);
}

public boolean expires(Timestamp e) {
	return e.before(new Date());
}



}
