package backend;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;

public class MaintainOfficers {
       private ArrayList<Officer> officers = new ArrayList<Officer>();
       private String path;

public void load(String path) throws Exception {
	CsvReader reader = new CsvReader(path);
	reader.readHeaders();
	
	while(reader.readRecord()){ 
		Officer officer = new Officer(reader.get("fname"),reader.get("lname"),reader.get("email"),reader.get("password"));
		//name,id,email,password
		officers.add(officer);
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
			csvOutput.write("id");
			csvOutput.endRecord();

			// else assume that the file already has the correct header line
			// write out a few records
			for(Officer u: officers){
				csvOutput.write(u.getFname());
				csvOutput.write(u.getLname());
				csvOutput.write(u.getEmail());
				csvOutput.write(u.getPassword());
				csvOutput.write(u.getId());
				csvOutput.endRecord();
			}
			csvOutput.close();
		
		}catch (Exception e) {
			e.printStackTrace();
		}
}

public ArrayList<Officer> getOfficers() {
	return officers;
}

public void setOfficers(ArrayList<Officer> officers) {
	this.officers = officers;
}

public String getPath() {
	return path;
}

public void setPath(String path) {
	this.path = path;
}


}
