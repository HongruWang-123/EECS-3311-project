package backend;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Example Junit test cases for testing ListDGraph.
 * @author wangs
 *
 */
public class TestForProject {
	AdminForCustomer customerDB;
	Administrator officerDB;
	AdminForParkingSpace psDB;
	AdminForRequest bookingDB;
	ArrayList<ParkingSpace> parkingSpaces;

	@Before
	public void setUp() throws Exception {
		customerDB = new AdminForCustomer();
		officerDB = new Administrator();
		psDB = new AdminForParkingSpace();
		bookingDB = new AdminForRequest();			
		//init the array lists
		parkingSpaces = psDB.getMaintain().getParkingSpaces();
	}

	// Test for REQ-4.1 Manage Parking Enforcement Officer 
	// Test for 4.1
	@Test
	public void test_1() throws Exception { 
		String fname = "Wen";
		String lname = "Li";
		String email = "Li55@my.yorku.ca";
		String password = "123456";
		boolean o1 = officerDB.add(fname,lname,email,password);
		boolean o2 = officerDB.add("Wen","Wang",email,password);
        Officer o3 = new Officer(fname,lname,email,password);
		Assert.assertEquals(o1,true);
		Assert.assertEquals(o2,false);
		assertTrue(o3.toString() != null);
	}

	// Test for 4.1
	@Test
	public void test_2() throws Exception { 
		String email = "Li55@my.yorku.ca";
		boolean o1 = officerDB.remove(email);

		Assert.assertEquals(o1,true);
	}

	// Test for REQ-4.2 Manage Parking Enforcement Officer 
	// Test for 4.2
	@Test
	public void test_3() throws Exception { 
		String fname = "Wen";
		String lname = "Li";
		String email = "Li66@my.yorku.ca";
		String password = "123456";
		String cardno = "123456";
		String secure = "123";
		String license = "123456";
		Customer c = new Customer("Wen","Li","Li66@my.yorku.ca","123456","123456", "123", "123456");
		customerDB.add(fname,lname,email,password,cardno,secure,license);
		assertTrue(customerDB.getMaintain().getCustomers().contains(c));
	}

	//Test for 4.2
	@Test
	public void test_5() throws Exception { 
		String email = "Li66@my.yorku.ca";
		Customer c = new Customer("Wen","Li","Li66@my.yorku.ca","123456","123456", "123", "123456");
		customerDB.remove(email);
		assertFalse(customerDB.getMaintain().getCustomers().contains(c));
	}

	//Test for 4.3	
	@Test
	public void test_6() throws Exception { 

		String email = "Li66@my.yorku.ca";
		String password = "123456";
		Customer c = customerDB.login(email,password);	
		assertTrue(c == null);
	}

	//Test for 4.3	
	@Test
	public void test_7() throws Exception { 

		String email = "Li123@my.yorku.ca";
		String password = "Liaa";
		Customer c1 = customerDB.login(email,password);	
		assertTrue(c1.getFname().equals("Wenxuan"));
		assertTrue(c1.getLname().equals("Li"));
		assertTrue(c1.getEmail().equals("Li123@my.yorku.ca"));
		assertTrue(c1.getCardno().equals("216403370"));
		assertTrue(c1.getSecurityno().equals("335"));
		assertTrue(c1.getLicense().equals("123456"));
		assertTrue(c1.getPassword().equals("Liaa"));
		assertTrue(c1.toString() != null);
	}

	//Test for 4.4	
	@Test
	public void test_8() throws Exception { 
		String email = "Li123@my.yorku.ca";
		String password = "Liaa";
		assertTrue(bookingDB.add("2022-04-30 07:00:30","2022-04-30 09:00:30" , customerDB.login(email,password), psDB.getMaintain().getParkingSpaces().get(2)));
		Booking b = new Booking("2022-04-30 07:00:30","2022-04-30 09:00:30",psDB.getMaintain().getParkingSpaces().get(2) , customerDB.login(email,password), "false","false","false");
		assertTrue(b.toString() != null);
	}   

	//Test for 4.4	
	@Test
	public void test_9() throws Exception { 
		String email = "Li123@my.yorku.ca";
		String password = "Liaa";
		assertFalse(bookingDB.add("2021-04-30 09:01:30","2021-04-30 10:00:30" , customerDB.login(email,password), psDB.getMaintain().getParkingSpaces().get(2)));
	}   


	//Test for 4.5	
	@Test
	public void test_10() throws Exception { 
		assertTrue(bookingDB.remove("Wenxuan2Li123@my.yorku.ca2022-04-30 07:00:30.0"));
	} 

	//Test for 4.5	
	@Test
	public void test_11() throws Exception { 
		assertFalse(bookingDB.remove("Wedada"));
	}

	//Test for 4.8	 
	@Test 
	public void test_12() throws Exception { 
		assertTrue(psDB.add("uppertown", "4", "12")); 
	}

	//Test for 4.8	
	@Test
	public void test_13() throws Exception { 
		assertTrue(psDB.remove("uppertown", "4")); 
	}

	//Test for 4.7	
	@Test 
	public void test_14() throws Exception {
		String email = "Li123@my.yorku.ca";
		String password = "Liaa";
		Customer c1 = customerDB.login(email,password);
		assertTrue(bookingDB.view(c1).size()>0); 
	}

	//Test for 4.7	
	@Test 
	public void test_15() throws Exception {
		assertTrue(bookingDB.view(psDB.getMaintain().getParkingSpaces().get(0)).size()>0); 
	}
	//Test for 4.7	
	@Test 
	public void test_16() throws Exception {
		assertTrue(bookingDB.view().size()>0); 
	}

	//Test for 4.8	
	@Test  
	public void test_17() throws Exception {
		assertFalse(bookingDB.granted("Wenxuan2Li123@my.yorku.ca2022-04-29 07:00:30.0")); 
	}
	//Test for 4.6	
	@Test 
	public void test_18() throws Exception {
		assertTrue(bookingDB.pay("Wenxuan2Li123@my.yorku.ca2022-04-29 07:00:30.0") == 0); 
	}
	//Test for 4.6	
	@Test 
	public void test_19() throws Exception {
		assertTrue(bookingDB.pay("Wenxuan2Li123@my.yorku.ca2022-04-29 07:00:30.0") != 1); 
	}

	//Test for 4.9	
	@Test 
	public void test_20() throws Exception {
		assertFalse(officerDB.changePaymentStatus("Wenxuan2Li123@my.yorku.ca2022-04-29 07:00:30.0")); 
	}

	//Test for 4.9	
	@Test 
	public void test_21() throws Exception {
		assertFalse(officerDB.changePaymentStatus("112")); 
	}


}