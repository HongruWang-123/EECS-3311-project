package backend;

import java.util.ArrayList;

public class AdminForCustomer {
	
	private MaintainCustomers maintain = new MaintainCustomers();

	public AdminForCustomer() {
		
		try {
			maintain.load("src\\users.csv");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public MaintainCustomers getMaintain() {
		return maintain;
	}
	public boolean add(String fname,String lname,String email,String password,String cardno,String security,String license) throws Exception {
		String nid = email;
		
		ArrayList<Customer> Customers = maintain.getCustomers();
		for(Customer a : maintain.getCustomers()) {
			if(a.getEmail().equals(nid)) {
				System.out.println("the Customer already exists in the system");
				return false;
			}
		}
		
		Customers.add(new Customer(fname,lname,email,password,cardno,security,license));
		maintain.update("src\\users.csv");
		return true;
	}
	
	public Customer login(String email,String password) {
        String nid = email;
		ArrayList<Customer> Customers = maintain.getCustomers();
		for(Customer a : maintain.getCustomers()) {
			if(a.getEmail().equals(nid) && a.getPassword().equals(password)) {
				return a;
			}
		}
		System.out.println("Invalid email or password");
		return null;
		
	}

	public void remove(String email) throws Exception {
		
		ArrayList<Customer> Customers = maintain.getCustomers();
		for(Customer a : Customers) {
			if(a.getEmail().equals(email)) {
				Customers.remove(a);
				maintain.update("src\\users.csv");
				return;
			}
		}
		System.out.println("the email input does not exist in the system");
	}

}
