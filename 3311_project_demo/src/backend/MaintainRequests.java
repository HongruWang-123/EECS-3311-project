package backend;
import java.io.File;
import java.io.FileWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;

public class MaintainRequests {
private ArrayList<Booking> requests = new ArrayList<Booking>();
private AdminForCustomer adc = new AdminForCustomer();
private AdminForParkingSpace adp = new AdminForParkingSpace();
private String path;

public void load(String path) throws Exception {
	CsvReader reader = new CsvReader(path);
	reader.readHeaders();
	
	while(reader.readRecord()){ 
		for(Customer c : adc.getMaintain().getCustomers()) {
			for(ParkingSpace p : adp.maintain().getParkingSpaces()) {
			if(reader.get("customer").equals(c.getEmail()) && reader.get("parking").equals(p.getNumber())) {
				Booking booking = new Booking(reader.get("start"),reader.get("end"),p,c,reader.get("granted"),reader.get("pay"), reader.get("paymentStatus"));
				requests.add(booking);
			}
			}
		}
		//name,id,email,password		
	}
}

public void update(String path) throws Exception{
	try {		
			CsvWriter csvOutput = new CsvWriter(new FileWriter(path, false), ',');
			//name,id,email,password
			csvOutput.write("start");
			csvOutput.write("end");
	    	csvOutput.write("customer");
			csvOutput.write("parking");
			csvOutput.write("license");
			csvOutput.write("granted");
			csvOutput.write("pay");
			csvOutput.write("paymentStatus");
			csvOutput.endRecord();

			// else assume that the file already has the correct header line
			// write out a few records
			for(Booking u: requests){
				csvOutput.write(u.getStart().toString());
				csvOutput.write(u.getEnd().toString());
				csvOutput.write(u.getCustomer().getEmail());
				csvOutput.write(u.getSpace().getNumber());
				csvOutput.write(u.getCustomer().getLicense());
				csvOutput.write(u.isGranted().toString());
				csvOutput.write(u.isPay().toString());
				csvOutput.write(u.getPaymentstatus().toString());
				csvOutput.endRecord();
			}
			csvOutput.close();
		
		}catch (Exception e) {
			e.printStackTrace();
		}
}

public ArrayList<Booking> getRequests() {
	return requests;
}
public String getPath() {
	return path;
}

public void setPath(String path) {
	this.path = path;
}


}
