
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Donor {
	private String name;	
	private String password;
	private String phoneNumber;
	// store list of donations
	private ArrayList<Aid> donations;
	
	public Donor(String name, String password,  String phoneNumber) {
		this.name = name;		
		this.password = password;
		this.phoneNumber = phoneNumber;
		this.donations = new ArrayList<>();
	}
	//getters and setters
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public ArrayList<Aid> getDonations() {
		return donations;
	}

	// check object whether equal or not
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Donor other = (Donor) obj;
		return name.equals(other.name) && phoneNumber.equals(other.phoneNumber);
	}
	
	// it wil read donation from file
	public void loadDonations() {
		try {
			Scanner scanner = new Scanner(new File(name+".csv"));
			while (scanner.hasNextLine()) {
				String data[] = scanner.nextLine().split(";");
				this.donations.add(new Aid(data[0], Integer.parseInt(data[1])));
			}
			scanner.close();
		} catch (FileNotFoundException e) {			
		}
	}
	
	// it wil don ate aid and save in file
	public void donateAid() {
		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter aid name: ");
		String name = scanner.nextLine();
		System.out.print("Enter quantity: ");
		int quantity = scanner.nextInt();
		Aid aid = new Aid(name, quantity);
		int index = isDonationExist(aid);
		if (index == -1) {
			try {
				FileWriter writer = new FileWriter(new File(this.name+".csv"), true);
				writer.write(aid.getName() + ";" + aid.getQuantity() + "\n");
				writer.close();				
				donations.add(aid);
			} catch (IOException e) {
			}
		} else {
			Aid newAid = new Aid(donations.get(index).getName(), donations.get(index).getQuantity() + aid.getQuantity());
			try {
				FileWriter writer = new FileWriter(new File(this.name+".csv"));
				donations.set(index, newAid);
				for (Aid donation : donations) {
					writer.write(donation.getName() + ";" + donation.getQuantity() + "\n");	
				}				
				writer.close();
			} catch (IOException e) {
			}			
		}
	}
	
	// we will check whether donation exists or not
	public int isDonationExist(Aid aid) {
		for (int i = 0; i < donations.size(); i++) {
			if (donations.get(i).getName().equalsIgnoreCase(aid.getName())) {
				return i;
			}
		}
		return -1;
	}
	
	// it will display donation base on ngos
	public void viewDonations(ArrayList<NGO> ngos) {
		System.out.println(String.format("%-5s %-15s %-10s %-10s %-4s %s", "Donor", "Phone", "Aids", "Quantity", "NGO", "Manpower"));
		for (Aid donation: donations) {	
			System.out.print(String.format("%-5s %-15s %-10s ", this.name, this.phoneNumber, donation.getName()));
			int remainingQuantity = donation.getQuantity();
			for (NGO ngo: ngos) {
				if (remainingQuantity <= 0) break;
				int quantity = ngo.required(remainingQuantity, donation.getName());
				if (quantity == -1) continue;			
				remainingQuantity -= quantity;
				if (remainingQuantity < 0)
					System.out.println(String.format("%-10d %-4s %d", donation.getQuantity(), ngo.getName(), ngo.getManPower()));
				else
					System.out.println(String.format("%-10d %-4s %d", quantity, ngo.getName(), ngo.getManPower()));	
			}
			if (remainingQuantity > 0) {
				System.out.println(String.format("%-5s %-15s %-10s %-10d %-4s %s",  this.name, this.phoneNumber, donation.getName(), remainingQuantity, "-", "-"));	
			}
		}
	}
	
	//it will display one to one donations based on ngo
	public void viewOneToOne(ArrayList<NGO> ngos) {
		System.out.println(String.format("%-5s %-15s %-10s %-10s %-4s %s", "Donor", "Phone", "Aids", "Quantity", "NGO", "Manpower"));
		for (Aid donation: donations) {	
			System.out.print(String.format("%-5s %-15s %-10s ", this.name, this.phoneNumber, donation.getName()));
			int remainingQuantity = donation.getQuantity();
			for (NGO ngo: ngos) {				
				int quantity = ngo.required(remainingQuantity, donation.getName());
				if (quantity == -1) continue;			
				remainingQuantity -= quantity;
				if (remainingQuantity == 0)
					System.out.println(String.format("%-10d %-4s %d", donation.getQuantity(), ngo.getName(), ngo.getManPower()));					
			}
			if (remainingQuantity > 0) {
				System.out.println(String.format("%-5s %-15s %-10s %-10d %-4s %s",  this.name, this.phoneNumber, donation.getName(), remainingQuantity, "-", "-"));	
			}
		}
	}
	
	// it will donate aid
	public int donate(int quantity, String name) {
		for (Aid aid: donations) {
			if (aid.getName().equalsIgnoreCase(name)) {
				return aid.getQuantity();
			}
		}
		return -1;
	}
}
