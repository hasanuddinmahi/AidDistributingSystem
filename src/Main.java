
import java.util.Scanner;

public class Main {

	private static DC dc = new DC();
	
	// it will display then menu 
	public static void main(String args[]) {
		while (true) {
			switch (mainMenu()) {
			case 1:
				handleLogin();
				break;
			case 2:
				handleRegister();
				break;
			case 3:
				handleDC();
				break;
			case 4: System.exit(0);
			default: System.out.println("Invalid option\n");
			}
		}
	}
	
	private static int DCMenu() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("1. View all");
		System.out.println("2. 1-1 view");
		System.out.println("3. 1-m view");
		System.out.println("4. m-1 view");
		System.out.println("5. m-m view");
		System.out.println("6. EXIT");
		System.out.print("\nEnter your choice: ");
		try {
			return Integer.parseInt(scanner.nextLine());			
		} catch (Exception e) {
			return -1;
		}
	}
	
	private static void handleDC() {
		while (true) {		
			switch (DCMenu()) {
			case 1:				
				dc.viewAll();
				break;
			case 2:
				dc.viewOneToOne();
				break;
			case 3:
				dc.viewOneToMany();
				break;
			case 4:
				dc.viewManyToOne();
				break;
			case 5:
				dc.viewManyToMany();
				break;
			case 6: return;
			default: System.out.println("Invalid option\n");
			}	
		}
	}
	
	private static int mainMenu() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("1. Login");
		System.out.println("2. Register");
		System.out.println("3. Enter as DC");
		System.out.println("4. EXIT");
		System.out.print("\nEnter your choice: ");
		try {
			return Integer.parseInt(scanner.nextLine());			
		} catch (Exception e) {
			return -1;
		}
	}
	
	private static void handleLogin() {
		while (true) {
			System.out.println("Login as: ");		
			switch (selectionMenu()) {
			case 1:				
				dc.handleDonor();
				break;
			case 2:				
				dc.handleNGO();
				break;
			case 3: return;
			default: System.out.println("Invalid option\n");
			}	
		}
	}

	private static void handleRegister() {
		while (true) {
			System.out.println("Register as: ");		
			switch (selectionMenu()) {
			case 1:
				if (dc.registerDonor()) return;
				break;
			case 2:
				if (dc.registerNgo()) return;
				break;
			case 3: return;
			default: System.out.println("Invalid option\n");
			}	
		}
	}
	
	private static int selectionMenu() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("1. Donor");
		System.out.println("2. NGO");
		System.out.println("3. BACK");
		System.out.print("\nEnter your choice: ");
		try {
			return Integer.parseInt(scanner.nextLine());			
		} catch (Exception e) {
			return -1;
		}
	}

}
