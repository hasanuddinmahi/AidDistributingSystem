
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class NGO {	
	private String name;
	private String password;
	private int manPower;
	// store list of aids that is needed to ngo
	private ArrayList<Aid> donationsNeeded;
	
	public NGO(String name, String password, int manPower) {			
		this.name = name;
		this.password = password;
		this.manPower = manPower;
		this.donationsNeeded = new ArrayList<>();
	}
	
	//getters and setters
	public int getManPower() {
		return manPower;
	}
	
	public void setManPower(int manPower) {
		this.manPower = manPower;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	// it will read file that we pass in constructor
	public void loadDonationsNeeded() {
		try {
			Scanner scanner = new Scanner(new File(name+".csv"));
			while (scanner.hasNextLine()) {
				String data[] = scanner.nextLine().split(";");
				this.donationsNeeded.add(new Aid(data[0], Integer.parseInt(data[1])));
			}
			scanner.close();
		} catch (FileNotFoundException e) {			
		}
	}
	
	// it wil request for aid and store aid in file
	public void requestAid() {
		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter aid name: ");
		String name = scanner.nextLine();
		System.out.print("Enter quantity: ");
		int quantity = scanner.nextInt();
		Aid aid = new Aid(name, quantity);
		int index = isDonationNeededExist(aid);
		if (index == -1) {
			try {
				FileWriter writer = new FileWriter(new File(this.name+".csv"), true);
				writer.write(aid.getName() + ";" + aid.getQuantity() + "\n");
				writer.close();				
				donationsNeeded.add(aid);
			} catch (IOException e) {
			}
		} else {
			Aid newAid = new Aid(donationsNeeded.get(index).getName(), donationsNeeded.get(index).getQuantity() + aid.getQuantity());
			try {
				FileWriter writer = new FileWriter(new File(this.name+".csv"));
				donationsNeeded.set(index, newAid);
				for (Aid donation : donationsNeeded) {
					writer.write(donation.getName() + ";" + donation.getQuantity() + "\n");	
				}				
				writer.close();				
			} catch (IOException e) {
			}			
		}
	}
	
	// it will check whether donation is needed or not
	public int isDonationNeededExist(Aid aid) {
		for (int i = 0; i < donationsNeeded.size(); i++) {
			if (donationsNeeded.get(i).getName().equalsIgnoreCase(aid.getName())) {
				return i;
			}
		}
		return -1;
	}
	
	//  it will return quantity that is needed in aid
	public int required(int quantity, String name) {
		for (Aid aid: donationsNeeded) {
			if (aid.getName().equalsIgnoreCase(name)) {
				return aid.getQuantity();
			}
		}
		return -1;
	}
	
	// it will display all donors
	public void viewDonors(ArrayList<Donor> donors) {
		System.out.println(String.format("%-5s %-10s %-10s %-10s %-5s %s", "NGO", "Manpower", "Aids", "Quantity", "Donor", "Phone"));
		for (Aid donation: donationsNeeded) {	
			System.out.print(String.format("%-5s %-10d %-10s ", this.name, this.manPower, donation.getName()));
			int remainingQuantity = donation.getQuantity();
			for (Donor donor: donors) {
				if (remainingQuantity <= 0) break;
				int quantity = donor.donate(remainingQuantity, donation.getName());
				if (quantity == -1) continue;			
				remainingQuantity -= quantity;
				if (remainingQuantity < 0)
					System.out.println(String.format("%-10d %-5s %s", donation.getQuantity(), donor.getName(), donor.getPhoneNumber()));
				else
					System.out.println(String.format("%-10d %-5s %s", quantity, donor.getName(), donor.getPhoneNumber()));	
			}
			if (remainingQuantity > 0) {
				System.out.println(String.format("%-5s %-10d %-10s %-10d %-5s %s", this.name, this.manPower, donation.getName(), remainingQuantity, "-", "-"));
			}
		}
	}
}
