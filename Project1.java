import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Dress Class........
class Dress {
    private String dressId;
    private String brand;
    private String name;
    private String color;
    private double basePricePerDay;
    private boolean isAvailable;

    public Dress(String dressId, String brand, String name,String color, double basePricePerDay) {
        this.dressId = dressId;
        this.brand = brand;
        this.name = name;
        this.color=color;
        this.basePricePerDay = basePricePerDay;
        this.isAvailable = true;
    }
    public String getDressId() {
        return dressId;
    }

    public String getBrand() {
        return brand;
    }

    public String getName() {
        return name;
    }
    public String getColor() {
        return color;
    }

    public double calculatePrice(int rentalDays) {
        return basePricePerDay * rentalDays;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void rent() {
        isAvailable = false;
    }

    public void returnDress() {
        isAvailable = true;
    }
}

// Customer class........
class Customer {
    private String customerId;
    private String name;
    private String Contactno;

    public Customer(String customerId, String name,String Contactno) {
        this.customerId = customerId;
        this.name = name;
        this.Contactno=Contactno;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }
    public String getContactNo(){
       return Contactno;
    }
}

// Rental Class..........
class Rental {
    private Dress dress;
    private Customer customer;
    private int days;

    public Rental(Dress dress, Customer customer, int days) {
        this.dress = dress;
        this.customer = customer;
        this.days = days;
    }

    public Dress getDress() {
        return dress;
    }

    public Customer getCustomer() {
        return customer;
    }

    public int getDays() {
        return days;
    }
}

// Dress Rental System Class......
class DressRentalSystem {
    private List<Dress> dresses;
    private List<Customer> customers;
    private List<Rental> rentals;

    public DressRentalSystem() {
        dresses = new ArrayList<>();
        customers = new ArrayList<>();
        rentals = new ArrayList<>();
    }
 
    public void addDress(Dress dress) {
        dresses.add(dress);
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public void rentDress(Dress dress, Customer customer, int days) {
        if (dress.isAvailable()) {
            dress.rent();
            rentals.add(new Rental(dress, customer, days));

        } else {
            System.out.println("Dress is not available for rent.");
        }
    }

    public void returnCar(Dress dress) {
        dress.returnDress();
        Rental rentalToRemove = null;
        for (Rental rental : rentals) {
            if (rental.getDress() == dress) {
                rentalToRemove = rental;
                break;
            }
        }
        if (rentalToRemove != null) {
            rentals.remove(rentalToRemove);

        } else {
            System.out.println("Dress was not rented.");
        }
    }

    public void menu() {
        Scanner scanner = new Scanner(System.in);
        System.out.println(" <<< WELCOME TO THE MOUSAM'S bOUTIQUE >>> ");
        while (true) {
            System.out.println("==== Dress Rental System ====");
            System.out.println("1. Rent a Dress");
            System.out.println("2. Return a Dress");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (choice == 1) {
                System.out.println("\n== Rent a Dress ==\n");
                System.out.print("Enter your name: ");
                String customerName = scanner.nextLine();
                System.out.print("Enter your contact number: ");
                String customerContactno = scanner.nextLine();

                System.out.println("\nAvailable Cars:");
                for (Dress dress : dresses) {
                    if (dress.isAvailable()) {
                        System.out.println(dress.getDressId() + " - " + dress.getBrand() + " " + dress.getName()+" "+dress.getColor());
                    }
                }

                System.out.print("\nEnter the Dress ID you want to rent: ");
                String dressId = scanner.nextLine();

                System.out.print("Enter the number of days for rental: ");
                int rentalDays = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                Customer newCustomer = new Customer("CUS" + (customers.size() + 1), customerName,customerContactno);
                addCustomer(newCustomer);

                Dress selectedDress = null;
                for (Dress dress : dresses) {
                    if (dress.getDressId().equals(dressId) && dress.isAvailable()) {
                        selectedDress = dress;
                        break;
                    }
                }

                if (selectedDress != null) {
                    double totalPrice = selectedDress.calculatePrice(rentalDays);
                    System.out.println("\n== Rental Information ==\n");
                    System.out.println("Customer ID: " + newCustomer.getCustomerId());
                    System.out.println("Customer Name: " + newCustomer.getName());
                    System.out.println("Customer Contact no.: "+newCustomer.getContactNo());
                    System.out.println("Dress: "+selectedDress.getDressId()+" " + selectedDress.getBrand() + " " + selectedDress.getName()+" "+selectedDress.getColor());
                    System.out.println("Rental Days: " + rentalDays);
                    System.out.printf("Total Price: $%.2f%n", totalPrice);

                    System.out.print("\nConfirm rental (Y/N): ");
                    String confirm = scanner.nextLine();

                    if (confirm.equalsIgnoreCase("Y")) {
                        rentDress(selectedDress, newCustomer, rentalDays);
                        System.out.println("\nDress rented successfully.");
                    } else {
                        System.out.println("\nRental canceled.");
                    }
                } else {
                    System.out.println("\nInvalid Dress selection or Dress not available for rent.");
                }
            } else if (choice == 2) {
                System.out.println("\n== Return a Dress ==\n");
                System.out.print("Enter the Dress ID you want to return: ");
                String carId = scanner.nextLine();

                Dress dressToReturn = null;
                for (Dress dress : dresses) {
                    if (dress.getDressId().equals(carId) && !dress.isAvailable()) {
                        dressToReturn = dress;
                        break;
                    }
                }

                if (dressToReturn != null) {
                    Customer customer = null;
                    for (Rental rental : rentals) {
                        if (rental.getDress() == dressToReturn) {
                            customer = rental.getCustomer();
                            break;
                        }
                    }

                    if (customer != null) {
                        returnCar(dressToReturn);
                        System.out.println("Dress returned successfully by " + customer.getName());
                    } else {
                        System.out.println("Dress was not rented or rental information is missing.");
                    }
                } else {
                    System.out.println("Invalid Dress ID or Dress is not rented.");
                }
            } else if (choice == 3) {
                break;
            } else {
                System.out.println("Invalid choice. Please enter a valid option.");
            }
        }

        System.out.println("\nThank you for using the Dress Rental System!");
    }

}
public class Project1{
    public static void main(String[] args) {
        DressRentalSystem rentalSystem = new DressRentalSystem();

        Dress dress1 = new Dress("D001", "H&M", "Jeans","blue", 60.0); 
        Dress dress2 = new Dress("D002", "Zara","top","red", 70.0);
        Dress dress3 = new Dress("D003", "Gucci","jumpsuit", "black", 160.0);
        Dress dress4 = new Dress("D004", "H&M","jumpsuit", "green", 120.0);
        Dress dress5 = new Dress("D005", "Zara","jumpsuit", "pink", 123.0);
        Dress dress6 = new Dress("D006", "H&M","Wide leg Jeans", "Maroon", 161.0);
        Dress dress7 = new Dress("D007", "Gucci","TanTop", "purple", 70.0);
        Dress dress8 = new Dress("D008", "H&M","Skirt", "white", 30.0);
        Dress dress9 = new Dress("D009", "Zara","Midi Skirt", "black", 90.0);
        Dress dress10 = new Dress("D0010", "Biba","suitset", "Golden", 167.0);
        Dress dress11 = new Dress("D0011", "Biba","Gown", "Hot pink", 130.0);
        Dress dress12 = new Dress("D0012", "Biba","Lahanga", "red", 170.0);
        Dress dress13 = new Dress("D0013", "Biba","Sharara", "orange", 120.0);
        Dress dress14 = new Dress("D0014", "Biba","Garara", "black", 100.0);
        Dress dress15 = new Dress("D0015", "Libaas","Lahanga Bridel", "red-green", 250.0);
        Dress dress16 = new Dress("D0016", "Libaas","Lahanga Bridel", "off white", 195.0);
        Dress dress17 = new Dress("D0017", "Libaas","Lahanga", "yellow", 110.0);
        Dress dress18 = new Dress("D0018", "Libaas","Sharara", "black", 150.0); 
        Dress dress19 = new Dress("D0019", "Libaas","Sharara","cherry red",50.0);
        Dress dress20 = new Dress("D0020", "Libaas","Anarkali", "chocolatyy", 120.0);
        Dress dress21 = new Dress("D0021", "Libaas","Anarkali", "green", 101.0);
        Dress dress22 = new Dress("D0022", "Biba","pantsuit", "magenta", 165.0);
        Dress dress23 = new Dress("D0023", "Biba","jumpsuit", "voilet", 20.0);
        Dress dress24 = new Dress("D0024","Libaas","Gown","Indigo" ,150.0);
        Dress dress25 = new Dress("D0025", "Biba","saree", "black", 200.0);
        Dress dress26 = new Dress("D0026", "Libaas","saree", "grey", 110.0);
        Dress dress27 = new Dress("D0027", "Libaas","saree", "green", 130.0);
        Dress dress28 = new Dress("D0028", "Biba","saree", "red", 150.0);
        Dress dress29 = new Dress("D0029", "Raymond","saree", "black", 190.0);
        Dress dress30 = new Dress("D0030", "Libaas","saree","pink", 150.0);
        Dress dress31 = new Dress("D0031", "Sabyasachi","saree","Burgundy", 189.0);
        Dress dress32 = new Dress("D0032", "Libaas"," Organza saree","navy blue", 170.0);
        Dress dress33 = new Dress("D0033", "Libaas","saree","Bronze", 156.0);
        Dress dress34 = new Dress("D0034", "Sabyasachi","saree","Brown", 130.0);
        Dress dress35 = new Dress("D0035", "Sabyasachi","saree","Lavender", 150.0);
        Dress dress36 = new Dress("D0036", "Sabyasachi","silk saree","Gold", 223.0);
        Dress dress37= new Dress("D0037", "Sabyasachi","Banarsi saree","Rust", 150.0);
        Dress dress38 = new Dress("D0038", "Meenakatri","saree","Teal", 147.0);
        Dress dress39 = new Dress("D0039", "Meenakatri","saree"," silk Tan", 159.0);
        Dress dress40 = new Dress("D0040", "Meenakatri","saree","Coral", 210.0);
        Dress dress41 = new Dress("D0041", "Meenakatri","saree","banarsi silver", 350.0);
        Dress dress42 = new Dress("D0042", "Meenakatri","saree","cyan", 267.0);
        Dress dress43 = new Dress("D0043", "Libaas","lahanga","Sky blue", 190.0);
        Dress dress44 = new Dress("D0044", "Libaas","saree","Ochre", 250.0);
        Dress dress45 = new Dress("D0045", "Biba","Lahanga","red", 220.0);
        Dress dress46 = new Dress("D0046", "Raymond","co-ord set","black", 150.0);
        Dress dress47 = new Dress("D0047", "Raymond","Trouser","white", 1870.0);
        Dress dress48 = new Dress("D0048", "Raymond","suit","silver", 150.0);
        Dress dress49 = new Dress("D0049", "Levis","saree","maroon", 187.0);
        Dress dress50 = new Dress("D0050", "Levis","co-ord set","pink", 130.0);

        rentalSystem.addDress(dress1);
        rentalSystem.addDress(dress2);
        rentalSystem.addDress(dress3);
        rentalSystem.addDress(dress4);
        rentalSystem.addDress(dress5);
        rentalSystem.addDress(dress6);
        rentalSystem.addDress(dress7);
        rentalSystem.addDress(dress8);
        rentalSystem.addDress(dress9);
        rentalSystem.addDress(dress10);
        rentalSystem.addDress(dress11);
        rentalSystem.addDress(dress12);
        rentalSystem.addDress(dress13);
        rentalSystem.addDress(dress14);
        rentalSystem.addDress(dress15);
        rentalSystem.addDress(dress16);
        rentalSystem.addDress(dress17);
        rentalSystem.addDress(dress18);
        rentalSystem.addDress(dress19);
        rentalSystem.addDress(dress20);
        rentalSystem.addDress(dress21);
        rentalSystem.addDress(dress22);
        rentalSystem.addDress(dress23);
        rentalSystem.addDress(dress24);
        rentalSystem.addDress(dress25);
        rentalSystem.addDress(dress26);
        rentalSystem.addDress(dress27);
        rentalSystem.addDress(dress28);
        rentalSystem.addDress(dress29);
        rentalSystem.addDress(dress30);
        rentalSystem.addDress(dress31);
        rentalSystem.addDress(dress32);
        rentalSystem.addDress(dress33);
        rentalSystem.addDress(dress34);
        rentalSystem.addDress(dress35);
        rentalSystem.addDress(dress36);
        rentalSystem.addDress(dress37);
        rentalSystem.addDress(dress38);
        rentalSystem.addDress(dress39);
        rentalSystem.addDress(dress40);
        rentalSystem.addDress(dress41);
        rentalSystem.addDress(dress42);
        rentalSystem.addDress(dress43);
        rentalSystem.addDress(dress44);
        rentalSystem.addDress(dress45);
        rentalSystem.addDress(dress46);
        rentalSystem.addDress(dress47);
        rentalSystem.addDress(dress48);
        rentalSystem.addDress(dress49);
        rentalSystem.addDress(dress50);

        rentalSystem.menu();
    }
}