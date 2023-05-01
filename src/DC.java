
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class DC {
	//store list of donor
	private ArrayList<Donor> donors;
	//store list of ngo
	private ArrayList<NGO> ngos;
	
	//calling both when object created
	public DC() {
		loadDonors();
		loadNGOs();
	}
	
	//it will read donors.csv file and add in list
	private void loadDonors() {
		donors = new ArrayList<>();
		try {
			Scanner scanner = new Scanner(new File("donors.csv"));
			while (scanner.hasNextLine()) {
				String data[] = scanner.nextLine().split(";");
				Donor donor = new Donor(data[0], data[1], data[2]);
				donor.loadDonations();
				donors.add(donor);
			}
			scanner.close();
		} catch (FileNotFoundException e) {			
		}
	}
	
	//it will read ngos.csv file and add in ngos list
	private void loadNGOs() {
		ngos = new ArrayList<>();
		try {
			Scanner scanner = new Scanner(new File("ngos.csv"));
			while (scanner.hasNextLine()) {
				String data[] = scanner.nextLine().split(";");
				NGO ngo = new NGO(data[0], data[1], Integer.parseInt(data[2]));
				ngo.loadDonationsNeeded();
				ngos.add(ngo);
			}
			scanner.close();
		} catch (FileNotFoundException e) {			
		}
	}
	
	//it will display all donation based on ngo
	public void viewAll() {
		for (Donor donor: donors) {
			donor.viewDonations(ngos);
		}
	}
	// it will display one to one donation based on ngo
	public void viewOneToOne() {
		for (Donor donor: donors) {
			donor.viewOneToOne(ngos);
		}
	}
	// it will display one to many donation based on ngo
	public void viewOneToMany() {
		for (Donor donor: donors) {
			donor.viewDonations(ngos);
		}
	}
	
	// it will display many to one donation based on ngo
	public void viewManyToOne() {
		for (Donor donor: donors) {
			donor.viewDonations(ngos);
		}
	}
	
	// it will display many to many donation based on ngo
	public void viewManyToMany() {
		for (Donor donor: donors) {			
			donor.viewDonations(ngos);
		}
	}
	// it handke ngo login and there operations
	public void handleNGO() {		
		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter name: ");
		String name = scanner.nextLine();
		System.out.print("Enter password: ");
		String password = scanner.nextLine();	
		NGO ngo = loginNGO(name, password);
		if (ngo == null) {
			System.out.println("Inavild usernmae or password");
	 	} else {	 		
	 		System.out.println("Logged in succesfully");
	 		while (true) {
	 			switch (NGOMenu()) {
	 			case 1:
	 				ngo.requestAid();
	 				break;				
	 			case 2:
	 				ngo.viewDonors(donors);
	 				break;
	 			case 3: return;
	 			default: System.out.println("Invalid option"); 	 				
	 			}
	 		}
	 	}
	}
	
	// it display ngo menu that ngo can choose
	private int NGOMenu() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("1. Enter aid needed");
		System.out.println("2. View list");
		System.out.println("3. BACK");
		System.out.print("\nEnter your choice: ");
		try {
			return Integer.parseInt(scanner.nextLine());			
		} catch (Exception e) {
			return -1;
		}
	}
	
	// it will check login exists or not
	private NGO loginNGO(String name, String password) {
		for (NGO ngo: ngos) {
			if ((ngo.getName().equals(name) && ngo.getPassword().equals(password))) {
				return ngo;
			}
		}
		return null;
	}
	
	// it will handle login of donor  and perform there operations
	public void handleDonor() {		
		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter name: ");
		String name = scanner.nextLine();
		System.out.print("Enter password: ");
		String password = scanner.nextLine();	
		Donor donor = loginDonor(name, password);
		if (donor == null) {
			System.out.println("Inavild usernmae or password");
	 	} else {	 		
	 		System.out.println("Logged in succesfully");
	 		while (true) {
	 			switch (donorMenu()) {
	 			case 1:
	 				donor.donateAid();
	 				break;				
	 			case 2:
	 				donor.viewDonations(ngos);
	 				break;
	 			case 3: return;
	 			default: System.out.println("Invalid option"); 	 				
	 			}
	 		}
	 	}
	}
	
	// it display the donor menus
	private int donorMenu() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("1. Enter aids to donate");
		System.out.println("2. View list of aids");
		System.out.println("3. BACK");
		System.out.print("\nEnter your choice: ");
		try {
			return Integer.parseInt(scanner.nextLine());			
		} catch (Exception e) {
			return -1;
		}
	}
	
	// it will check dono exists or not
	private Donor loginDonor(String name, String password) {
		for (Donor donor: donors) {
			if ((donor.getName().equals(name) && donor.getPassword().equals(password))) {
				return donor;
			}
		}
		return null;
	}
	
	// its for registering  donor save in file
	public boolean registerDonor() {
		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter name: ");
		String name = scanner.nextLine();
		System.out.print("Enter password: ");
		String password = scanner.nextLine();
		System.out.print("Enter phone number: ");
		String phoneNumber = scanner.nextLine();
		Donor donor = new Donor(name, password, phoneNumber);
		if (!isDonorExist(donor)) {
			return addDonor(donor);			
		}		
		return false;
	}
	
	// if dono is not already exists then it will save in file
	private boolean addDonor(Donor donor) {		
		try {
			FileWriter writer = new FileWriter(new File("donors.csv"), true);
			writer.write(donor.getName() + ";" + donor.getPassword() + ";" + donor.getPhoneNumber() + "\n");
			writer.close();
			donors.add(donor);
			System.out.println("Register succesfully!!!\n");
			return true;
		} catch (IOException e) {
			System.out.println("An error occured will register\n");
			return false;
		}
	}
	
	// it will check dono exists or not
	private boolean isDonorExist(Donor donor) {
		for (int i = 0; i < donors.size(); i++) {
			if (donors.get(i).equals(donor)) { 
				System.out.print("Donor already exist\n");
				return true;
			}
		}
		return false;
	}
	
	// it will register ngo
	public boolean registerNgo() {
		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter name: ");
		String name = scanner.nextLine();
		System.out.print("Enter password: ");
		String password = scanner.nextLine();
		System.out.print("Enter man power: ");
		int manPower = scanner.nextInt();
		NGO ngo = new NGO(name, password, manPower);
		if (!isNGOExist(ngo)) {
			return addNgo(ngo);			
		}		
		return false;
	}
	
	// it will add ngo in file
	private boolean addNgo(NGO ngo) {		
		try {
			FileWriter writer = new FileWriter(new File("ngos.csv"), true);
			writer.write(ngo.getName() + ";" + ngo.getPassword() + ";" + ngo.getManPower() + "\n");
			writer.close();
			System.out.println("Register succesfully!!!\n");
			ngos.add(ngo);
			return true;
		} catch (IOException e) {
			System.out.println("An error occured will register\n");
			return false;
		}
	}
	
	// it will check if ngo doent exists then it save in file
	private boolean isNGOExist(NGO ngo) {
		for (int i = 0; i < ngos.size(); i++) {
			if (ngos.get(i).equals(ngo)) { 
				System.out.print("NGO already exist\n");
				return true;
			}
		}
		return false;
	}
}