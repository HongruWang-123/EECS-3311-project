package backend; 

public class Officer extends User {
private String id;


public String getId() {
	return id;
}

public void setId(String id) {
	this.id = id;
}

public Officer(String fname,String lname,String email,String password) {
	this.setFname(fname);
	this.setLname(lname);
	this.setEmail(email);
	this.setPassword(password);
	this.id = email;
	
}

@Override
public boolean equals(Object o) {

    // If the object is compared with itself then return true  
    if (o == this) {
        return true;
    }

    /* Check if o is an instance of Booking or not
      "null instanceof [type]" also returns false */
    if (!(o instanceof Officer)) {
        return false;
    }
      
    // typecast o to Booking so that we can compare data members 
    Officer a = (Officer) o;
    
    
    return this.id.equals(a.getId());
}

@Override
public String toString() {
	return "Officer" + id + ", name: " + fname + " " + lname +"\n"+ 
			"email: "+ email + "\n"+
			"password: " + password + "\n"
	        +"================";
}
}

