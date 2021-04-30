package backend;

import java.util.ArrayList;

public class AdminForParkingSpace {
	private MaintainParkingSpace maintain = new MaintainParkingSpace();
	public MaintainParkingSpace maintain() {
		return maintain;
	}
	public AdminForParkingSpace()  {
		try {
			maintain.load("src\\parkingspaces.csv");
		} catch (Exception e) {
			// TODO Auto-generated catch block 
			e.printStackTrace();
		}
	}
	public boolean add(String address,String number,String hourly) throws Exception {
		String nno = number;
		ArrayList<ParkingSpace> parkingSpaces = maintain.getParkingSpaces();
		for(ParkingSpace a : maintain.getParkingSpaces()) {
			if(a.getNumber().equals(nno)) {
				System.out.println("the ParkingSpace already exists in the system");
				return false;
			}
		}

		parkingSpaces.add(new ParkingSpace(address,number,hourly));
		maintain.update("src\\parkingspaces.csv");
		return true;
	}

	public ArrayList<ParkingSpace> view(){
		try {
			maintain.update("src\\parkingspaces.csv");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return maintain.getParkingSpaces();
	}

	//view before this
	public boolean remove(String address,String number) throws Exception {
        boolean flag = false;
        ParkingSpace flagS = null;
        AdminForRequest adminR = new AdminForRequest();
        ArrayList<ParkingSpace> parkingSpaces = maintain.getParkingSpaces();
        if(parkingSpaces.size() <= 1) {
            System.out.println("can not remove parkingspace");
            return false;
        }
        for(ParkingSpace a : parkingSpaces) {
            if(a.getNumber().equals(number)) {
                flagS = a;
                for(Booking b : adminR.view()) {
                    if(b.getSpace().equals(a)) {
                        flag = true;
                    }
                }

            }
        }
        if(!flag) {
            parkingSpaces.remove(flagS);
            maintain.update("src\\parkingspaces.csv");
            return true;
        }
        System.out.println("the input address and number are not valid");
        return false;
    }
	public MaintainParkingSpace getMaintain() {
		return maintain;
	}

}
