package backend;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;

public class MaintainCustomers {
       private ArrayList<Customer> customers = new ArrayList<Customer>();
       private String path;

public void load(String path) throws Exception {
	CsvReader reader = new CsvReader(path);
	reader.readHeaders();
	
	while(reader.readRecord()){ 
		Customer customer = new Customer(reader.get("fname"),reader.get("lname"),reader.get("email"),reader.get("password"),reader.get("cardno"),reader.get("security"),reader.get("license"));
		//name,id,email,password
		customers.add(customer);
	}
}

public void update(String path) throws Exception{
	try {		
			CsvWriter csvOutput = new CsvWriter(new FileWriter(path, false), ',');
			//name,id,email,password
			csvOutput.write("fname");
			csvOutput.write("lname");
	    	csvOutput.write("email");
			csvOutput.write("password");
			csvOutput.write("cardno");
			csvOutput.write("security");
			csvOutput.write("license");
			csvOutput.endRecord();

			// else assume that the file already has the correct header line
			// write out a few records
			for(Customer u: customers){
				csvOutput.write(u.getFname());
				csvOutput.write(u.getLname());
				csvOutput.write(u.getEmail());
				csvOutput.write(u.getPassword());
				csvOutput.write(u.getCardno());
				csvOutput.write(u.getSecurityno());
				csvOutput.write(u.getLicense());
				csvOutput.endRecord();
			}
			csvOutput.close();
		
		}catch (Exception e) {
			e.printStackTrace();
		}
}

public ArrayList<Customer> getCustomers() {
	return customers;
}

public void setCustomers(ArrayList<Customer> customers) {
	this.customers = customers;
}

public String getPath() {
	return path;
}

public void setPath(String path) {
	this.path = path;
}


}
