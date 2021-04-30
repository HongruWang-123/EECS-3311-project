package backend;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;

public class MaintainParkingSpace {
private ArrayList<ParkingSpace> parkingSpaces = new ArrayList<ParkingSpace>();
private String path;

public void load(String path) throws Exception {
	CsvReader reader = new CsvReader(path);
	reader.readHeaders();
	
	while(reader.readRecord()){ 
		ParkingSpace parkingSpace = new ParkingSpace(reader.get("address"),reader.get("number"),reader.get("hourly price"));
		//name,number,email,password
		parkingSpaces.add(parkingSpace);
	}
}

public void update(String path) throws Exception{
	try {		
			CsvWriter csvOutput = new CsvWriter(new FileWriter(path, false), ',');
			//name,number,email,password
			csvOutput.write("address");
			csvOutput.write("number");
	    	csvOutput.write("hourly price");
			
			csvOutput.endRecord();

			// else assume that the file already has the correct header line
			// write out a few records
			for(ParkingSpace u: parkingSpaces){
				csvOutput.write(u.getAddress());
				csvOutput.write(u.getNumber());
				csvOutput.write(u.getHourlyprice().toString());

				csvOutput.endRecord();
			}
			csvOutput.close();
		
		}catch (Exception e) {
			e.printStackTrace();
		}
}

public ArrayList<ParkingSpace> getParkingSpaces() {
	return parkingSpaces;
}

public void setParkingSpaces(ArrayList<ParkingSpace> ParkingSpaces) {
	this.parkingSpaces = ParkingSpaces;
}

public String getPath() {
	return path;
}

public void setPath(String path) {
	this.path = path;
}


}
