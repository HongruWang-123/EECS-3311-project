package backend; 

import java.util.ArrayList;

public class Administrator extends User {
	private MaintainOfficers maintain = new MaintainOfficers();
	private MaintainRequests maintainbooking = new MaintainRequests();

	public Administrator() throws Exception {
		maintain.load("src\\Officers.csv");
	}

	public boolean add(String fname,String lname,String email,String password) throws Exception {
		String nid = email;

		ArrayList<Officer> officers = maintain.getOfficers();
		for(Officer a : maintain.getOfficers()) {
			if(a.getId().equals(nid)) {
				System.out.println("the officer already exists in the system");
				return false;
			}
		}
		officers.add(new Officer(fname,lname,email,password));
		maintain.update("src\\Officers.csv");
		return true;
	}

	public boolean remove(String email) throws Exception {

		ArrayList<Officer> officers = maintain.getOfficers();
		for(Officer a : officers) {
			if(a.getEmail().equals(email)) {
				officers.remove(a);
				maintain.update("src\\Officers.csv");
				return true;
			}
		}
		System.out.println("the email input does not exist in the system");
		return false;
	}


	public Officer login(String email,String password) {
		String nid = email;
		//ArrayList<Officer> officers = maintain.getOfficers();
		for(Officer a : maintain.getOfficers()) {
			if(a.getEmail().equals(nid) && a.getPassword().equals(password)) {
				return a;
			}
		}
		System.out.println("Invalid email or password");
		return null;	
	}

	
	public boolean changePaymentStatus(String id) throws Exception {
	    AdminForRequest adr = new AdminForRequest();
	     for(Booking b : adr.view()) {
	            if(b.getId().equals(id) && b.isGranted() && b.isPay()) {
	            	b.setPaymentstatus(true);
	            	System.out.println(b.getPaymentstatus());
	                adr.view();
	                return true;
	            }
	        }
	     return false;
	}


}

