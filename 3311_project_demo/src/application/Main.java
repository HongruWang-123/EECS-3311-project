package application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;
import java.util.regex.Pattern;
import backend.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;


public class Main extends Application {

	private AdminForCustomer customerDB;
	private Administrator officerDB;
	private AdminForParkingSpace psDB;
	private AdminForRequest bookingDB;
	private ArrayList<ParkingSpace> parkingSpaces;
	private ArrayList<Button> parkingButtons;
	private Customer c;
	private Officer o;
	private User currentUser;

	private Scene scene;
	private boolean newCustomer = true;
	private Alert alert = new Alert(AlertType.INFORMATION);
	private Button back, logOut;

	GridPane root = new GridPane();
	@Override
	public void start(Stage primaryStage) 
	{
		try
		{
			//load in databases
			customerDB = new AdminForCustomer();
			officerDB = new Administrator();
			psDB = new AdminForParkingSpace();
			bookingDB = new AdminForRequest();			

			//init the array lists
			parkingSpaces = psDB.getMaintain().getParkingSpaces();
			parkingButtons = new ArrayList<Button>();

			back = new Button("back");
			back.setPrefWidth(150);
			back.setOnAction(e->{
				root.getChildren().clear();

				if(currentUser instanceof Officer)
					root.add(officerPage((Officer)currentUser), 0, 0);
				else if(currentUser instanceof Customer)
					root.add(customerPage((Customer)currentUser), 0, 0);
				else
					root.add(startingPage(), 0, 0);
			});

			logOut = new Button("Log Out");
			logOut.setPrefWidth(150);
			logOut.setOnAction(e->{
				root.getChildren().clear();
				root.add(startingPage(), 0, 0);
				currentUser = null;
			});

			//interface
			root.setAlignment(Pos.TOP_CENTER);
			root.setPrefWidth(350);
			scene = new Scene(root,350,400);

			//add the panes to the root
			root.add(startingPage(), 0, 0);

			primaryStage.setScene(scene);
			primaryStage.getIcons().clear();
			primaryStage.setTitle("3311 final project");
			primaryStage.show();
		} 
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public Pane startingPage()
	{
		VBox buttonPannel = new VBox(10);

		// buttons to add
		/*
		 * 1. Customer log
		 * 2. officer log
		 * 3. admin log
		 * 4. user register
		 */

		Button clog = new Button("Customer Log in");
		clog.setOnAction(e->{
			c = (Customer) customerLogin("customer");
			if(c !=null){
				currentUser = c;
				System.out.println("success");
				root.getChildren().clear();
				root.add(customerPage((Customer)c), 0, 0);
			}
			else
			{
				System.out.println("failure"); 
			}
		});

		Button olog  = new Button("Officer Log in");
		olog.setOnAction(e->{
			o = (Officer) customerLogin("officer");
			if(o !=null) {
				currentUser = o;
				System.out.println("Log in success");
				root.getChildren().clear();
				root.add(officerPage(o), 0, 0);

			}
			else {
				System.out.println("failure"); 
			}
		});

		Button admin = new Button("Administrator");
		admin.setOnAction(e->{
			root.getChildren().clear();
			root.add(adminPage(), 0, 0);
		});

		Button cRegister = new Button("User Register");
		cRegister .setOnAction(e->{
			root.getChildren().clear();
			root.add(register("customer"),0,0);
		});

		clog.setPrefWidth(150);
		olog.setPrefWidth(150);
		admin.setPrefWidth(150);
		cRegister.setPrefWidth(150);

		buttonPannel.getChildren().addAll(clog, olog, admin,cRegister);

		return buttonPannel;
	}

	private Pane officerPage(Officer o) 
	{
		VBox pannel = new VBox(10);
		pannel.setAlignment(Pos.CENTER);

		/*
		 * 1. add space
		 * 2. remove space
		 * 3. grant req
		 * 4. cancel req
		 */

		Button add = new Button("add Parking Space");
		add.setOnAction(e->{
			pannel.setDisable(true);
			addParkingSpace(pannel);
		});

		Button remove= new Button("remove");
		remove.setOnAction(e->{
			pannel.setDisable(true);
			removeParkingSpace(pannel);
		});

		Button grant = new Button("grant");
		grant.setOnAction(e->{
			
			TextInputDialog dialog = new TextInputDialog();
			dialog.setTitle("Grant Request");
			dialog.setHeaderText(null);
			dialog.setContentText("Enter Booking ID");
			Optional<String> result = dialog.showAndWait();
			
			if(result.isPresent()){
				String id = result.get();
				boolean success = false;
				try {
					success = bookingDB.granted(id);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if(success)
					System.out.println("Request Granted");
			}
			
		});

		Button cancel = new Button("cancel");
		cancel.setOnAction(e->{
			TextInputDialog dialog = new TextInputDialog();
			dialog.setTitle("Cancel Request");
			dialog.setHeaderText(null);
			dialog.setContentText("Enter Booking ID");
			Optional<String> result = dialog.showAndWait();
			
			if(result.isPresent()){
				String id = result.get();
				boolean success = false;
				try {
					success = bookingDB.remove(id);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if(success)
					System.out.println("Request Canceled");
			}
		});

		add.setPrefWidth(150);
		remove.setPrefWidth(150);
		grant.setPrefWidth(150);
		cancel.setPrefWidth(150);

		pannel.getChildren().addAll(parkingButtonPane(o, ""),add, remove, grant, cancel, logOut);

		return pannel;
	}

	private void removeParkingSpace(Pane p) 
	{
		// TODO Auto-generated method stub
		psDB.view();
		bookingDB.view();

		Stage stage = new Stage();

		VBox pannel = new VBox(10);
		pannel.setAlignment(Pos.TOP_CENTER);
		pannel.setPadding(new Insets(10,10,10,10));

		TextField address = new TextField("Address");
		TextField id = new TextField("ID number");

		Button ok = new Button("ok");
		ok.setOnAction(e->{

			boolean success = false;
			try {
				success = psDB.remove(address.getText(), id.getText());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			//add a function to remove bookings with parking space as input
			if(success) {
				System.out.println("Success, parking space removed");
				psDB.view();
				bookingDB.view();		
			}
		});

		//address.setPrefWidth(150);
		//id.setPrefWidth(150);
		//price.setPrefWidth(150);
		ok.setPrefWidth(150);

		pannel.getChildren().addAll(address, id, ok);	

		Scene s = new Scene(pannel,200,200);

		stage.setOnCloseRequest(e->{
			p.setDisable(false);
			p.getChildren().set(0, parkingButtonPane(o, ""));
		});
		stage.setScene(s);
		stage.getIcons().clear();
		stage.setTitle("3311 final project");
		stage.show();

	}

	private void addParkingSpace(Pane p) {
		// TODO Auto-generated method stub
		psDB.view();
		bookingDB.view();

		Stage stage = new Stage();
		stage.setOnCloseRequest(e->{
			p.setDisable(false);
			p.getChildren().set(0, parkingButtonPane(o, ""));
		});

		VBox pannel = new VBox(10); 
		pannel.setAlignment(Pos.TOP_CENTER);
		pannel.setPadding(new Insets(10,10,10,10));

		TextField address = new TextField("Address");
		TextField id = new TextField("ID number");
		TextField price = new TextField("Hourly Price");

		Button ok = new Button("ok");
		ok.setOnAction(e->{

			boolean success = false; 
			try {
				success = psDB.add(address.getText(), id.getText(), price.getText());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			if(success) {
				System.out.println("Success, parking space added");
				psDB.view();
				bookingDB.view();	
			}
		});

		//address.setPrefWidth(150);
		//id.setPrefWidth(150);
		//price.setPrefWidth(150);
		ok.setPrefWidth(150);

		pannel.getChildren().addAll(address, id, price, ok);	

		Scene s = new Scene(pannel,200,200);

		stage.setScene(s);
		stage.getIcons().clear();
		stage.setTitle("3311 final project");
		stage.show();

	}

	private Pane customerPage(Customer c) 
	{
		/*
		 * 1. view booking
		 * 2. add booking
		 * 3. cancel booking
		 * 4. pay
		 */

		VBox pannel = new VBox(10);

		Button view = new Button("View Booking");
		view.setOnAction(e->{

			String text ="";
			for(Booking book: bookingDB.view(c))			
				text += book.toString()+"\n";

			TextArea textArea = new TextArea(text);
			textArea.setEditable(false);
			textArea.setWrapText(false);
			textArea.setPrefWidth(600);
			GridPane gridPane = new GridPane();
			gridPane.add(textArea, 0, 0);

			Alert a = new Alert(AlertType.INFORMATION);
			a.getDialogPane().setContent(gridPane);
			a.setHeaderText(null);
			a.setTitle("info");
			a.showAndWait();
		});

		Button add = new Button("Add Booking");
		add.setOnAction(e->{
			root.getChildren().clear();
			VBox temp = new VBox(10); temp.setAlignment(Pos.CENTER); temp.getChildren().addAll(parkingButtonPane(c, "add"), back);
			root.add(temp,0,0);	
		});

		Button cancel = new Button("cancel Booking");
		cancel.setOnAction(e->{

			TextInputDialog cancelb = new TextInputDialog();
			cancelb.setTitle("Cancel Booking");
			cancelb.setHeaderText(null);
			cancelb.setContentText("enter Booking ID");
			Optional<String> result = cancelb.showAndWait();

			if(result.isPresent())
			{
				String id = result.get();
				boolean success = false;

				try {
					success = bookingDB.remove(id);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				if(success)
					System.out.println("booking removed successfully");
			}
			else
				System.out.println("invalid booking ID");
		});

		Button pay = new Button("Pay");
		pay.setOnAction(e->{
			root.getChildren().clear();
			root.add(payBooking(c),0,0);
		});

		view.setPrefWidth(150);
		add.setPrefWidth(150);
		cancel.setPrefWidth(150);
		pay.setPrefWidth(150);

		pannel.getChildren().addAll(view, add, cancel, pay, logOut);

		return pannel;
	}

	private Pane payBooking(Customer c) {
		// TODO Auto-generated method stub
		/*
		 * 1. id
		 * 2. cardno
		 * 3. security no
		 * 4.cost
		 * 5. ok 
		 * 6. back
		 */
		
		VBox pannel = new VBox(10);
		
		TextField id = new TextField("Booking ID");
		TextField cardno = new TextField("Credit Card Number");
		TextField securityNo = new TextField("Security Number");
		
		Button ok = new Button("ok");
		
		id.setPrefWidth(150);
		cardno.setPrefWidth(150);
		securityNo.setPrefWidth(150);
		
		ok.setPrefWidth(150);
		ok.setOnAction(e->{
			
			double cost = bookingDB.getCost(id.getText());
					
			if(cost >0)
			{
				Alert a = new Alert(AlertType.INFORMATION);
				a.setHeaderText(null);
				a.setContentText("The cost for this booking is: $"+cost);
				a.setTitle("info");
				a.getButtonTypes().clear();
				a.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
				Optional<ButtonType> result = a.showAndWait();
				
				if(result.get() == ButtonType.OK){
					
					if (bookingDB.pay(cardno.getText(), securityNo.getText(), id.getText()) >0)
						System.out.println("Payment Successful");	
				}			
			}
			
		});
		
		pannel.getChildren().addAll(id, cardno, securityNo, ok, back);
		return pannel;
	}

	private Pane parkingButtonPane(User u, String action)
	{
		FlowPane parkingPane = new FlowPane(10,10);
		parkingPane.setAlignment(Pos.TOP_LEFT);
		parkingPane.setPadding(new Insets(10,10,10,10));
		parkingPane.setMaxWidth(350);
		parkingPane.setOrientation(Orientation.HORIZONTAL);

		// init the parking buttons
		parkingButtons.clear();
		for(ParkingSpace p : psDB.getMaintain().getParkingSpaces())
		{
			Button b = new Button(p.getAddress()+""+p.getNumber());
			b.setPrefWidth(100);
			parkingButtons.add(b);

			b.setOnAction(e->{
				if (u instanceof Officer || u == null){

					String text ="";
					for(Booking book: bookingDB.view(p))						
						text += book.toString()+"\n";
					

					TextArea textArea = new TextArea(text);
					textArea.setEditable(false);
					textArea.setWrapText(false);
					textArea.setPrefWidth(600);
					GridPane gridPane = new GridPane();
					gridPane.add(textArea, 0, 0);

					Alert a = new Alert(AlertType.INFORMATION);
					a.getDialogPane().setContent(gridPane);
					a.setHeaderText(null);
					a.setTitle("info");
					a.showAndWait();
				}
				else
				{
					//customer
					if(action.equals("add"))
					{
						root.getChildren().clear();
						root.add(addBooking(c, p),0,0);	
					}

				}
			});

		}

		parkingPane.getChildren().addAll(parkingButtons);

		return parkingPane;
	}

	private Pane addBooking(Customer c2, ParkingSpace p) 
	{
		VBox pannel = new VBox(10);

		TextField startTime = new TextField("yyyy-mm-dd hh:mm:ss");
		TextField endTime = new TextField("yyyy-mm-dd hh:mm:ss");

		Button ok = new Button("ok");
		ok.setOnAction(e->{
			boolean success = false;
			try {success = bookingDB.add(startTime.getText(), endTime.getText(), c2, p);} catch (Exception e1) {e1.printStackTrace();}

			if(success)
				System.out.println("request success");
		});

		ok.setPrefWidth(150);
		startTime.setPrefWidth(150);
		endTime.setPrefWidth(150);

		pannel.getChildren().addAll(startTime, endTime, ok, back);

		return pannel;
	}

	private Pane adminPage()
	{
		VBox pannel = new VBox(10);
		pannel.setAlignment(Pos.CENTER);

		Button addOfficer = new Button("Add Officer");
		addOfficer.setPrefWidth(150);;
		addOfficer.setOnAction(e->{
			root.getChildren().clear();
			root.add(register("officer"),0,0);			
		});

		Button removeOfficer = new Button("remove Officer");
		removeOfficer.setPrefWidth(150);
		removeOfficer.setOnAction(e->{
			
			TextInputDialog email = new TextInputDialog();
			email.setTitle("Remove Officer");
			email.setHeaderText(null);
			email.setContentText("Enter Email");
			Optional<String> result = email.showAndWait();
			
			if(result.isPresent())
			{
				String officerId = result.get();
				boolean success = false;
				
				try {
					 success = officerDB.remove(officerId);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				if(success)
					System.out.println("Officer Removed");
				else
					System.out.println("invalid email");
					
			}

		});

		Button pay = new Button("Settle Payment");	
		pay.setPrefWidth(150);
		pay.setOnAction(e->{
			
			TextInputDialog dialog = new TextInputDialog();
			dialog.setTitle("Settle Payment");
			dialog.setHeaderText(null);
			dialog.setContentText("Enter Booking ID");
			Optional<String> result = dialog.showAndWait();
			
			if(result.isPresent())
			{
				String bookingId = result.get().trim();
				boolean success = false;
				try {
					 success = officerDB.changePaymentStatus(bookingId);
					 				
				} catch (Exception e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				if(success) {
					System.out.println("Payment settled, good bye!");
					
					System.exit(0);
				}
				else
					System.out.println("Invalid Booking ID");
					
			}
		});

		pannel.getChildren().addAll(parkingButtonPane(null, ""),addOfficer, removeOfficer, pay, back);

		return pannel;

	}

	private User customerLogin(String type)
	{
		User u=null;

		TextInputDialog email = new TextInputDialog();
		email.setTitle("Enter email");
		email.setHeaderText(null);
		email.setContentText(type+" Log In");
		Optional<String> result = email.showAndWait();

		if (result.isPresent())
		{

			String userEmail = result.get();

			email = new TextInputDialog();
			email.setHeaderText(null);
			email.setTitle("Enter Password");
			email.setContentText(type+" Log In");
			result = email.showAndWait();

			if(result.isPresent())
			{
				String userPassword = result.get();

				if (type.equals("customer"))
					u = customerDB.login(userEmail, userPassword);	
				else if (type.equals("officer"))
					u = officerDB.login(userEmail, userPassword);

			}
			else
				System.out.println("error on user log in, please enter the password!");
		}
		else
			System.out.println("error on user log in, please enter an email!");

		return u;
	}

	private Pane register(String type)
	{
		/*
		 * for addofficer: String fname,String lname,String email,String password
		   for removeofficer: string emai
		 */
		TextField email = new TextField("email");
		TextField fname = new TextField("first name");
		TextField lname = new TextField("last name");
		TextField password = new TextField("password");

		//only for customer
		/*
		 * ,String cardno,String securityno,String license
		 */
		TextField cardno = new TextField("credit card number");
		TextField securityno = new TextField("credit card security number");
		TextField license = new TextField("license");

		Button ok = new Button("ok");
		ok.setOnAction(e->{

			boolean success = false;
			if(type.equals("officer"))
			{
				try {success = officerDB.add(fname.getText(), lname.getText(), email.getText(), password.getText());} catch (Exception e1) {e1.printStackTrace();}
			}
			else
			{
				//customer
				try {success = customerDB.add(fname.getText(), lname.getText(), email.getText(), password.getText(), cardno.getText(), securityno.getText(), license.getText());}
				catch (Exception e1) {e1.printStackTrace();}
			}

			if(success) System.out.println("success");

		});

		email.setPrefWidth(150);
		fname.setPrefWidth(150);
		lname.setPrefWidth(150);
		password.setPrefWidth(150);
		cardno.setPrefWidth(150);
		securityno.setPrefWidth(150);
		license.setPrefWidth(150);
		ok.setPrefWidth(150);		

		VBox pannel = new VBox(10);
		pannel.setPadding(new Insets(10,10,10,10));

		pannel.getChildren().addAll(email, fname, lname, password);
		if(type.equals("customer"))
			pannel.getChildren().addAll(cardno, securityno, license);

		pannel.getChildren().addAll(ok, back);		

		return pannel;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
